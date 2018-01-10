package jermaine.shotclockapp.view.activity

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import jermaine.shotclockapp.R
import jermaine.shotclockapp.extension.animateHide
import jermaine.shotclockapp.extension.animateShow
import jermaine.shotclockapp.extension.getThemeType
import jermaine.shotclockapp.extension.startSettingsActivity
import jermaine.shotclockapp.view.adapter.TimerPagerAdapter
import jermaine.shotclockapp.view.fragment.Timer14Fragment
import jermaine.shotclockapp.view.fragment.Timer24Fragment
import jermaine.shotclockapp.view.listener.observables.TimerExpirationObserver
import jermaine.shotclockapp.view.listener.observables.TimerObservable
import jermaine.shotclockapp.view.listener.observables.TimerObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TimerObservable, TimerExpirationObserver {

    companion object {
        val TAG = "MainActivity"
        val THEME_LIGHT = "theme:light"
        val THEME_DARK = "theme:dark"
    }

    private val list = listOf(Timer14Fragment.TIMER_14, Timer24Fragment.TIMER_24)

    private lateinit var adapter: TimerPagerAdapter
    private var shortAnimationDuration: Long = 0

    private var observer: TimerObserver? = null

    /**
     * True/false when time is running/paused.
     **/
    private var playStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleThemese()

        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        initializeToolbar()
        initializeTextViews()
        initializeViewPager()
    }

    private fun handleThemese() {
        when (getThemeType()) {
            THEME_LIGHT -> {
                setTheme(R.style.AppThemeLight)
            }
            THEME_DARK -> {
                setTheme(R.style.AppThemeDark)
            }
        }
        setContentView(R.layout.activity_main)
    }

    private fun initializeToolbar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initializeTextViews() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(assets, "fonts/dsdigi.TTF")
            prev_page_text_view.typeface = typeface
            next_page_text_view.typeface = typeface
        }

        prev_page_text_view.setOnClickListener {
            prev_page_text_view.animateHide(shortAnimationDuration)
            view_pager.setCurrentItem(view_pager.currentItem - 1, true)
        }
        next_page_text_view.setOnClickListener {
            next_page_text_view.animateHide(shortAnimationDuration)
            view_pager.setCurrentItem(view_pager.currentItem + 1, true)
        }
    }

    private fun initializeViewPager() {
        adapter = TimerPagerAdapter(supportFragmentManager, list)
        view_pager.pageMargin = 0
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> hidePrevNextPageTextViews()
                    ViewPager.SCROLL_STATE_IDLE -> handlePageSelected(view_pager.currentItem)
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                observer?.onReset()
            }
        })
        view_pager.adapter = adapter
        view_pager.currentItem = 1
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
        prev_page_text_view.animateHide(shortAnimationDuration)
    }

    private fun hideNextPageTextView() {
        next_page_text_view.animateHide(shortAnimationDuration)
    }

    private fun showPrevPageTextView() {
        prev_page_text_view.animateShow(shortAnimationDuration)
    }

    private fun showNextPagerTextView() {
        next_page_text_view.animateShow(shortAnimationDuration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ")
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ")
        when (item?.itemId) {
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
        stop_play_button.setImageResource(R.drawable.ic_pause_white_24dp)

        observer?.onTimePlay()
    }

    private fun stop() {
        playStatus = false
        stop_play_button.setImageResource(R.drawable.ic_play_arrow_white_44dp)

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
