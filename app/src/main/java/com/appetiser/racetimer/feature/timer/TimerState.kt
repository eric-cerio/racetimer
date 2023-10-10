package com.appetiser.racetimer.feature.timer

import com.appetiser.racetimer.model.Rider

sealed class TimerState {

    class UpdateTime(val time: String): TimerState()

    class GetRacerTime(val time: String): TimerState()

    class UpdateRiders(val list: List<Rider>): TimerState()

    class ShowRiderIDs(val riderRange: String): TimerState()

}
