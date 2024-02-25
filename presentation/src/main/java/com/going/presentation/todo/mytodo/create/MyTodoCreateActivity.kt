package com.going.presentation.todo.mytodo.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityMyTodoCreateBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.presentation.todo.create.OurTodoCreateActivity.Companion.EXTRA_PARTICIPANT_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.drawableOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyTodoCreateActivity :
    BaseActivity<ActivityMyTodoCreateBinding>(R.layout.activity_my_todo_create) {

    private val viewModel by viewModels<MyTodoCreateViewModel>()

    private var myTodoCreateBottomSheet: MyTodoCreateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initDateClickListener()
        initFinishBtnListener()
        initBackBtnListener()
        getId()
        observeTodoCreateState()
        observeTextLength()
        observeMemoLength()
        observeDateEmpty()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initDateClickListener() {
        binding.etMyTodoCreateDate.setOnSingleClickListener {
            myTodoCreateBottomSheet = MyTodoCreateBottomSheet()
            myTodoCreateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnMyTodoMemoFinish.setOnSingleClickListener {
            viewModel.postToCreateTodoFromServer()
        }
    }

    private fun initBackBtnListener() {
        binding.btnMyTodoCreateBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun getId() {
        viewModel.tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
        viewModel.participantId = intent.getLongExtra(EXTRA_PARTICIPANT_ID, 0)
    }

    private fun observeTodoCreateState() {
        viewModel.todoCreateState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.todo_create_toast))
                    finish()
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun observeTextLength() {
        viewModel.isTodoAvailable.observe(this) { state ->
            setColors(
                state,
                binding.tvMyTodoTodoCounter,
            ) { background ->
                binding.etMyTodoCreateTodo.background = drawableOf(background)
            }
        }
    }

    private fun observeMemoLength() {
        viewModel.isMemoAvailable.observe(this) { state ->
            setColors(
                state,
                binding.tvMyTodoMemoCounter,
            ) { background ->
                binding.etMyTodoCreateMemo.background = drawableOf(background)
            }
        }
    }

    private fun observeDateEmpty() {
        viewModel.endDate.observe(this) { text ->
            if (text.isEmpty()) {
                binding.etMyTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray200_line)
            } else {
                binding.etMyTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray700_line)
            }
        }
    }

    private fun setColors(
        state: NameState,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when (state) {
            NameState.Empty -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            NameState.Success -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            NameState.Blank -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            NameState.OVER -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myTodoCreateBottomSheet?.isAdded == true) myTodoCreateBottomSheet?.dismiss()
    }

    companion object {
        private const val DATE_BOTTOM_SHEET = "DATE_BOTTOM_SHEET"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            participantId: Long
        ): Intent = Intent(context, MyTodoCreateActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, tripId)
            putExtra(EXTRA_PARTICIPANT_ID, participantId)
        }
    }
}
