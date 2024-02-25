package com.going.presentation.todo.ourtodo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.util.RvItemDecoration
import com.going.presentation.todo.ourtodo.checkfriends.CheckFriendsActivity
import com.going.presentation.todo.create.TodoCreateActivity
import com.going.presentation.todo.ourtodo.friendlist.OurTodoFriendAdapter
import com.going.presentation.todo.ourtodo.invite.FriendInviteDialog
import com.going.presentation.todo.ourtodo.todolist.OurTodoViewPagerAdapter
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
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
class OurTodoFragment() : BaseFragment<FragmentOurTodoBinding>(R.layout.fragment_our_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private var _adapter: OurTodoFriendAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var friendInviteDialog: FriendInviteDialog? = null

    private val viewModel by activityViewModels<OurTodoViewModel>()

    private var participantList = listOf<TripParticipantModel>()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var enableClickRunnable: Runnable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initAddTodoBtnListener()
        initItemDecoration()
        initInviteBtnListener()
        initBackBtnClickListener()
        initTripFriendBtnClickListener()
        setTabLayout()
        setViewPager()
        setViewPagerChangeListener()
        setViewPagerDebounce()
        initOffsetChangedListener()
        observeOurTripInfoState()

    }

    override fun onResume() {
        super.onResume()

        setOurTripInfo()
    }

    private fun initAdapter() {
        _adapter = OurTodoFriendAdapter()
        binding.rvOurTripFriend.adapter = adapter
    }

    private fun initAddTodoBtnListener() {
        binding.btnOurTodoAddTodo.setOnSingleClickListener {
            TodoCreateActivity.createIntent(
                requireContext(),
                viewModel.tripId,
                true,
                ArrayList(participantList.map { it.participantId.toInt() }),
                ArrayList(participantList.map { it.name }),
                ArrayList(participantList.map { it.result })
            ).apply { startActivity(this) }
        }
    }

    private fun initItemDecoration() {
        val itemDeco = RvItemDecoration(requireContext(), 0, 0, 150, 0)
        binding.rvOurTripFriend.addItemDecoration(itemDeco)
    }

    private fun initInviteBtnListener() {
        binding.btnOurTodoAddFriend.setOnSingleClickListener {
            friendInviteDialog = FriendInviteDialog()
            friendInviteDialog?.show(parentFragmentManager, INVITE_DIALOG)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnOurTodoBack.setOnSingleClickListener {
            activity?.finish()
        }
    }

    private fun initTripFriendBtnClickListener() {
        binding.btnOurTripFriend.setOnSingleClickListener {
            Intent(activity, CheckFriendsActivity::class.java).apply {
                putExtra(EXTRA_TRIP_ID, viewModel.tripId)
                startActivity(this)
            }
        }
    }

    private fun setOurTripInfo() {
        arguments?.let {
            viewModel.tripId = it.getLong(EXTRA_TRIP_ID)
        }
        viewModel.getOurTripInfoFromServer()
    }

    private fun setTabLayout() {
        binding.tabOurTodo.apply {
            for (tabName in tabTextList) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
    }

    private fun setViewPager() {
        binding.vpOurTodo.adapter = OurTodoViewPagerAdapter(this)
        binding.vpOurTodo.isUserInputEnabled = false
        TabLayoutMediator(binding.tabOurTodo, binding.vpOurTodo) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    private fun setViewPagerChangeListener() {
        binding.vpOurTodo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.resetListState()
            }
        })
    }

    private fun setViewPagerDebounce() {
        binding.tabOurTodo.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.vpOurTodo.currentItem = tab.position
                disableTabClick()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun disableTabClick() {
        for (i in tabTextList.indices) {
            binding.tabOurTodo.getTabAt(i)?.view?.isClickable = false
        }
        enableClickRunnable = Runnable {
            for (i in tabTextList.indices) {
                binding.tabOurTodo.getTabAt(i)?.view?.isClickable = true
            }
        }
        handler.postDelayed(enableClickRunnable, debounceTime)
    }

    private fun initOffsetChangedListener() {
        binding.appbarOurTodo.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val displayHeight = activity?.getWindowHeight() ?: return@addOnOffsetChangedListener
            val toolbarHeight = binding.toolbarOurTodo.height
            val appBarHeight = appBarLayout.totalScrollRange + verticalOffset
            binding.layoutOurTodoEmpty.layoutParams = (binding.layoutOurTodoEmpty.layoutParams).also {
                it.height = displayHeight - toolbarHeight - appBarHeight - 300
            }

            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                setStatusBarColor(R.color.white_000)
                binding.toolbarOurTodo.setBackgroundColor(colorOf(R.color.white_000))
            } else {
                setStatusBarColor(R.color.gray_50)
                binding.toolbarOurTodo.setBackgroundColor(colorOf(R.color.gray_50))
            }
        }
    }

    fun showEmptyView(show: Boolean) {
        binding.layoutOurTodoEmpty.isVisible = show
    }

    private fun observeOurTripInfoState() {
        viewModel.ourTripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    binding.run {
                        setTitleTextWithDay(state.data.day, state.data.isComplete)
                        tvOurTodoTitleUp.text = state.data.title
                        tvOurTodoTitleDate.text = getString(R.string.our_todo_date_form).format(
                            convertDate(state.data.startDate), convertDate(state.data.endDate)
                        )
                        progressBarOurTodo.progress = state.data.progress
                        tvOurTripInfoPercent.text = state.data.progress.toString() + "%"
                        participantList = state.data.participants
                        adapter.submitList(state.data.participants)
                    }
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun convertDate(date: String) =
        date.split(".").let { splitDate ->
            getString(R.string.our_todo_day_form).format(splitDate[1], splitDate[2])
        }

    private fun setTitleTextWithDay(day: Int, isComplete: Boolean) {
        when {
            day > 0 -> {
                binding.tvOurTodoTitleDown.text = getString(R.string.our_todo_title_down_before).format(day)
                setDateTextColor(6, 6)
            }

            !isComplete -> {
                binding.tvOurTodoTitleDown.text = getString(R.string.our_todo_title_down_during)
                setDateTextColor(0, 4)
            }

            else -> {
                binding.tvOurTodoTitleDown.text = getString(R.string.our_todo_title_down_end)
                setDateTextColor(4, 5)
            }
        }
    }

    private fun setDateTextColor(start: Int, end: Int) {
        binding.tvOurTodoTitleDown.apply {
            text = SpannableStringBuilder(text).apply {
                setSpan(
                    ForegroundColorSpan(
                        colorOf(R.color.red_500)
                    ), start, length - end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        if (friendInviteDialog?.isAdded == true) friendInviteDialog?.dismiss()
        if (::enableClickRunnable.isInitialized) handler.removeCallbacks(enableClickRunnable)
    }

    companion object {
        const val TAB_UNCOMPLETE = "해야 해요"
        const val TAB_COMPLETE = "완료했어요"
        const val INVITE_DIALOG = "inviteDialog"

        const val debounceTime = 300L
    }

}
