package com.algan.nsuicomponents.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.algan.nsuicomponents.R
import com.algan.nsuicomponents.databinding.CustomTextFieldDialogBinding

class NSDialogTextField : DialogFragment() {

    private var imageResId: Int = R.drawable.ic_info
    private var title: String = "Default Title"
    private var buttonText: String = "Ok"
    private var buttonColor: Int = Color.BLUE
    private var backgroundColor: Int = Color.WHITE

    var onDone: ((String) -> Unit)? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResId = it.getInt(ARG_IMAGE, R.drawable.ic_info)
            title = it.getString(ARG_TITLE, "Default Title") ?: "Default Title"
            buttonText = it.getString(ARG_BUTTON_TEXT, "Ok") ?: "Ok"
            buttonColor = it.getInt(ARG_BUTTON_COLOR, Color.BLUE)
            backgroundColor = it.getInt(ARG_BACKGROUND_COLOR, Color.WHITE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = CustomTextFieldDialogBinding.inflate(layoutInflater)

        binding.btnDone.setOnClickListener {
            val editTextData = binding.editText.text.toString()
            onDone?.invoke(editTextData)
            dialog?.dismiss()
        }


        val titleTextView: TextView = binding.root.findViewById(R.id.dialog_title)
        titleTextView.text = title

        binding.btnDone.setBackgroundColor(buttonColor)
        binding.btnDone.setText(buttonText)

        binding.appCompatImageView.setImageResource(imageResId)

        binding.linearLayout.setBackgroundColor(backgroundColor)
        binding.cardViewIcon.setBackgroundColor(backgroundColor)

        val dialog = MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.ShapeAppearance_Capsule
        ).apply {
            setView(binding.root)
            setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                    dismiss()
                    true
                } else false
            }
        }.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }


    companion object {
        private const val ARG_IMAGE = "arg_image"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BUTTON_TEXT= "arg_button_text"
        private const val ARG_BUTTON_COLOR = "arg_button_color"
        private const val ARG_BACKGROUND_COLOR = "arg_background"

        fun newInstance(
            imageResId: Int,
            title: String,
            buttonText :String,
            buttonColor: Int,
            dialogBackground: Int
        ): NSDialogTextField {
            val dialog = NSDialogTextField()
            val args = Bundle()
            args.putInt(ARG_IMAGE, imageResId)
            args.putString(ARG_TITLE, title)
            args.putString(ARG_BUTTON_TEXT, buttonText)
            args.putInt(ARG_BUTTON_COLOR, buttonColor)
            args.putInt(ARG_BACKGROUND_COLOR, dialogBackground)
            dialog.arguments = args
            return dialog
        }
    }
}
