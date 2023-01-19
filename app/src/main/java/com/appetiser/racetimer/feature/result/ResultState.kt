package com.appetiser.racetimer.feature.result

import com.appetiser.racetimer.model.Rider

sealed class ResultState {
    data class GetAllRiders(val list: List<Rider>): ResultState()
}
