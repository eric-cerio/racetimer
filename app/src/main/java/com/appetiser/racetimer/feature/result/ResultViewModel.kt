package com.appetiser.racetimer.feature.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appetiser.racetimer.ext.formatTime
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.timer.TimerState
import com.appetiser.racetimer.feature.timer.TimerViewModel
import com.appetiser.racetimer.local.RiderRepository
import com.appetiser.racetimer.model.ResultRow
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
        val bfs = list.filter { rider -> rider.category == "BFS" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )
        val bht = list.filter { rider -> rider.category == "BHT" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )

        val afs = list.filter { rider -> rider.category == "AFS" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )
        val aht = list.filter { rider -> rider.category == "AHT" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )
        val elite = list.filter { rider -> rider.category == "Elite" }.sortedWith(
            Comparator.comparing {
                it.elapseTime
            }
        )


        val allRiders = mutableListOf<Rider>()
        allRiders.addAll(bfs)
        allRiders.addAll(bht)
        allRiders.addAll(afs)
        allRiders.addAll(aht)
        allRiders.addAll(elite)
        return allRiders
    }

    fun exportResult(raceName: String){
        repository
            .allRider
            .map {
                it.filterNot { it.elapseTime == 0L }.sortedWith(Comparator.comparingLong {
                    it.elapseTime
                }).mapIndexed { index, rider ->
                    ResultRow(
                        rank = index+1,
                        name = rider.name,
                        id = rider.id,
                        category = rider.category,
                        startTimeFormatted = rider.startTimeFormatted,
                        finishTime = rider.finishTimeFormatted,
                        finishTimeFormatted = if(rider.runType == "Seeding Run") {
                            rider.seedingTime.formatTime()
                        } else {
                            rider.finalTime.formatTime()
                        }
                    )
                }
            }
            .flatMap {
                repository.exportToCSV(it, raceName)
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {
                    _state
                        .onNext(
                            ResultState.Error(it.message.orEmpty())
                        )
                },
                onSuccess = {
                    _state
                        .onNext(
                            ResultState.ExportSuccess(it)
                        )
                }
            )
            .addTo(disposable)
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
