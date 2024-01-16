package com.going.presentation.todo

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TodoDecoration(
    val context: Context,
    private val top: Int,
    private val left: Int,
    private val right: Int,
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

        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.top = top
            outRect.left = left
            outRect.right = right
            outRect.bottom = bottom
        }
    }
}
