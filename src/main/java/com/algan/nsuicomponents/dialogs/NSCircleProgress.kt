package com.algan.nsuicomponents.dialogs
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import com.algan.nsuicomponents.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class NSCircleProgress(context: Context) : Dialog(context) {

    private var progressBar: CircularProgressIndicator? = null

    init {

        requestWindowFeature(Window.FEATURE_NO_TITLE)


        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_progress_dialog, null)
        setContentView(view)


        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)

        progressBar = view.findViewById(R.id.materialCircularProgress)
    }

    fun showProgress() {
        if (!isShowing) {
            show()
        }
    }

    fun hideProgress() {
        if (isShowing) {
            dismiss()
        }
    }
}
