package jermaine.shotclockapp.features.main

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import jermaine.shotclockapp.R
import jermaine.shotclockapp.databinding.ActivityMainBinding
import jermaine.shotclockapp.extension.animateHide
import jermaine.shotclockapp.extension.animateShow
import jermaine.shotclockapp.extension.getThemeType
import jermaine.shotclockapp.extension.startSettingsActivity
import jermaine.shotclockapp.features.main.adapter.TimerPagerAdapter
import jermaine.shotclockapp.features.main.listeners.observables.TimerExpirationObserver
import jermaine.shotclockapp.features.main.listeners.observables.TimerObservable
import jermaine.shotclockapp.features.main.listeners.observables.TimerObserver

class MainActivity : AppCompatActivity(), TimerObservable, TimerExpirationObserver {

    companion object {
        const val TAG = "MainActivity"
        const val THEME_LIGHT = "theme:light"
        const val THEME_DARK = "theme:dark"
    }

    private val list = listOf(Timer14Fragment.TIMER_14, Timer24Fragment.TIMER_24)

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TimerPagerAdapter
    private var shortAnimationDuration: Long = 0
    private var observer: TimerObserver? = null

    /**
     * True/false when time is running/paused.
     **/
    private var playStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleThemes()

        shortAnimationDuration =
            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        initializeToolbar()
        initializeTextViews()
        initializeViewPager()
    }

    private fun handleThemes() {
        when (getThemeType()) {
            THEME_LIGHT -> {
                setTheme(R.style.AppThemeLight)
            }
            THEME_DARK -> {
                setTheme(R.style.AppThemeDark)
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeToolbar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initializeTextViews() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(assets, "fonts/dsdigi.TTF")
            binding.txtPrevPage.typeface = typeface
            binding.txtNextPage.typeface = typeface
        }

        binding.txtPrevPage.setOnClickListener {
            binding.txtPrevPage.animateHide(shortAnimationDuration)
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1, true)
        }
        binding.txtNextPage.setOnClickListener {
            binding.txtNextPage.animateHide(shortAnimationDuration)
            binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1, true)
        }
    }

    private fun initializeViewPager() {
        adapter = TimerPagerAdapter(supportFragmentManager, list)
        binding.viewPager.pageMargin = 0
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> hidePrevNextPageTextViews()
                    ViewPager.SCROLL_STATE_IDLE -> handlePageSelected(binding.viewPager.currentItem)
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                observer?.onReset()
            }
        })
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 1
    }

    private fun handlePageSelected(position: Int) {
        when (adapter.getItems()[position]) {
            Timer14Fragment.TIMER_14 -> {
                hidePrevPageTextView()
                showNextPagerTextView()
            }
            Timer24Fragment.TIMER_24 -> {
                showPrevPageTextView()
                hideNextPageTextView()
            }
        }
    }

    private fun hidePrevNextPageTextViews() {
        hidePrevPageTextView()
        hideNextPageTextView()
    }

    private fun hidePrevPageTextView() {
        binding.txtPrevPage.animateHide(shortAnimationDuration)
    }

    private fun hideNextPageTextView() {
        binding.txtNextPage.animateHide(shortAnimationDuration)
    }

    private fun showPrevPageTextView() {
        binding.txtPrevPage.animateShow(shortAnimationDuration)
    }

    private fun showNextPagerTextView() {
        binding.txtNextPage.animateShow(shortAnimationDuration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ")
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ")
        when (item.itemId) {
            R.id.action_settings -> {
                startSettingsActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onMinus1Click(view: View) {
        observer?.onMinus1()
    }

    fun onPlus1Click(view: View) {
        observer?.onPlus1()
    }

    fun onResetClick(view: View) {
        observer?.onReset()
    }

    fun onPlayClick(view: View) {
        handleOnPlayClick()
    }

    private fun handleOnPlayClick() {
        if (!playStatus) {
            start()
        } else {
            stop()
        }
    }

    private fun start() {
        playStatus = true
        binding.btnStopPlay.setImageResource(R.drawable.ic_pause_white_24dp)

        observer?.onTimePlay()
    }

    private fun stop() {
        playStatus = false
        binding.btnStopPlay.setImageResource(R.drawable.ic_play_arrow_white_44dp)

        observer?.onTimePause()
    }

    override fun setObserver(observer: TimerObserver) {
        this.observer = observer

        if (playStatus) {
            start()
        } else {
            stop()
        }
    }

    override fun onExpire() {
        handleOnPlayClick()
        observer?.onReset()
    }
}
