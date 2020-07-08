package com.shadowings.apodkmp.android.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintSet.connectToTopWithHeight(child: View, parent: View, height: Int) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP)
    this.constrainHeight(child.id, height)
}
