package com.going.presentation.todo.mytodo.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoCreateBottomSheetBinding
import com.going.ui.base.BaseBottomSheet
import com.going.ui.extension.setOnSingleClickListener

class MyTodoCreateBottomSheet() :
    BaseBottomSheet<FragmentMyTodoCreateBottomSheetBinding>(R.layout.fragment_my_todo_create_bottom_sheet) {

    private val viewModel by activityViewModels<MyTodoCreateViewModel>()

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
            val createdMonth = String.format("%02d", binding.dpCreateTripDate.month + 1)
            val createdDay = String.format("%02d", binding.dpCreateTripDate.dayOfMonth)
            viewModel.endDate.value =
                binding.dpCreateTripDate.year.toString() + "." + createdMonth + "." + createdDay
            viewModel.checkIsFinishAvailable()
            dismiss()
        }
    }

}