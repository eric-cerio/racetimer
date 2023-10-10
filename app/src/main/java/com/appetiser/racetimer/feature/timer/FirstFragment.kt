package com.appetiser.racetimer.feature.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appetiser.racetimer.MainActivity
import com.appetiser.racetimer.R
import com.appetiser.racetimer.RaceTimerApplication
import com.appetiser.racetimer.databinding.FragmentFirstBinding
import com.appetiser.racetimer.ext.formatTime
import com.appetiser.racetimer.ext.toPx
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.splash.ImportViewModelFactory
import com.appetiser.racetimer.feature.timer.adapter.BottomTopSpaceMarginItemDecoration
import com.appetiser.racetimer.feature.timer.adapter.RiderAdapter
import com.appetiser.racetimer.model.Rider
import com.appetiser.racetimer.utils.ViewUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    private val timerViewModel by viewModels<TimerViewModel> {
        TimerViewModelFactory((((activity as MainActivity)).application as RaceTimerApplication).repository)
    }

    private val rideAdapter by lazy {
        RiderAdapter(disposable)
    }

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewObservable()
        setupVmObserver()
        setupRecyclerView()
//        if(timerViewModel.riderList.isNotEmpty()) {
//            binding.listRacers.adapter = rideAdapter
//            binding.listRacers.addItemDecoration(
//                BottomTopSpaceMarginItemDecoration(
//                    verticalMargin = 8.toPx(requireContext())
//                )
//            )
//            rideAdapter.submitList(timerViewModel.riderList)
//        } else {
//            setupRecyclerView()
//        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ViewUtils.showConfirmDialog(
                    requireContext(),
                    "End Race",
                    "Are you sure to end race?",
                    "Yes",
                    "No",
                    {
                        findNavController().navigateUp()
                    }
                )
            }
        })
        timerViewModel.getCategoryRiderRange()
    }

    private fun setupRecyclerView() {
        rideAdapter.onRiderClicked
            .subscribeOn(scheduler.ui())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext =  { rider ->
                    ViewUtils.showInputRacerIdDialog(requireContext(), rider.finishTimeFormatted) {
                        timerViewModel.updateRacerId(
                            it,
                            rider.finishTimeFormatted,
                            arguments?.get("raceName").toString(),
                            arguments?.get("racerInterval").toString().toInt()
                        )
                    }
                }
            )
            .addTo(disposable)

        with(binding.listRacers) {
            adapter = rideAdapter
            layoutManager =  LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            addItemDecoration(
                BottomTopSpaceMarginItemDecoration(
                    verticalMargin = 8.toPx(requireContext())
                )
            )
        }
    }

    private fun setupVmObserver() {
        timerViewModel
            .state
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    handleState(it)
                }
            )
            .addTo(disposable)
    }

    private fun setTopRacer(list: List<Rider>) {
        val rider = list.filter {
            it.id > 0
        }.minWith(Comparator.comparingLong {
            it.elapseTime
        })

        binding.tvTopPlacer.text = String.format("Racer %d / Time: %s", rider.id, rider.elapseTime.formatTime())
    }

    private fun handleState(state: TimerState?) {
        when(state) {
            is TimerState.UpdateTime -> {
                binding.tvTimer.text = state.time
            }

            is TimerState.UpdateRiders -> {
                rideAdapter.submitList(state.list.toList().reversed())
                Observable.timer(1, TimeUnit.SECONDS)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .subscribeBy(
                        onError = {

                        },
                        onNext = {
                            binding.listRacers.scrollToPosition(0)
                            setTopRacer(state.list)

                        }
                    )
                    .addTo(disposable)
            }

            is TimerState.ShowRiderIDs ->  {
                binding.tvRunType.text = String.format(
                    "%s \n%s", arguments?.get("raceName").toString(), state.riderRange
                )
            }

            is TimerState.GetRacerTime -> {
                Toast.makeText(requireContext(), "Racer time ${state.time}", Toast.LENGTH_SHORT).show()
            } else -> {

            }


        }
    }

    private fun setupViews() {
        binding.tvRunType.text = arguments?.get("raceName").toString()
        binding.btnStart.clicks()
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    timerViewModel.startTimer()
                }
            )
            .addTo(disposable)

        binding.btnStop.clicks()
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    timerViewModel.stopTimer()
                }
            )
            .addTo(disposable)

        binding.btnReset.clicks()
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    ViewUtils.showConfirmDialog(
                        requireContext(),
                        "Confirm Reset",
                        "All time data will be deleted, Are you sure you want to reset time?",
                        "Yes",
                        "No",
                        {
                            timerViewModel.resetTimer()
                        },
                        {

                        }
                    )

                }
            )
            .addTo(disposable)

        binding.btnSetTime.clicks()
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    timerViewModel.setRacerTime()
                }
            )
            .addTo(disposable)

        binding.btnViewResult.clicks()
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    ViewUtils.showConfirmDialog(
                        requireContext(),
                        "Race Results",
                        "Are you sure you want to view ranking? (you might lose existing timer data)",
                        "Yes",
                        "No",
                        {
                            findNavController().navigate(
                                R.id.action_FirstFragment_to_SecondFragment,
                                bundleOf(
                                    Pair("raceName", arguments?.get("raceName").toString())
                                )
                            )
                        },
                        {

                        }
                    )
                }
            )
            .addTo(disposable)
    }

    private fun setupViewObservable() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}