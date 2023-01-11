package com.appetiser.racetimer.feature.scheduler

import io.reactivex.CompletableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer

/**
 * Allow providing different types of [Scheduler]s.
 */
interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun <T> applySchedulers(): SingleTransformer<T, T>

    fun <T> applyMaybeSchedulers(): MaybeTransformer<T, T>

    fun applyCompletableSchedulers(): CompletableTransformer
}
