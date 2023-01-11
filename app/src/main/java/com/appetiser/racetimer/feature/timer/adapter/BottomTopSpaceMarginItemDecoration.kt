package com.appetiser.racetimer.feature.timer.adapter
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomTopSpaceMarginItemDecoration(private val verticalMargin: Int = 0) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val childCount = state.itemCount

        val firstItem = position == 0
        val lastItem = position == childCount - 1

        with(outRect) {
            top = if (firstItem) 2 * verticalMargin else verticalMargin / 2
            bottom = if (lastItem) 2 * verticalMargin else verticalMargin / 2
        }
    }
}
