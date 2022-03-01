package jermaine.shotclockapp.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import jermaine.shotclockapp.features.main.TimerFragment
import jermaine.shotclockapp.utils.INITIAL_TIME_14
import jermaine.shotclockapp.utils.INITIAL_TIME_24
import jermaine.shotclockapp.utils.PAGE_POSITION_TIMER_14
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class TimerPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val initialTime = if (position == PAGE_POSITION_TIMER_14) {
            INITIAL_TIME_14
        } else {
            INITIAL_TIME_24
        }
        return TimerFragment.newInstance(initialTime)
    }
}