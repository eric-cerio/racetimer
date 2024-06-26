package com.appetiser.racetimer.feature.timer

import androidx.lifecycle.ViewModel
import com.appetiser.racetimer.model.Rider
import com.google.android.material.textview.MaterialTextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import timerx.*
import java.util.concurrent.TimeUnit

class TimerViewModel: ViewModel() {

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
        _state.onNext(TimerState.UpdateTime(stopwatch.currentFormattedTime.toString()))
    }

    fun setRacerTime() {
//        _state.onNext(TimerState.GetRacerTime(stopwatch.currentFormattedTime.toString()))

        riderList.add(
            Rider(
                tempRaceId--,
                "0:0",
                0L,
                stopwatch.currentTimeInMillis
            )
        )

        _state.onNext(TimerState.UpdateRiders(riderList.toMutableList()))
    }
}