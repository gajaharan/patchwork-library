package com.patchwork

import com.patchwork.model.Book
import com.patchwork.model.BorrowerUser

class LibraryService(
    private val books: MutableMap<Long, Book> = mutableMapOf(),
    private val users: Set<BorrowerUser> = setOf()
) {

    fun find(searchTerm: String): String {
        books.values.find { book ->
            book.author.lowercase().contains(searchTerm.lowercase())
                    || book.title.lowercase().contains(searchTerm.lowercase())
                    || book.isbn.toString() == searchTerm
                    && book.isAvailable
        }?.let {
            return "Book found: $it"
        }
        return "No book found"
    }


    fun borrow(selectedBook: Book, userId: Long): String {

        val book = books.values.find { it == selectedBook }
        book?.let { book ->
            val user: BorrowerUser? = users.find { it.id == userId }
            user?.borrowedBooks?.add(book) ?: return "User not found: $userId"
            books[selectedBook.id] = selectedBook.copy(isAvailable = false)
            return "Book $book successfully checked out for ${user.name}"
        }
        return "Book ${selectedBook.isbn} is not on the system"
    }
}