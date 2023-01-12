package com.appetiser.racetimer.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.appetiser.racetimer.R
import com.appetiser.racetimer.databinding.DialogInputRacerIdBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView

object ViewUtils {

    fun showConfirmDialog(
        context: Context,
        title: String = "",
        body: String,
        positiveButtonText: String,
        negativeButtonText: String = "",
        positiveClickListener: () -> Unit = {},
        negativeClickListener: () -> Unit = {}
    ) {
        val alertBuilder = MaterialAlertDialogBuilder(context)
        title.isNotEmpty().let {
            alertBuilder.setTitle(title)
        }
        alertBuilder.setMessage(HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_LEGACY))

        alertBuilder
            .setPositiveButton(
                positiveButtonText
            ) { _, _ ->
                positiveClickListener()
            }

        if (negativeButtonText.isNotEmpty()) {
            alertBuilder
                .setNegativeButton(
                    negativeButtonText
                ) { _, _ ->
                    negativeClickListener()
                }
        }

        alertBuilder.setCancelable(true)
        alertBuilder.create().show()
    }

    fun showInputRacerIdDialog(
        context: Context,
        time: String,
        onRacerIdInput: (racerId: String) -> Unit
    ) {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_input_racer_id, null, false)
        val pickerDialog = AlertDialog.Builder(context).create()
            .apply {
                setView(dialogView)
            }

        val racerTimme = dialogView.findViewById<MaterialTextView>(R.id.tvFinishTime)
        val btnSetRacerId = dialogView.findViewById<MaterialButton>(R.id.btnSetRacerId)
        val etRacerId = dialogView.findViewById<AppCompatEditText>(R.id.etRacerID)

        racerTimme.text = time

        btnSetRacerId
            .setOnClickListener {
                pickerDialog.dismiss()
                onRacerIdInput(etRacerId.text.toString())
            }

        pickerDialog.setCancelable(true)

        pickerDialog.show()
    }
}