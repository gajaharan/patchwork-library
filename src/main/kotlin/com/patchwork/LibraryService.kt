package com.patchwork

import com.patchwork.model.Book

class LibraryService(
    private val books: MutableList<Book> = mutableListOf()
) {
    fun add(book: Book) = books.add(book)

    fun find(title: String): String {
        val book: Book? = books.find { it.author.contains(title) }
        book?.let {
            return "Book found: $it"
        }
        return "No book found"
    }
}