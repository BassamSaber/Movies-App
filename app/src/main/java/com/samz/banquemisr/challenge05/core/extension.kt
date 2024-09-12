package com.samz.banquemisr.challenge05.core

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    contentType: ((T) -> Any)? = null,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    items(
        items.itemCount,
        key = items.itemKey(key),
        contentType = items.itemContentType(contentType)
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
