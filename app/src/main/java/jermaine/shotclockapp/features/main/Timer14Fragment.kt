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
import jermaine.shotclockapp.databinding.FragmentTimer14Binding
import jermaine.shotclockapp.features.main.listeners.observables.TimerExpirationObserver
import jermaine.shotclockapp.features.main.listeners.observables.TimerObservable
import jermaine.shotclockapp.features.main.listeners.observables.TimerObserver
import java.util.concurrent.TimeUnit

class Timer14Fragment : Fragment(), TimerObserver {
    companion object {
        const val TAG = "Timer14Fragment"
        const val TIMER_14 = 0

        fun newInstance(): Timer14Fragment = Timer14Fragment()
    }

    private lateinit var binding: FragmentTimer14Binding

    private var initialValue: Long = 14
    private var myTimerObservable: TimerObservable? = null
    private var myTimerExpirationObserver: TimerExpirationObserver? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var timerObservable: Observable<Long>

    private var hasResumed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savredInstanceState: Bundle?
    ): View {
        binding = FragmentTimer14Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(requireContext().assets, "fonts/dsdigi.TTF")
            binding.txtTimer.typeface = typeface
            binding.txtTimerBackground.typeface = typeface
        }

        binding.txtTimer.visibility = View.VISIBLE

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
            onTimeStop(::binding.isInitialized)
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

        timerObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(TAG, "onTimePlay: $it")
                initialValue = it
            }
            .subscribe(
                {
                    Log.d(TAG, "onTimePlay: $it")
                    binding.txtTimer.text = it.toString()
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

    private fun onTimeStop(shouldUpdateTextView: Boolean) {
        initialValue = 14
        compositeDisposable?.clear()
        if (shouldUpdateTextView) updateTimerTextViewText()
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
        binding.txtTimer.text = initialValue.toString()
    }
}