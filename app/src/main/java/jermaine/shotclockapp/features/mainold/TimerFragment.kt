package jermaine.shotclockapp.features.mainold

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import jermaine.shotclockapp.databinding.FragmentTimerBinding
import jermaine.shotclockapp.extension.argument
import jermaine.shotclockapp.features.mainold.cancellation.TimerCompleted
import jermaine.shotclockapp.utils.tickerFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds


@FlowPreview
@ExperimentalCoroutinesApi
class TimerFragment : Fragment() {

    companion object {
        const val TAG = "TimerFragment"

        fun newInstance(initialTime: Int): TimerFragment = TimerFragment().apply {
            this.initialTime = initialTime
        }
    }

    private var initialTime by argument<Int>()
    private var currentTime: Int = 0
    private var timer: Job? = null

    private lateinit var binding: FragmentTimerBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: TimerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater)
        binding.txtTimer.text = initialTime.toString()
        binding.txtTimer.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[TimerViewModel::class.java]

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(requireContext().assets, "fonts/dsdigi.TTF")
            binding.txtTimer.typeface = typeface
            binding.txtTimerBackground.typeface = typeface
        }

        setupVmObservers()

        viewModel.init(initialTime)
    }

    override fun onPause() {
        timer?.cancel()
        viewModel.resetTimer()
        super.onPause()
    }

    private fun setupVmObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    mainViewModel.state.collect(::handleMainState)
                }
                launch {
                    viewModel.state.collect(::handleState)
                }
            }
        }
    }

    private fun handleState(state: TimerState): Unit = with(state) {
        this@TimerFragment.currentTime = currentTime
        binding.txtTimer.text = currentTime.toString()
    }

    private fun handleMainState(event: MainState): Unit = with(event) {
        if (play) {
            startTimer()
        } else {
            timer?.cancel()
        }

        (events.firstOrNull() as? MainEvents.TimerEvent)?.let {
            when (it) {
                MainEvents.TimerEvent.DecreaseTime -> {
                    viewModel.decreaseTime()
                }
                MainEvents.TimerEvent.IncreaseTime -> {
                    viewModel.increaseTime()
                }
                MainEvents.TimerEvent.Reset -> {
                    viewModel.resetTimer()
                }
            }
            mainViewModel.eventConsumed(it.eventId)
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = viewLifecycleOwner.lifecycleScope.launch {
            tickerFlow(period = 1.seconds, initialDelay = 1.seconds)
                .flatMapConcat {
                    flowOf(currentTime - 1)
                }
                .flowOn(Dispatchers.Default)
                .onEach {
                    if (it <= 0) {
                        timer?.cancel(TimerCompleted)
                    }
                }
                .onCompletion {
                    if (it is TimerCompleted) {
                        viewModel.resetTimer()
                        mainViewModel.stop()
                    }
                }
                .collect {
                    viewModel.onTick(it)
                }
        }
    }
}