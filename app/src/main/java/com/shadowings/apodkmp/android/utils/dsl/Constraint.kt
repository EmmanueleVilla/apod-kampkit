package com.shadowings.apodkmp.android.utils.dsl

import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

enum class Connection {
    Top,
    Bottom
}

fun ConstraintSet.connectTo(connection: Connection, child: View, parent: View, margin: Int = Dimens.margin) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, margin)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, margin)
    when (connection) {
        Connection.Top -> this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.TOP, margin)
        Connection.Bottom -> this.connect(child.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM, margin)
    }
}

fun ConstraintSet.centerInParent(child: View, parent: View, margin: Int = Dimens.margin) {
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
    this.connect(child.id, ConstraintSet.BOTTOM, parent.id, ConstraintSet.BOTTOM,
        Dimens.margin
    )
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START,
        Dimens.margin
    )
    this.constrainHeight(child.id, side)
    this.constrainWidth(child.id, side)
}

fun ConstraintSet.appendBelowWithMarginAndHeight(
    child: View,
    parent: View,
    margin: Int,
    height: Int
) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, 0)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, 0)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.BOTTOM, margin * 2)
    this.constrainHeight(child.id, height)
}

fun ConstraintSet.appendBelowAndToBottom(
    child: View,
    parent: View,
    container: View,
    margin: Int
) {
    this.connect(child.id, ConstraintSet.START, parent.id, ConstraintSet.START, 0)
    this.connect(child.id, ConstraintSet.END, parent.id, ConstraintSet.END, 0)
    this.connect(child.id, ConstraintSet.TOP, parent.id, ConstraintSet.BOTTOM, margin * 2)
    this.connect(child.id, ConstraintSet.BOTTOM, container.id, ConstraintSet.BOTTOM, margin * 2)
}
