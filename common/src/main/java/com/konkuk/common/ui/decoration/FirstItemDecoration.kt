package com.konkuk.common.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FirstItemDecoration(private val margin: Int = 28) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = margin
        } else {
            outRect.left = 0
        }
    }
}
