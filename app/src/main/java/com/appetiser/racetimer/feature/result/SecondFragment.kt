package com.appetiser.racetimer.feature.result

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
import com.appetiser.racetimer.databinding.FragmentSecondBinding
import com.appetiser.racetimer.ext.toPx
import com.appetiser.racetimer.feature.result.adapter.ResultAdapter
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.timer.TimerViewModel
import com.appetiser.racetimer.feature.timer.TimerViewModelFactory
import com.appetiser.racetimer.feature.timer.adapter.BottomTopSpaceMarginItemDecoration
import com.appetiser.racetimer.feature.timer.adapter.RiderAdapter
import com.appetiser.racetimer.utils.ViewUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    private val resultViewModel by viewModels<ResultViewModel> {
        ResultViewModelFactory((((activity as MainActivity)).application as RaceTimerApplication).repository)
    }

    private val resultAdapter by lazy {
        ResultAdapter(disposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupVmObserver()
        setupRecyclerView()

        binding.tvRunType.text = arguments?.get("raceName").toString()

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

        resultViewModel.getAllRiders()
    }

    private fun setupView() {
        binding
            .btnImportCSV
            .setOnClickListener {
                resultViewModel.exportResult(arguments?.get("raceName").toString())
            }
    }

    private fun setupVmObserver() {
        resultViewModel
            .state
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    handleResult(it)
                }
            )
            .addTo(disposable)
    }

    private fun handleResult(it: ResultState?) {
        when(it) {
            is ResultState.GetAllRiders -> {
                resultAdapter.submitList(it.list)
            }
            is ResultState.ExportSuccess -> {
                ViewUtils.showConfirmDialog(
                    requireContext(),
                    "Export CSV Success",
                    "${it.path} file is saved in Downloads Folder",
                    "OK",
                    "",
                    {

                    },
                    {

                    }
                )
            }
            is ResultState.Error -> {
                Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    private fun setupRecyclerView() {

        with(binding.listRacers) {
            adapter = resultAdapter
           layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            addItemDecoration(
                BottomTopSpaceMarginItemDecoration(
                    verticalMargin = 8.toPx(requireContext())
                )
            )
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}