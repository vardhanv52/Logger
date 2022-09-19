package com.android.my_logger.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.android.my_logger.listeners.MyOnClickListener
import com.android.my_logger.utils.LogUtil

class MyButton : Button, View.OnClickListener {
    var listener: OnClickListener? = null
    var ctx: Context? = null

    constructor(context: Context) : super(context) {
        this.ctx = context
        super.setOnClickListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.ctx = context
        super.setOnClickListener(this)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        this.ctx = context
        super.setOnClickListener(this)
    }

    constructor(
        context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        this.ctx = context
        super.setOnClickListener(this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        listener = l
    }

    override fun onClick(p0: View?) {
        if (listener !is MyOnClickListener)
            LogUtil.logUserAction(p0)
        listener?.onClick(p0)
    }
}