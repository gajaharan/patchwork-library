package com.patchwork

import com.patchwork.model.Book

class LibraryService(
    private val books: MutableList<Book> = mutableListOf()
) {
    fun add(book: Book) = books.add(book)

    fun find(searchTerm: String): String {
        val book: Book? = books.find {
            it.author.lowercase().contains(searchTerm.lowercase())
                    || it.title.lowercase().contains(searchTerm.lowercase())
                    || it.isbn.toString() == searchTerm
                    && it.isAvailable
        }
        book?.let {
            return "Book found: $it"
        }
        return "No book found"
    }
}