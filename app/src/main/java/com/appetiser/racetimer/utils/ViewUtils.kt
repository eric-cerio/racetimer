package com.appetiser.racetimer.utils

import android.content.Context
import androidx.core.text.HtmlCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
}