package com.samz.banquemisr.challenge05.core

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun <T : Any> LazyGridScope.items(
    items: List<T>,
    key: ((T) -> Any)? = null,
    contentType: ((T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(T) -> Unit
) {
    items(
        items.size,
//        key = items.itemKey(key),
//        contentType = items.itemContentType(contentType)
    ) loop@{ i ->
        val item = items[i] ?: return@loop
        itemContent(item)
    }
}

fun String.formatDate(): String {
    val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    apiDateFormat.parse(this)?.apply {
        return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            .format(this)
    }
    return ""
}

fun String.toDate(): Date? {
    val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    return apiDateFormat.parse(this)
}

fun Date.format(newFormat: String = "MMM dd, yyyy"): String {
    return SimpleDateFormat(newFormat, Locale.getDefault())
        .format(this)
}

fun Int.toMovieDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (minutes > 0) "${hours}h ${minutes}m" else "${hours}h"
}

fun String.toFullImageUrl(): String =
    "https://image.tmdb.org/t/p/original$this"
