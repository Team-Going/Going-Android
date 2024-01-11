package com.going.presentation.todo.ourtodo.create

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOurTodoCreateBinding
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
        initTodoFocusListener()
        initMemoFocusListener()
        initDateClickListener()
        initFinishBtnListener()
        initBackBtnListener()
        observeTodoCreateState()
        observeTextLength()
        observeMemoLength()
        observeDateEmpty()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initNameListAdapter() {
        // 아워투두 뷰에서 intent로 친구목록 받아와서 적용할 예정
        _adapter = TodoCreateNameAdapter(false)
        binding.rvOurTodoCreatePerson.adapter = adapter
        adapter.submitList(viewModel.totalParticipantList)
    }

    private fun initTodoFocusListener() {
        binding.etOurTodoCreateTodo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvOurTodoTodoCounter,
            ) { background ->
                binding.etOurTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun initMemoFocusListener() {
        binding.etOurTodoCreateMemo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowMemoLength.value ?: 0,
                binding.tvOurTodoMemoCounter,
            ) { background ->
                binding.etOurTodoCreateMemo.background = setBackgroundColor(background)
            }
        }
    }

    private fun initDateClickListener() {
        binding.etOurTodoCreateDate.setOnSingleClickListener {
            ourTodoCreateBottomSheet = OurTodoCreateBottomSheet()
            ourTodoCreateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnOurTodoMemoFinish.setOnSingleClickListener {
            // tripId는 임시 설정
            val tripId: Long = 1
            viewModel.participantList = adapter.currentList
            viewModel.postToCreateTodoFromServer(tripId)
        }
    }

    private fun initBackBtnListener() {
        binding.btnOurTodoCreateBack.setOnSingleClickListener {
            finish()
        }
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
        viewModel.nowTodoLength.observe(this) { length ->
            val maxTodoLen = viewModel.getMaxTodoLen()

            if (length > maxTodoLen) {
                binding.etOurTodoCreateTodo.apply {
                    setText(text?.subSequence(0, maxTodoLen))
                    setSelection(maxTodoLen)
                }
            }
            setColors(
                false,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvOurTodoTodoCounter,
            ) { background ->
                binding.etOurTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeMemoLength() {
        viewModel.nowMemoLength.observe(this) { length ->
            val maxMemoLen = viewModel.getMaxMemoLen()
            if (length > maxMemoLen) {
                binding.etOurTodoCreateTodo.apply {
                    setText(text?.subSequence(0, maxMemoLen))
                    setSelection(maxMemoLen)
                }
            }
            setColors(
                false,
                viewModel.nowMemoLength.value ?: 0,
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
        hasFocus: Boolean,
        length: Int,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when {
            hasFocus || viewModel.nowTodoLength.value != 0 -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
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
    }

}