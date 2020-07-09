package com.shadowings.apodkmp.android.utils.dsl

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintSet.centerInParent(child: View, parent: View, margin: Int) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, margin)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, margin)
    this.connect(child.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM, margin)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP, margin)
}
