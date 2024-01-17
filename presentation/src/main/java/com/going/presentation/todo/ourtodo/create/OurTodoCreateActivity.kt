package com.going.presentation.todo.ourtodo.create

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.NameState
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOurTodoCreateBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OurTodoCreateActivity :
    BaseActivity<ActivityOurTodoCreateBinding>(R.layout.activity_our_todo_create) {

    private val viewModel by viewModels<OurTodoCreateViewModel>()

    private var _adapter: TodoCreateNameAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var ourTodoCreateBottomSheet: OurTodoCreateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initNameListAdapter()
        initDateClickListener()
        initFinishBtnListener()
        initBackBtnListener()
        getTripId()
        setParticipantList()
        observeTodoCreateState()
        observeTextLength()
        observeMemoLength()
        observeDateEmpty()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initNameListAdapter() {
        _adapter = TodoCreateNameAdapter(false) { position ->
            viewModel.participantList[position].isSelected =
                !viewModel.participantList[position].isSelected
            viewModel.checkIsFinishAvailable()
        }
        binding.rvOurTodoCreatePerson.adapter = adapter
    }

    private fun initDateClickListener() {
        binding.etOurTodoCreateDate.setOnSingleClickListener {
            ourTodoCreateBottomSheet = OurTodoCreateBottomSheet()
            ourTodoCreateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnOurTodoMemoFinish.setOnSingleClickListener {
            viewModel.participantList = adapter.currentList.filter { it.isSelected }
            viewModel.postToCreateTodoFromServer()
        }
    }

    private fun initBackBtnListener() {
        binding.btnOurTodoCreateBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun getTripId() {
        viewModel.tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
    }

    private fun setParticipantList() {
        val idList = intent.getIntegerArrayListExtra(EXTRA_PARTICIPANT_ID)?.toList() ?: listOf()
        val nameList = intent.getStringArrayListExtra(EXTRA_NAME)?.toList() ?: listOf()
        val resultList = intent.getIntegerArrayListExtra(EXTRA_RESULT)?.toList() ?: listOf()
        viewModel.totalParticipantList =
            idList.zip(nameList).zip(resultList) { (id, name), result ->
                TripParticipantModel(id.toLong(), name, result)
            }
        viewModel.participantList = viewModel.totalParticipantList
        adapter.submitList(viewModel.totalParticipantList)
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
                binding.tvOurTodoTodoCounter,
            ) { background ->
                binding.etOurTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeMemoLength() {
        viewModel.isMemoAvailable.observe(this) { state ->
            setColors(
                state,
                binding.tvOurTodoMemoCounter,
            ) { background ->
                binding.etOurTodoCreateMemo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeDateEmpty() {
        viewModel.endDate.observe(this) { text ->
            if (text.isEmpty()) {
                binding.etOurTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray200_line)
            } else {
                binding.etOurTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray700_line)
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

    private fun setBackgroundColor(background: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            this.resources,
            background,
            theme,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
        if (ourTodoCreateBottomSheet?.isAdded == true) ourTodoCreateBottomSheet?.dismiss()
    }

    companion object {
        private const val DATE_BOTTOM_SHEET = "DATE_BOTTOM_SHEET"

        const val EXTRA_PARTICIPANT_ID = "EXTRA_PARTICIPANT_ID"
        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA_RESULT = "EXTRA_RESULT"
    }
}
