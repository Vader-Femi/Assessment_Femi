package com.femi.assessment_femi

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.femi.assessment_femi.data.remote.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

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
    var error = failure.errorBody?.string().toString()

    when {
        failure.isNetworkError == true -> {
            error = getString(R.string.check_your_internet)
            view?.snackbar(error, retry)
        }

        failure.errorCode == 500 -> {
            error = getString(R.string.api_error)
            view?.snackbar(error)
        }

        else -> {
            context.dialogBuilder(
                title = getString(R.string.error),
                error,
                positiveButton = getString(R.string.okay)
            )
        }
    }

    Timber.e("$this - $error")
}