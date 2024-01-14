package com.going.presentation.todo.ourtodo.checkfriends

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.presentation.todo.ourtodo.OurTodoFriendAdapter
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class CheckFriendsActivity :
    BaseActivity<ActivityCheckFriendsBinding>(R.layout.activity_check_friends) {

    private var _adapter: OurTodoFriendAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<CheckFriendsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackClickListener()
        initAdapter()
        setProgressBarStatus()

    }

    private fun initBackClickListener() {
        binding.btnCheckFriendsBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initAdapter() {
        _adapter = OurTodoFriendAdapter()
        binding.rvCheckFriendsMember.adapter = adapter
        adapter.submitList(viewModel.mockParticipantsList)
    }

    private fun setProgressBarStatus() {
        // styleA의 평균 값이 1이라고 가정
        binding.progressBarCheckFriends1.progress = 1
        if (binding.progressBarCheckFriends1.progress <= 2) {
            // 평균 값이 0 ~2면 1 더하기
            binding.progressBarCheckFriends1.progress = 1 + 1
            binding.progressBarCheckFriends2.progress = 2 + 1
            binding.progressBarCheckFriends3.progress = 0 + 1
        }

        // styleD의 평균 값이 3이라고 가정
        binding.progressBarCheckFriends4.progress = 3
        if (binding.progressBarCheckFriends4.progress > 2) {
            // 프로그레스바 반대 방향으로 적용
            binding.progressBarCheckFriends4.visibility = View.INVISIBLE
            binding.progressBarCheckFriends4Revert.visibility = View.VISIBLE

            binding.progressBarCheckFriends5.visibility = View.INVISIBLE
            binding.progressBarCheckFriends5Revert.visibility = View.VISIBLE

            // 평균 값이 4 ~5 라면 2 빼기
            binding.progressBarCheckFriends4Revert.progress = 3 - 2
            binding.progressBarCheckFriends5Revert.progress = 4 - 2
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

}