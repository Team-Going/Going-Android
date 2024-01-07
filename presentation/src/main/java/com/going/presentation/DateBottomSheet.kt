package com.going.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.going.presentation.databinding.BottomSheetDateContentBinding

class DateBottomSheet :
    BindingBottomSheetDialog<BottomSheetDateContentBinding>(R.layout.bottom_sheet_date_content) {

    override fun onStart() {
        super.onStart()
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 필요한 로직 추가
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // 필요한 로직 추가
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}
