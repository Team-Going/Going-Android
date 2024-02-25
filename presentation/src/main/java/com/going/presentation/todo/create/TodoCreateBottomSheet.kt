package com.going.presentation.todo.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoCreateBottomSheetBinding
import com.going.presentation.todo.mytodo.create.MyTodoCreateBottomSheet.Companion.TWO_DIGIT_FORMAT
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener

class TodoCreateBottomSheet() :
    BaseBottomSheet<FragmentOurTodoCreateBottomSheetBinding>(R.layout.fragment_our_todo_create_bottom_sheet) {

    private val viewModel by activityViewModels<TodoCreateViewModel>()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFinishBtnClickListener()
    }

    private fun initFinishBtnClickListener() {
        binding.btnCreateTripFinish.setOnSingleClickListener {
            val createdMonth = String.format(TWO_DIGIT_FORMAT, binding.dpCreateTripDate.month + 1)
            val createdDay = String.format(TWO_DIGIT_FORMAT, binding.dpCreateTripDate.dayOfMonth)
            viewModel.endDate.value =
                binding.dpCreateTripDate.year.toString() + "." + createdMonth + "." + createdDay
            viewModel.checkIsFinishAvailable()
            dismiss()
        }
    }

}