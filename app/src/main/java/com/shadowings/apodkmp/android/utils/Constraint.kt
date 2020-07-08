package com.shadowings.apodkmp.android.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintSet.connectToTopWithHeight(child: View, parent: View, height: Int) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP)
    this.constrainHeight(child.id, height)
}

fun ConstraintSet.centerInParentWithMargin(child: View, parent: View, margin: Int) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, margin)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, margin)
    this.connect(child.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM, margin)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP, margin)
}

fun ConstraintSet.bottomStartInParentWithSide(
    child: View,
    parent: View,
    side: Int
) {
    this.connect(child.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM, Dimens.margin)
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, Dimens.margin)
    this.constrainHeight(child.id, side)
    this.constrainWidth(child.id, side)
}

fun ConstraintSet.appendBelowWithMarginAndHeight(
    child: View,
    parent: View,
    margin: Int,
    height: Int
) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, margin)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, margin)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.BOTTOM, margin * 2)
    this.constrainHeight(child.id, height)
}
