package com.going.presentation.todo.mytodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoBinding
import com.going.presentation.profile.ProfileActivity
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.presentation.todo.create.TodoCreateActivity
import com.going.presentation.todo.mytodo.todolist.MyTodoViewPagerAdapter
import com.going.presentation.todo.ourtodo.OurTodoFragment.Companion.debounceTime
import com.going.ui.base.BaseFragment
import com.going.ui.state.UiState
import com.going.ui.extension.colorOf
import com.going.ui.extension.getWindowHeight
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.setStatusBarColor
import com.going.ui.extension.toast
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.abs

@AndroidEntryPoint
class MyTodoFragment() : BaseFragment<FragmentMyTodoBinding>(R.layout.fragment_my_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private val viewModel by activityViewModels<MyTodoViewModel>()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var enableClickRunnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAddTodoListener()
        initBackBtnClickListener()
        initProfileBtnClickListener()
        setMyTripInfo()
        setTabLayout()
        setViewPager()
        setViewPagerChangeListener()
        setViewPagerDebounce()
        setTodoCountText()
        initEmptyViewHeight()
        initOffsetChangedListener()
        observeMyTripInfoState()
        observeTotalUncompletedTodoCount()

    }

    private fun initAddTodoListener() {
        binding.btnMyTodoAddTodo.setOnSingleClickListener {
            TodoCreateActivity.createIntent(
                requireContext(),
                viewModel.tripId,
                false,
                arrayListOf(viewModel.participantId)
            ).apply { startActivity(this) }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnMyTodoBack.setOnSingleClickListener {
            activity?.finish()
        }
    }

    private fun initProfileBtnClickListener() {
        binding.btnMyTodoProfile.setOnSingleClickListener {
            Intent(activity, ProfileActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setMyTripInfo() {
        arguments?.let {
            viewModel.tripId = it.getLong(EXTRA_TRIP_ID)
        }
        viewModel.getMyTripInfoFromServer()
    }

    private fun setTabLayout() {
        binding.tabMyTodo.apply {
            for (tabName in tabTextList) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
    }

    private fun setViewPager() {
        binding.vpMyTodo.adapter = MyTodoViewPagerAdapter(this)
        binding.vpMyTodo.isUserInputEnabled = false
        TabLayoutMediator(binding.tabMyTodo, binding.vpMyTodo) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    private fun setViewPagerChangeListener() {
        binding.vpMyTodo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.resetListState()
            }
        })
    }

    private fun setViewPagerDebounce() {
        binding.tabMyTodo.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpMyTodo.currentItem = tab.position
                disableTabClick()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun disableTabClick() {
        for (i in tabTextList.indices) {
            binding.tabMyTodo.getTabAt(i)?.view?.isClickable = false
        }
        enableClickRunnable = Runnable {
            for (i in tabTextList.indices) {
                binding.tabMyTodo.getTabAt(i)?.view?.isClickable = true
            }
        }
        handler.postDelayed(enableClickRunnable, debounceTime)
    }

    private fun setTodoCountText() {
        setTodoCount(viewModel.totalUncompletedTodoCount.value)
    }

    private fun initEmptyViewHeight() {
        binding.appbarMyTodo.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.appbarMyTodo.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val displayHeight = activity?.getWindowHeight() ?: return
                val toolbarHeight = binding.toolbarMyTodo.height
                val appBarHeight = binding.appbarMyTodo.totalScrollRange
                binding.layoutMyTodoEmpty.layoutParams =
                    (binding.layoutMyTodoEmpty.layoutParams).also {
                        it.height = displayHeight - toolbarHeight - appBarHeight - 300
                    }
            }
        })
    }

    private fun initOffsetChangedListener() {
        binding.appbarMyTodo.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val displayHeight = activity?.getWindowHeight() ?: return@addOnOffsetChangedListener
            val toolbarHeight = binding.toolbarMyTodo.height
            val appBarHeight = appBarLayout.totalScrollRange + verticalOffset
            binding.layoutMyTodoEmpty.layoutParams = (binding.layoutMyTodoEmpty.layoutParams).also {
                it.height = displayHeight - toolbarHeight - appBarHeight - 300
            }

            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                setStatusBarColor(R.color.white_000)
                binding.toolbarMyTodo.setBackgroundColor(colorOf(R.color.white_000))
            } else {
                setStatusBarColor(R.color.gray_50)
                binding.toolbarMyTodo.setBackgroundColor(colorOf(R.color.gray_50))
            }
        }
    }

    fun showEmptyView(show: Boolean) {
        binding.layoutMyTodoEmpty.isVisible = show
    }

    private fun observeMyTripInfoState() {
        viewModel.myTripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> binding.tvMyTodoTitleUp.text = state.data.title

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun observeTotalUncompletedTodoCount() {
        viewModel.totalUncompletedTodoCount.flowWithLifecycle(lifecycle).onEach { count ->
            setTodoCount(count)
        }.launchIn(lifecycleScope)
    }

    private fun setTodoCount(count: Int) {
        binding.tvMyTodoTitleDown.apply {
            text = SpannableStringBuilder(
                getString(R.string.my_todo_tv_title_down).format(count)
            ).apply {
                setSpan(
                    ForegroundColorSpan(
                        colorOf(R.color.red_500)
                    ), length - 3, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::enableClickRunnable.isInitialized) handler.removeCallbacks(enableClickRunnable)
    }

    companion object {
        const val TAB_UNCOMPLETE = "해야 해요"
        const val TAB_COMPLETE = "완료했어요"
    }

}