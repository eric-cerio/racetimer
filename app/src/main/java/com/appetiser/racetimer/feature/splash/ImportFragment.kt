package com.appetiser.racetimer.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.navigation.fragment.findNavController
import com.appetiser.racetimer.MainActivity
import com.appetiser.racetimer.R
import com.appetiser.racetimer.RaceTimerApplication
import com.appetiser.racetimer.databinding.FragmentImportBinding
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.utils.ViewUtils
import com.atwa.filepicker.core.FilePicker
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import java.io.File

class ImportFragment : Fragment() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    private val importViewModel: ImportViewModel by viewModels {
        ImportViewModelFactory((((activity as MainActivity)).application as RaceTimerApplication).repository)
    }

    private var _binding: FragmentImportBinding? = null

    lateinit var filePicker : FilePicker
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filePicker = FilePicker.getInstance(requireActivity() as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnImportCSV.setOnClickListener {
            filePicker.pickFile {
                //Toast.makeText(requireContext(), "${it?.second?.path}", Toast.LENGTH_SHORT).show()
                importViewModel.parseCSV(File(it!!.second!!.toString()))
            }
        }

        binding.btnResumeRace.setOnClickListener {
            importViewModel.hasRiders()
        }

        binding
            .inputRaceName
            .setOnClickListener {
                binding.spinnerRaceType.performClick()
            }

        binding
            .spinnerRaceType
            .onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.inputRaceName.setText(p0?.adapter?.getItem(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.inputRaceName.setText("")
            }
        }
        setupVmObserver()
        setupSpinner()
    }

    private fun setupSpinner() {
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            listOf<String>("Seeding Run", "Final Run")
        )
        binding.spinnerRaceType.adapter = arrayAdapter
    }

    private fun setupVmObserver() {
        importViewModel
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

        binding.inputRaceName.textChangeEvents()
            .map {
                it.text.isNotEmpty()
            }
            .subscribeOn(scheduler.ui())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    binding.btnImportCSV.isEnabled = it
                }
            )
            .addTo(disposable)

    }

    private fun handleState(it: ImportState?) {
        when(it){
            ImportState.SuccessImportingCSV -> {
                findNavController()
                    .navigate(
                        R.id.action_ImportFragment_to_FirstFragment,
                        bundleOf(
                            Pair("raceName", binding.inputRaceName.text.toString()),
                            Pair("racerInterval", if(binding.inputRacerInterval.text.toString().isEmpty()) 30 else binding.inputRacerInterval.text.toString().toInt())
                        )
                    )
            }

            ImportState.HasRider -> {
                findNavController()
                    .navigate(
                        R.id.action_ImportFragment_to_FirstFragment,
                        bundleOf(
                            Pair("raceName", binding.inputRaceName.text.toString()),
                            Pair("racerInterval", if(binding.inputRacerInterval.text.toString().isEmpty()) 30 else binding.inputRacerInterval.text.toString().toInt())
                        )
                    )
            }

            ImportState.NoRider -> {
                ViewUtils.showConfirmDialog(
                    requireContext(),
                    "No Riders",
                    "Race has no riders, please import riders data (.csv file)",
                    "OK",
                    "Cancel",
                    {
                        binding.btnImportCSV.performClick()
                    },
                    {

                    }
                )
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}