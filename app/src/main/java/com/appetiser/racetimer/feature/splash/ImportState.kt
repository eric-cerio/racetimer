package com.appetiser.racetimer.feature.splash

sealed class ImportState {

    object SuccessImportingCSV: ImportState()

    object HasRider: ImportState()

    object NoRider: ImportState()
}
