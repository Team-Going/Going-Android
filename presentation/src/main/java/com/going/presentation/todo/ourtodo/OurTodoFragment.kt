package com.going.presentation.todo.ourtodo

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.presentation.todo.ourtodo.checkfriends.CheckFriendsActivity
import com.going.presentation.todo.ourtodo.create.OurTodoCreateActivity
import com.going.presentation.todo.ourtodo.create.OurTodoCreateActivity.Companion.EXTRA_NAME
import com.going.presentation.todo.ourtodo.create.OurTodoCreateActivity.Companion.EXTRA_PARTICIPANT_ID
import com.going.presentation.todo.ourtodo.create.OurTodoCreateActivity.Companion.EXTRA_RESULT
import com.going.presentation.todo.ourtodo.invite.FriendInviteDialog
import com.going.presentation.todo.ourtodo.todolist.OurTodoViewPagerAdapter
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OurTodoFragment() : BaseFragment<FragmentOurTodoBinding>(R.layout.fragment_our_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private var _adapter: OurTodoFriendAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var friendInviteDialog: FriendInviteDialog? = null

    private val viewModel by activityViewModels<OurTodoViewModel>()

    private var participantList = listOf<TripParticipantModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initAddTodoBtnListener()
        initItemDecoration()
        initInviteBtnListener()
        initBackBtnClickListener()
        initTripFriendBtnClickListener()
        setMyTripInfo()
        setTabLayout()
        setViewPager()
        observeOurTripInfoState()
    }

    private fun initAdapter() {
        _adapter = OurTodoFriendAdapter()
        binding.rvOurTripFriend.adapter = adapter
    }

    private fun initAddTodoBtnListener() {
        binding.btnOurTodoAddTodo.setOnSingleClickListener {
            val idList: ArrayList<Int> = ArrayList(participantList.map { it.participantId.toInt() })
            val nameList: ArrayList<String> = ArrayList(participantList.map { it.name })
            val resultList: ArrayList<Int> = ArrayList(participantList.map { it.result })
            Intent(activity, OurTodoCreateActivity::class.java).apply {
                putIntegerArrayListExtra(EXTRA_PARTICIPANT_ID, idList)
                putStringArrayListExtra(EXTRA_NAME, nameList)
                putIntegerArrayListExtra(EXTRA_RESULT, resultList)
                putExtra(EXTRA_TRIP_ID, viewModel.tripId)
                startActivity(this)
            }
        }
    }

    private fun initItemDecoration() {
        val itemDeco = OurTodoDecoration(requireContext())
        binding.rvOurTripFriend.addItemDecoration(itemDeco)
    }

    private fun initInviteBtnListener() {
        binding.btnOurTodoAddFriend.setOnSingleClickListener {
            friendInviteDialog = FriendInviteDialog.newInstance(viewModel.inviteCode)
            friendInviteDialog?.show(parentFragmentManager, INVITE_DIALOG)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnOurTodoBack.setOnSingleClickListener {
            activity?.finish()
        }
    }

    private fun initTripFriendBtnClickListener() {
        binding.tvOurTripFriendTitle.setOnSingleClickListener {
            Intent(activity, CheckFriendsActivity::class.java).apply {
                putExtra(EXTRA_TRIP_ID, viewModel.tripId)
                startActivity(this)
            }
        }
    }


    private fun setMyTripInfo() {
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

    private fun observeOurTripInfoState() {
        viewModel.ourTripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    binding.run {
                        setTitleTextWithDay(state.data.day)
                        tvOurTodoTitleUp.text = state.data.title
                        tvOurTodoTitleDate.text = getString(R.string.our_todo_date_form).format(
                            convertDate(state.data.startDate),
                            convertDate(state.data.endDate)
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

    private fun convertDate(date: String): String {
        val splitDate = date.split(".")
        return getString(R.string.our_todo_day_form).format(splitDate[1], splitDate[2])
    }

    private fun setTitleTextWithDay(day: Int) {
        when {
            day > 0 -> {
                binding.tvOurTodoTitleDown.text =
                    getString(R.string.our_todo_title_down_before).format(day)
                setDateTextColor(6, 6)
            }

            day == 0 -> {
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
                        ContextCompat.getColor(
                            requireContext(), R.color.red_500
                        )
                    ), start, length - end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        if (friendInviteDialog?.isAdded == true) friendInviteDialog?.dismiss()
    }

    companion object {
        const val TAB_UNCOMPLETE = "해야 해요"
        const val TAB_COMPLETE = "완료했어요"
        const val INVITE_DIALOG = "inviteDialog"
    }

}