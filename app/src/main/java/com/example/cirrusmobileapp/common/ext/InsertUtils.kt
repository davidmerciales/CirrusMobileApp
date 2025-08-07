package com.example.cirrusmobileapp.common.ext

// InsertUtils.kt
suspend fun <T> List<T>.chunkedInsert(
    chunkSize: Int = 100,
    insertFunc: suspend (List<T>) -> Unit
) {
    this.chunked(chunkSize).forEach { chunk ->
        insertFunc(chunk)
    }
}
