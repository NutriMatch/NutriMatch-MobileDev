package com.akmalmf.nutrimatch.abstraction.base

import androidx.appcompat.app.AppCompatActivity

import androidx.viewbinding.ViewBinding
import com.akmalmf.nutrimatch.R
import androidx.appcompat.widget.Toolbar

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/09 04:44
 * akmalmf007@gmail.com
 */
abstract class BaseToolbarFragment<VB: ViewBinding> : BaseFragment<VB>(), IToolbar {

    protected abstract fun setToolbar(): Toolbar?

    override fun onStart() {
        super.onStart()

        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(setToolbar())

        if (activity.supportActionBar != null) {
            activity.supportActionBar?.setDisplayShowTitleEnabled(setToolbarTitle())
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(setToolbarBack())
            activity.supportActionBar?.setDisplayShowHomeEnabled(setToolbarIcon())
            if (setToolbarBack()) {
                setToolbar()?.setNavigationIcon(R.drawable.ic_back)
            }
        }
    }

    override fun setToolbarBack(): Boolean = false

    override fun setToolbarIcon(): Boolean = false

    override fun setToolbarTitle(): Boolean = false
}