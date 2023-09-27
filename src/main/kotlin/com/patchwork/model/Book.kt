package com.patchwork.model

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val isAvailable: Boolean = true
) {
    override fun toString(): String {
        return "$title by $author"
    }
}