package com.appetiser.racetimer.feature.timer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.appetiser.racetimer.model.Rider

object RiderDiffUtil : DiffUtil.ItemCallback<Rider>() {
    override fun areItemsTheSame(oldItem: Rider, newItem: Rider): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Rider, newItem: Rider): Boolean {
        return oldItem.finishTime == newItem.finishTime
    }
}