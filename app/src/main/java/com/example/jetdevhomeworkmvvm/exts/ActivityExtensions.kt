package com.example.jetdevhomeworkmvvm.exts

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineExceptionHandler
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Context.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableRes(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

fun Context.getStringRes(@StringRes id: Int) = this.resources.getString(id)

fun Context.getDimenRes(@DimenRes id: Int) = this.resources.getDimension(id)

fun ViewGroup.isKeyboardShowing(isKeyboardShowing: (Boolean) -> Unit = {}) {
    var isKeyboardShow = false

    this.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        this.getWindowVisibleDisplayFrame(r)

        val screenHeight = this.rootView.height
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight > screenHeight * 0.15) {
            if (!isKeyboardShow) {
                isKeyboardShow = true
                isKeyboardShowing(true)
            }
        } else {
            if (isKeyboardShow) {
                isKeyboardShow = false
                isKeyboardShowing(false)
            }
        }
    }
}


fun Context.getBitmap(@DrawableRes drawableRes: Int, width: Int = -1, height: Int = -1): Bitmap? {
    getDrawableRes(drawableRes)?.let { drawable ->
        val mWidth = if (width == -1) drawable.intrinsicWidth else width
        val mHeight = if (height == -1) drawable.intrinsicHeight else height

        val canvas = Canvas()
        val bitmap: Bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, mWidth, mHeight)
        drawable.draw(canvas)
        return bitmap
    }

    return null
}


infix fun RecyclerView?.pairRecyclerView(cardview: CardView?) {
    cardview?.setOnClickListener {
        this?.smoothScrollToPosition(0)
    }
}

fun RecyclerView.syncRecyclerView(cardview: CardView?, dy: Int = 0) {
    cardview?.isVisible = (((this.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()) ?: -1) > 0 && dy < 0
}


infix fun View?.snackbar(message: String) {
    this?.let {
        Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}


fun View.playAnimation() {
    beVisible()
    ValueAnimator.ofFloat(0f, 2f).apply {
        interpolator = LinearInterpolator()
        duration = 1500
        repeatCount = 1
        addUpdateListener { animation ->
            (animation.animatedValue as Float).let { progress ->
                alpha = if (progress <= 1) {
                    progress
                } else {
                    2 - progress
                }
            }
        }
        start()
    }
}

fun EditText.keepLastNDigit(max: Int) {
    text?.toString()?.trim()?.takeIf { it.length > max }?.let { itNumber ->
        setText(itNumber.takeLast(max))
        setSelection(text.length)
    }
}

inline fun <T, K> Iterable<T>.sliceVerticalBy(keySelector: (T) -> K) = mutableListOf<K>().apply {
    for (element in this@sliceVerticalBy) {
        add(keySelector(element))
    }
}

fun String.parseOrNull() = try {
    Color.parseColor(this)
} catch (e: Exception) {
    null
}

fun String.blendColor(textColor: String, percentage: Int): String {
    return if (textColor.length == 7 && this.length == 7 && percentage in (0..100)) {
        try {
            val colorWith8Char = ("#" + Integer.toHexString(
                ColorUtils.blendARGB(
                    Color.parseColor(textColor),
                    Color.parseColor(this),
                    percentage / 100f
                )
            )).takeIf { it.firstOrNull() == '#' }?.drop(1)
            "#" + (colorWith8Char.takeIf { it?.length == 8 }?.takeLast(6) ?: colorWith8Char)
        } catch (e: Exception) {
            textColor
        }
    } else {
        textColor
    }
}

infix fun MaterialTextView.setTextHtml(s: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(s)
    }
}

infix fun AppCompatImageView.loadImageOrHide(url: String?) {
    url?.takeIf { it.isNotBlank() }?.let {
        beVisible()
        Glide.with(context)
            .load(it)
            .into(this)
    } ?: kotlin.run {
        beGone()
    }
}

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    // this error mostly fired from here
    // Read error: ssl=0x7400336cc8: I/O error during system call, Software caused connection abort
}

fun Boolean.inverseIfTrue(input: Boolean): Boolean = if (input) this.not() else this

fun JSONArray.getOrNull(index: Int) = try {
    getJSONObject(index)
} catch (e: Exception) {
    null
}

fun JSONObject.getStringOrNull(key: String) = try {
    getString(key)
} catch (e: Exception) {
    null
}

fun String.toJSONArrayOrNull(): JSONArray? = try {
    JSONArray(this)
} catch (e: Exception) {
    null
}

fun Bundle.getStringOrNull(key: String) = try {
    this.takeIf { it.containsKey(key) }?.getString(key)
} catch (e: Exception) {
    null
}

fun Bundle.getIntOrNull(key: String) = try {
    this.takeIf { it.containsKey(key) }?.getInt(key)
} catch (e: Exception) {
    null
}




infix fun Int.moreThen(other: Int) = this > other

fun Fragment.registerActivity(result: (resultCode: Int, bundle: Bundle?) -> Unit) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result(it.resultCode, it.data?.extras)
    }

infix fun Boolean?.ifTrue(KFunction0: () -> Unit) {
    if (this == true) KFunction0()
}


object FileObject {
    @JvmStatic
    fun toParentDirectoryName(path: String) = File(path).parentFile?.name
}

infix fun String.folderNameFormat(count: Int): String {
    return if (this.length > count) {
        take(count) + "\u2026"
    } else {
        this
    }
}

fun <T> List<T>.takeSafe(num: Int): List<T> {
    return if (num in 1 until size) {
        this.take(num)
    } else {
        this
    }
}

infix fun String?.ifNotBlank(f: () -> Unit) {
    if (this.isNullOrBlank().not()) f()
}

/**
 * @sample elvis() is simple extension function for take decision between two value based on given
 * @param this
 * @author TW Team
 * */
infix fun <T> Boolean.elvis(`true`: T): (`false`: T) -> T = { b -> if (this) `true` else b }

val Boolean?.isTrue: Boolean
    get() = this ?: false

fun MaterialTextView.setTwoValueSapByChar(value1: String?, value2: String?) {
    if (value1.isNullOrBlank().not() && value2.isNullOrBlank().not()) {
        text = value1?.trim() + " | " + value2?.trim()
    } else if (value1.isNullOrBlank().not()) {
        text = value1
    } else if (value2.isNullOrBlank().not()) {
        text = value2
    }
}

fun Any?.isNotNull() = this != null

fun EditText.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}

private const val TAG = "ActivityExtensions"
enum class ParentLayoutType {
    RelativeLayout,
    LinearLayout,
    FrameLayout,
}


fun isMoreThenZero(value: Int) = value > 0

fun currentTimeInHuman() = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

fun <T> T?.ifNullUse(default: T) = this ?: default

val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024

fun ViewGroup.toInflater() = LayoutInflater.from(this.context)


fun <T> T?.orUseThis(defaultValue: T): T {
    return this ?: defaultValue
}
