package smy20011.lkreader.ui

import android.view.View

public open class BasePresenter<T> {
    private var view: T? = null

    fun takeView(view: T) {
        this.view = view
    }

    fun dropView(view: T) {
        this.view = null
    }

    fun getView(): T  = view!!

    fun hasView(): Boolean = view != null
}
