package com.appetiser.racetimer.feature.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.timer.TimerState
import com.appetiser.racetimer.local.RiderRepository
import com.appetiser.racetimer.model.Rider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.io.File

class ImportViewModel(private val repository: RiderRepository): ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    private val _state by lazy {
        PublishSubject.create<ImportState>()
    }
    val state: Observable<ImportState> = _state

    fun parseCSV(file: File) {
        repository
            .parseCSV(file)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {
                          Log.e(this.javaClass.name, it.message.orEmpty())
                },
                onSuccess = {
                    importCSV(it)
                }
            )
            .addTo(disposable)
    }

    fun importCSV(riders: List<Rider>) {
        repository
            .insertAllRiders(riders)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {
                    Log.e(this.javaClass.name, it.message.orEmpty())
                },
                onComplete = {
                    _state
                        .onNext(
                            ImportState.SuccessImportingCSV
                        )
                }
            )
            .addTo(disposable)
    }
}

class ImportViewModelFactory(private val repository: RiderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImportViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}