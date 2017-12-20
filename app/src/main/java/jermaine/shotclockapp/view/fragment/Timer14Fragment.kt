package jermaine.shotclockapp.view.fragment

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import jermaine.shotclockapp.R
import jermaine.shotclockapp.view.listener.observables.TimerExpirationObserver
import jermaine.shotclockapp.view.listener.observables.TimerObservable
import jermaine.shotclockapp.view.listener.observables.TimerObserver
import kotlinx.android.synthetic.main.fragment_timer_14.*
import java.util.concurrent.TimeUnit


class Timer14Fragment : Fragment(), TimerObserver {
    companion object {
        val TAG = "Timer14Fragment"
        val TIMER_14 = 0

        fun newInstance(): Timer14Fragment = Timer14Fragment()
    }

    private var initialValue: Long = 14
    private var myTimerObservable: TimerObservable? = null
    private var myTimerExpirationObserver: TimerExpirationObserver? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var timerObservable: Observable<Long>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savredInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_timer_14, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(context.assets, "fonts/dsdigi.TTF")
            timer_textView.typeface = typeface
            timer_background_textView.typeface = typeface
        }

        timer_textView.visibility = View.VISIBLE

        compositeDisposable = CompositeDisposable()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is TimerObservable) {
            myTimerObservable = context
            initializeMyTimerObservable(userVisibleHint)
        }

        if (context is TimerExpirationObserver) {
            myTimerExpirationObserver = context
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        initializeMyTimerObservable(isVisibleToUser)
    }

    private fun initializeMyTimerObservable(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            myTimerObservable?.setObserver(this)
        } else {
            onTimeStop()
        }
    }

    override fun onTimePlay() {
        Log.d(TAG, "onTimePlay: $initialValue")

        if (!isVisible) return

        timerObservable = Observable.interval(1, 1, TimeUnit.SECONDS)
                .concatMap {
                    Log.d(Timer24Fragment.TAG, "onTimePlay: ${initialValue - 1}")
                    Observable.just(initialValue - 1)
                }
                .takeWhile {
                    it >= 0
                }

        compositeDisposable?.add(timerObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    Log.d(TAG, "onTimePlay: $it")
                    initialValue = it
                }
                .subscribe({
                    Log.d(TAG, "onTimePlay: $it")
                    timer_textView.text = it.toString()
                }, {
                    Log.e(TAG, "onTimePlay: onError", it)
                }, {
                    Log.d(TAG, "onTimePlay: onComplete")
                    myTimerExpirationObserver?.onExpire()
                })
        )
    }

    private fun onTimeStop() {
        initialValue = 14
        compositeDisposable?.clear()
        updateTimerTextViewText()
    }

    override fun onTimePause() {
        Log.d(TAG, "onTimePause: $initialValue")

        if (!isVisible) return
        compositeDisposable?.clear()
    }

    override fun onPlus1() {
        if (initialValue < 14) {
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
        initialValue = 14
        updateTimerTextViewText()
    }

    private fun updateTimerTextViewText() {
        timer_textView?.text = initialValue.toString()
    }
}