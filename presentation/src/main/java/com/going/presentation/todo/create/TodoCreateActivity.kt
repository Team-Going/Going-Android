package com.going.presentation.todo.create

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.NameState
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoCreateBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoCreateActivity : BaseActivity<ActivityTodoCreateBinding>(R.layout.activity_todo_create) {

    private val viewModel by viewModels<TodoCreateViewModel>()

    private var _adapter: TripParticipantAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var todoCreateBottomSheet: TodoCreateBottomSheet? = null

    private var isOurTodo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setTodoCreateType()
        initDateClickBtnListener()
        initFinishBtnListener()
        initBackBtnListener()
        getTripInfoId()
        setTodoCreateType()
        observeTodoCreateState()
        observeTextLength()
        observeMemoLength()
        observeDateEmpty()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initDateClickBtnListener() {
        binding.etTodoCreateDate.setOnSingleClickListener {
            todoCreateBottomSheet = TodoCreateBottomSheet()
            todoCreateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnTodoMemoFinish.setOnSingleClickListener {
            if (isOurTodo) viewModel.participantIdList =
                adapter.currentList.filter { it.isSelected }.map { it.participantId }
            viewModel.postToCreateTodoFromServer()
        }
    }

    private fun initBackBtnListener() {
        binding.btnTodoCreateBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun getTripInfoId() {
        with(viewModel) {
            tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
            participantIdList =
                intent.getIntegerArrayListExtra(EXTRA_PARTICIPANT_ID)?.map { it.toLong() }
                    ?: listOf()
        }
    }

    private fun setTodoCreateType() {
        isOurTodo = intent.getBooleanExtra(EXTRA_IS_OUR_TODO, true)
        setParticipantByType()
        if (isOurTodo) {
            initOurTodoNameListAdapter()
            setOurTodoParticipantList()
        }
    }

    private fun setParticipantByType() {
        if (!isOurTodo) {
            with(binding) {
                rvOurTodoCreatePerson.visibility = View.INVISIBLE
                layoutMyTodoCreatePerson.visibility = View.VISIBLE
            }
        }
    }

    private fun initOurTodoNameListAdapter() {
        _adapter = TripParticipantAdapter(false) { position ->
            viewModel.participantModelList[position].also { it.isSelected = !it.isSelected }
            viewModel.checkIsFinishAvailable()
        }
        binding.rvOurTodoCreatePerson.adapter = adapter
    }

    private fun setOurTodoParticipantList() {
        viewModel.participantModelList =
            viewModel.participantIdList.zip(
                intent.getStringArrayListExtra(EXTRA_NAME) ?: listOf()
            ).zip(
                intent.getIntegerArrayListExtra(EXTRA_RESULT) ?: listOf()
            ) { (id, name), result ->
                TripParticipantModel(id, name, result)
            }
        adapter.submitList(viewModel.participantModelList)
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
                binding.tvTodoTodoCounter,
            ) { background ->
                binding.etTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeMemoLength() {
        viewModel.isMemoAvailable.observe(this) { state ->
            setColors(
                state,
                binding.tvOurTodoMemoCounter,
            ) { background ->
                binding.etTodoCreateMemo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeDateEmpty() {
        viewModel.endDate.observe(this) { text ->
            if (text.isEmpty()) {
                binding.etTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray200_line)
            } else {
                binding.etTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray700_line)
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
        if (todoCreateBottomSheet?.isAdded == true) todoCreateBottomSheet?.dismiss()
    }

    companion object {
        private const val DATE_BOTTOM_SHEET = "DATE_BOTTOM_SHEET"

        private const val EXTRA_IS_OUR_TODO = "EXTRA_IS_OUR_TODO"
        private const val EXTRA_PARTICIPANT_ID = "EXTRA_PARTICIPANT_ID"
        private const val EXTRA_NAME = "EXTRA_NAME"
        private const val EXTRA_RESULT = "EXTRA_RESULT"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            isOurTodo: Boolean,
            participantIdList: ArrayList<Int>,
            nameList: ArrayList<String> = arrayListOf(),
            resultList: ArrayList<Int> = arrayListOf(),
        ): Intent = Intent(context, TodoCreateActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, tripId)
            putExtra(EXTRA_IS_OUR_TODO, isOurTodo)
            putIntegerArrayListExtra(EXTRA_PARTICIPANT_ID, participantIdList)
            putStringArrayListExtra(EXTRA_NAME, nameList)
            putIntegerArrayListExtra(EXTRA_RESULT, resultList)
        }
    }
}
