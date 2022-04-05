package com.albertomagalhaes.lordoftheringsinfo.presentation

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView

fun View.show(isVisible: Boolean){
    visibility =
        if(isVisible) View.VISIBLE
        else View.GONE
}

fun View.rotate(
    value: Float,
    duration: Long = 200
){
    animate()
        .rotation(value)
        .setDuration(duration)
        .start()
}

fun View.collapse(): Boolean {
    show(false)
    return false
}

fun View.expand(): Boolean{
    show(true)
    return true
}

fun View.setInvisible(){
    visibility = View.INVISIBLE
}

fun TextView.setTextOrHide(_text: String?): Boolean{
    return if(_text.isNullOrBlank()){
        show(false)
        false
    }else {
        text = _text
        true
    }
}

fun View.onClickRedirect(
    link: String
){
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    setOnClickListener {
        context.startActivity(intent)
    }
}