package com.appetiser.racetimer.feature.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.timer.TimerState
import com.appetiser.racetimer.feature.timer.TimerViewModel
import com.appetiser.racetimer.local.RiderRepository
import com.appetiser.racetimer.model.Rider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class ResultViewModel(private val repository: RiderRepository): ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    private val _state by lazy {
        PublishSubject.create<ResultState>()
    }
    val state: Observable<ResultState> = _state

    fun getAllRiders() {
        repository
            .allRider
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onSuccess = {
                    _state
                        .onNext(
                            ResultState.GetAllRiders(splitRidersByCategory(
                                it.filterNot { it.elapseTime == 0L }.sortedWith(Comparator.comparingLong {
                                    it.elapseTime
                                })
                            ))
                        )
                }
            )
            .addTo(disposable)
    }

    fun splitRidersByCategory(list: List<Rider>): List<Rider> {
        val hardtailRiders = list.filter { rider -> rider.category == "Hardtail" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )
        val fullsusRiders = list.filter { rider -> rider.category == "Fullsus" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )

        val allRiders = mutableListOf<Rider>()
        allRiders.addAll(hardtailRiders)
        allRiders.addAll(fullsusRiders)
        return allRiders
    }

}
class ResultViewModelFactory(private val repository: RiderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
