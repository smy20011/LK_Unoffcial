package smy20011.lkreader.util

import android.app.Activity
import android.support.annotation.AnyRes
import android.view.View

fun <T: View> findById(view: View, @AnyRes resId: Int): T = view.findViewById(resId) as T
fun <T: View> findById(activity: Activity, @AnyRes resId: Int): T = activity.findViewById(resId) as T
