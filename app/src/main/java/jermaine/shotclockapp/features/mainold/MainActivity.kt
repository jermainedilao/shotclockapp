package jermaine.shotclockapp.features.mainold

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.viewpager2.widget.ViewPager2
import jermaine.shotclockapp.R
import jermaine.shotclockapp.databinding.ActivityMainBinding
import jermaine.shotclockapp.extension.animateHide
import jermaine.shotclockapp.extension.animateShow
import jermaine.shotclockapp.extension.getThemeType
import jermaine.shotclockapp.extension.startSettingsActivity
import jermaine.shotclockapp.features.mainold.adapter.TimerPagerAdapter
import jermaine.shotclockapp.utils.PAGE_POSITION_TIMER_14
import jermaine.shotclockapp.utils.PAGE_POSITION_TIMER_24
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val THEME_LIGHT = "theme:light"
        const val THEME_DARK = "theme:dark"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: TimerPagerAdapter
    private var shortAnimationDuration: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleThemes()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        shortAnimationDuration =
            resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        setupViews()
        setupVmObservers()
    }

    private fun setupVmObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.state.collect(::handleState)
                }
            }
        }
    }

    private fun handleState(state: MainState): Unit = with(state) {
        binding.btnStopPlay.setImageResource(
            if (play) {
                R.drawable.ic_pause_white_24dp
            } else {
                R.drawable.ic_play_arrow_white_44dp
            }
        )
    }

    private fun setupViews() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupTextViews()
        setupViewPager()
    }

    private fun setupTextViews() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val typeface = Typeface.createFromAsset(assets, "fonts/dsdigi.TTF")
            binding.txtPrevPage.typeface = typeface
            binding.txtNextPage.typeface = typeface
        }

        binding.txtPrevPage.setOnClickListener {
            binding.txtPrevPage.animateHide(shortAnimationDuration)
            binding.viewPager.setCurrentItem(PAGE_POSITION_TIMER_14, true)
        }
        binding.txtNextPage.setOnClickListener {
            binding.txtNextPage.animateHide(shortAnimationDuration)
            binding.viewPager.setCurrentItem(PAGE_POSITION_TIMER_24, true)
        }
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

    private fun setupViewPager() {
        adapter = TimerPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                resetTimer()
            }

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> hidePrevNextPageTextViews()
                    ViewPager2.SCROLL_STATE_IDLE -> handlePageSelected(binding.viewPager.currentItem)
                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(PAGE_POSITION_TIMER_24, false)
    }

    private fun resetTimer() {
        viewModel.reset()
    }

    private fun handlePageSelected(position: Int) {
        when (position) {
            PAGE_POSITION_TIMER_14 -> {
                hidePrevPageTextView()
                showNextPagerTextView()
            }
            PAGE_POSITION_TIMER_24 -> {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startSettingsActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onMinus1Click(view: View) {
        viewModel.decreaseTime()
    }

    fun onPlus1Click(view: View) {
        viewModel.increaseTime()
    }

    fun onResetClick(view: View) {
        resetTimer()
    }

    fun onPlayClick(view: View) {
        viewModel.onPlayClick()
    }
}
