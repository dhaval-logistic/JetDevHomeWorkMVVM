package com.example.jetdevhomeworkmvvm.exts


import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout


fun String.showToast(c: Context, length: Int = Toast.LENGTH_LONG) {
    Toast.makeText(c, this, length).show()
}

fun Context.showToastHere(message: Any?, toastLength: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message.toString(), toastLength).show()
}

fun View.beGone() {
    this.visibility = View.GONE
}

fun View.beVisible() {
    this.visibility = View.VISIBLE
}

fun View.beInVisible() {
    this.visibility = View.INVISIBLE
}

fun View.isHidden(): Boolean {
    return this.visibility == View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.viewVisibility(isEnabled: Boolean) {
    if (isEnabled)
        this.beVisible()
    else
        this.beGone()
}

fun View.viewInvisibility(isEnabled: Boolean) {
    if (isEnabled)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.INVISIBLE
}

fun View.toggleVisibility() {
    isVisible = !isVisible
}

fun EditText.setOnEditorActionListener(
    activity: Activity,
    etView: EditText? = null
) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            etView?.let {
                etView.requestFocus()
                etView.setSelection(etView.text?.length ?: 0)

            } ?: activity.hideKeyboard()
            true
        } else false
    }
}

fun resetTextInputErrorsOnTextChanged(vararg view: TextInputLayout) {
    view.forEach {
        it.resetTextInputErrorsOnTextChanged()
    }
}


fun TextInputLayout.resetTextInputErrorsOnTextChanged() {
    editText?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (this@resetTextInputErrorsOnTextChanged.error != null) {
                this@resetTextInputErrorsOnTextChanged.error = null
                this@resetTextInputErrorsOnTextChanged.isErrorEnabled = false
            }
        }

        override fun afterTextChanged(s: Editable) {}
    })
}


infix fun View?.click(f: () -> Unit) {
    this?.setOnClickListener { f() }
}