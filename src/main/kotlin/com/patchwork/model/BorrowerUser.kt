package com.patchwork.model

data class BorrowerUser(
    val id: Long,
    val name: String,
    val borrowedBooks: MutableSet<Book> = mutableSetOf()
)