package com.appetiser.racetimer.feature.result

import com.appetiser.racetimer.model.Rider

sealed class ResultState {
    data class GetAllRiders(val list: List<Rider>): ResultState()

    data class ExportSuccess(val path:String): ResultState()

    data class Error(val errorMessage: String): ResultState()
}
