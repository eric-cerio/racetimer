package com.appetiser.racetimer.feature.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appetiser.racetimer.feature.scheduler.SchedulerProvider
import com.appetiser.racetimer.feature.splash.ImportViewModel
import com.appetiser.racetimer.local.RiderRepository
import com.appetiser.racetimer.model.Rider
import com.google.android.material.textview.MaterialTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timerx.*
import java.util.concurrent.TimeUnit

class TimerViewModel(private val repository: RiderRepository): ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val scheduler: SchedulerProvider =  SchedulerProvider.getInstance()

    var tempRaceId: Int = -1

    val riderList = mutableListOf<Rider>()

    val stopwatch: Stopwatch by lazy {
        buildStopwatch {
            startFormat("MM:SS.LLL")
            onTick(tickListener = object : TimeTickListener {
                override fun onTick(millis: Long, formattedTime: CharSequence) {
                    _state.onNext(TimerState.UpdateTime(formattedTime.toString()))
                }
            })
            changeFormatWhen(1,TimeUnit.MILLISECONDS, "MM:SS.LLL")
        }
    }

    private val _state by lazy {
        PublishSubject.create<TimerState>()
    }
    val state: Observable<TimerState> = _state

    fun setStopWatchDisplay(textView: MaterialTextView) {

    }

    fun startTimer() {
        stopwatch.start()
    }

    fun stopTimer() {
        stopwatch.stop()
    }

    fun resetTimer() {
        stopwatch.reset()
        stopwatch.setTime(0, TimeUnit.MILLISECONDS)
        riderList.clear()
        tempRaceId  = -1
        _state.onNext(TimerState.UpdateRiders(riderList.toMutableList()))
        _state.onNext(TimerState.UpdateTime(stopwatch.currentFormattedTime.toString()))
    }

    fun setRacerTime() {
//        _state.onNext(TimerState.GetRacerTime(stopwatch.currentFormattedTime.toString()))

        riderList.add(
            Rider(
                id = tempRaceId--,
                categoryId = tempRaceId,
                name ="temp name",
                category = "Hard Tail",
                startTimeFormatted = "00:00.000",
                elapseTime = 0L,
                finishTime = stopwatch.currentTimeInMillis,
                finishTimeFormatted = stopwatch.currentFormattedTime.toString()
            )
        )

        _state.onNext(TimerState.UpdateRiders(riderList.toMutableList()))
    }

    fun updateRacerId(racerId: String, finishTimeFormatted: String, runType: String, raceInterval: Int = 0) {

        repository
            .getRider(racerId.toInt())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onSuccess = { rider ->
                    riderList
                        .find {
                            it.finishTimeFormatted == finishTimeFormatted
                        }.apply {
                            this?.categoryId = rider.categoryId
                            val catId = this?.categoryId ?: 0
                            val finishTime = this?.finishTime ?: 0
                            this?.name = rider.name
                            this?.category = rider.category
                            this?.id = racerId.toInt()
                            Log.e("calculation", "${finishTime} - ${ ((catId-1) * (raceInterval * 1000))} = ${finishTime - ((catId-1) * (raceInterval * 1000))}")
                            this?.elapseTime = finishTime - ((catId-1) * (raceInterval * 1000))
                            this?.runType = runType

                            if(runType == "Seeding Run") {
                                this?.seedingTime = finishTime - ((catId-1) * (raceInterval * 1000))
                            } else {
                                this?.finalTime = finishTime - ((catId-1) * (raceInterval * 1000))
                            }
                        }
                    updatedRacers(riderList)
                }
            )
            .addTo(disposable)
    }

    private fun updatedRacers(riderList: List<Rider>) {
        repository
            .insertAllRiders(riderList.filterNot {
                it.id < 0
            })
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribeBy(
                onError = {

                },
                onComplete = {
                    _state.onNext(TimerState.UpdateRiders(riderList.toMutableList()))
                }
            )
            .addTo(disposable)
    }
}

class TimerViewModelFactory(private val repository: RiderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}