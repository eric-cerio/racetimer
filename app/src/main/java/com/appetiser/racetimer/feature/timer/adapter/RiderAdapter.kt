package com.appetiser.racetimer.feature.timer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appetiser.racetimer.R
import com.appetiser.racetimer.databinding.ItemRiderBinding
import com.appetiser.racetimer.ext.formatTime
import com.appetiser.racetimer.model.Rider
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class RiderAdapter(
    private val disposables: CompositeDisposable
): ListAdapter<Rider, RiderAdapter.RiderViewHolder>(RiderDiffUtil) {

    val onRiderClicked = PublishSubject.create<Rider>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rider, parent, false)

        val binding = ItemRiderBinding.bind(view)

        return RiderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiderViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root
            .clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {

                },
                onNext = {
                    onRiderClicked.onNext(getItem(position))
                }
            )
            .addTo(disposables)
    }

    class RiderViewHolder(val binding: ItemRiderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rider)  {
            binding.tvRaceId.text = item.id.toString()
            binding.tvFinishTime.text = item.finishTime.formatTime()
            binding.tvStartTime.text = item.startTime
        }
    }
}