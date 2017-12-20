package jermaine.shotclockapp.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import jermaine.shotclockapp.view.listener.VisibilityAnimatorListener


fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun View.animateHide(duration: Long) {
    animate().alpha(0f)
            .setDuration(duration)
            .setListener(VisibilityAnimatorListener(this, View.GONE))
}

fun View.animateShow(duration: Long) {
    visibility = View.VISIBLE
    animate().alpha(1f)
            .setDuration(duration)
            .setListener(VisibilityAnimatorListener(this, View.VISIBLE))
}