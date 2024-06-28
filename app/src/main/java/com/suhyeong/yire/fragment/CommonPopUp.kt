package com.suhyeong.yire.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.suhyeong.yire.databinding.FragmentCommonPopupBinding
import com.suhyeong.yire.listener.PopUpClickListener

class CommonPopUp: DialogFragment() {
    private lateinit var binding: FragmentCommonPopupBinding
    private lateinit var title: String
    private lateinit var detail: String
    private var isOne: Boolean = false
    private lateinit var listener: PopUpClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommonPopupBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = arguments?.getString("title") ?: "NONE"
        detail = arguments?.getString("detail") ?: "NONE"
        isOne = arguments?.getBoolean("isOne") ?: false

        if (!"NONE".equals(title)) {
            binding.tvPopupTitle.text = title
        }

        if (!"NONE".equals(detail)) {
            binding.tvPopupDetail.text = detail
        }

        binding.tvPopupCancel.isVisible = isOne

        binding.tvPopupConfirm.setOnClickListener {
            listener.onConfirm()
            dismiss()
        }

        binding.tvPopupCancel.setOnClickListener {
            listener.onCancel()
            dismiss()
        }
    }

    fun setPopUpListener(listener: PopUpClickListener) {
        this.listener = listener
    }

    companion object {
        fun newInstance(title: String, detail: String, isOne: Boolean): CommonPopUp {
            val args = Bundle()
            args.putString("title", title)
            args.putString("detail", detail)
            args.putBoolean("isOne", isOne)

            val fragment = CommonPopUp()
            fragment.arguments = args
            return fragment
        }
    }
}