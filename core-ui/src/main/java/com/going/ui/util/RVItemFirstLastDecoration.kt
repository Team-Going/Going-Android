package com.going.ui.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RVItemFirstLastDecoration(
    private val top: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = top
        }

        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = bottom
        }
    }
}
