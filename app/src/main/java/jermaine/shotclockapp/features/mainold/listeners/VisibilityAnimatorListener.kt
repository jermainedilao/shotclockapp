package jermaine.shotclockapp.features.mainold.listeners

import android.animation.Animator
import android.view.View


class VisibilityAnimatorListener(private val view: View, private val visibility: Int) : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationEnd(animation: Animator?) {
        view.visibility = visibility
    }

    override fun onAnimationCancel(animation: Animator?) {

    }

    override fun onAnimationStart(animation: Animator?) {

    }
}