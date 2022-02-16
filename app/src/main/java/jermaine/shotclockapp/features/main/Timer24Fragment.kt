package jermaine.shotclockapp.features.main

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jermaine.shotclockapp.R
import jermaine.shotclockapp.features.main.listeners.observables.TimerExpirationObserver
import jermaine.shotclockapp.features.main.listeners.observables.TimerObservable
import jermaine.shotclockapp.features.main.listeners.observables.TimerObserver
import kotlinx.android.synthetic.main.fragment_timer_24.*
import java.util.concurrent.TimeUnit


class Timer24Fragment : Fragment(), TimerObserver {

    companion object {
        val TAG = "Timer24Fragment"
        val TIMER_24 = 1

        fun newInstance(): Timer24Fragment = Timer24Fragment()
    }

    private var initialValue: Long = 24
    private var myTimerObservable: TimerObservable? = null
    private var myTimerExpirationObserver: TimerExpirationObserver? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var timerObservable: Observable<Long>

    private var hasResumed: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timer_24, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(requireContext().assets, "fonts/dsdigi.TTF")
            timer_textView.typeface = typeface
            timer_background_textView.typeface = typeface
        }

        timer_textView.visibility = View.VISIBLE
        compositeDisposable = CompositeDisposable()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TimerObservable) {
            myTimerObservable = context
            initializeMyTimerObservable(hasResumed)
        }

        if (context is TimerExpirationObserver) {
            myTimerExpirationObserver = context
        }
    }

    override fun onResume() {
        super.onResume()
        hasResumed = true
        initializeMyTimerObservable(true)
    }

    override fun onPause() {
        hasResumed = false
        initializeMyTimerObservable(false)
        super.onPause()
    }

    private fun initializeMyTimerObservable(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            myTimerObservable?.setObserver(this)
        } else {
            onTimeStop()
        }
    }

    override fun onTimePlay() {
        Log.d(TAG, "onTimePlay: ")

        if (!isVisible) return

        timerObservable = Observable.interval(1, 1, TimeUnit.SECONDS)
            .concatMap {
                Log.d(TAG, "onTimePlay: ${initialValue - 1}")
                Observable.just(initialValue - 1)
            }
            .takeWhile {
                it >= 0
            }

        timerObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(Timer14Fragment.TAG, "onTimePlay: $it")
                initialValue = it
            }
            .subscribe(
                {
                    Log.d(TAG, "onTimePlay: $it")
                    timer_textView.text = it.toString()
                },
                {
                    Log.e(TAG, "onTimePlay: onError", it)
                },
                {
                    Log.d(TAG, "onTimePlay: onComplete")
                    myTimerExpirationObserver?.onExpire()
                }
            )
            .apply { compositeDisposable?.add(this) }
    }

    private fun onTimeStop() {
        initialValue = 24
        compositeDisposable?.clear()
        updateTimerTextViewText()
    }

    override fun onTimePause() {
        Log.d(TAG, "onTimePause: $initialValue")

        if (!isVisible) return
        compositeDisposable?.clear()
    }

    override fun onPlus1() {
        if (initialValue < 24) {
            initialValue += 1
        }
        updateTimerTextViewText()
    }

    override fun onMinus1() {
        if (initialValue > 1) {
            initialValue -= 1
        }
        updateTimerTextViewText()
    }

    override fun onReset() {
        initialValue = 24
        updateTimerTextViewText()
    }

    private fun updateTimerTextViewText() {
        timer_textView?.text = initialValue.toString()
    }
}