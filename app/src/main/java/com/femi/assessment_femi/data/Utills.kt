package com.femi.assessment_femi.data

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.femi.assessment_femi.R
import com.femi.assessment_femi.data.remote.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun View?.hideKeyboard() {
    this?.let {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction(context.getString(R.string.retry)) {
            it()
        }
    }
    if (!snackbar.isShownOrQueued)
        snackbar.show()
}

fun Context?.dialogBuilder(
    title: String? = null,
    message: String?,
    positiveButton: String? = null,
    onPositiveAction: (() -> Unit)? = null,
    negativeButton: String? = null,
    onNegativeAction: (() -> Unit)? = null,
    onCancelAction: (() -> Unit)? = null,
) {
    this?.let {
        MaterialAlertDialogBuilder(it)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(positiveButton) { _, _ ->
                if (onPositiveAction != null) {
                    onPositiveAction()
                }
            }.setNegativeButton(negativeButton) { _, _ ->
                if (onNegativeAction != null) {
                    onNegativeAction()
                }
            }.setOnCancelListener {
                if (onCancelAction != null) {
                    onCancelAction()
                }
            }.show()
    }

}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null,
) {
    val error = failure.errorBody?.string().toString()

    view.hideKeyboard()
    when {
        failure.isNetworkError == true -> {
            view?.snackbar(getString(R.string.check_your_internet), retry)
        }

        failure.errorCode == 500 -> {
            view?.snackbar(getString(R.string.api_error))
        }

        else -> {
            context.dialogBuilder(
                title = getString(R.string.error),
                error,
                positiveButton = getString(R.string.okay)
            )
        }
    }
}