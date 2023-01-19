package com.appetiser.racetimer.feature.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appetiser.racetimer.R
import com.appetiser.racetimer.databinding.ItemRiderBinding
import com.appetiser.racetimer.databinding.ItemRiderRankBinding
import com.appetiser.racetimer.ext.formatTime
import com.appetiser.racetimer.feature.timer.adapter.RiderDiffUtil
import com.appetiser.racetimer.model.Rider
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

class ResultAdapter(
    private val disposables: CompositeDisposable
): ListAdapter<Rider, ResultAdapter.ResultViewHolder>(RiderDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rider_rank, parent, false)

        val binding = ItemRiderRankBinding.bind(view)

        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ResultViewHolder(val binding: ItemRiderRankBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rider)  {
            binding.tvRank.text = adapterPosition.toString()
            binding.tvCategory.text = item.category
            binding.tvNameRaceId.text = "${item.name} #${item.id}"
            binding.tvTime.text = item.elapseTime.formatTime()
        }
    }
}