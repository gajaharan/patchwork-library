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

    fun borrow(selectedBook: Book, user: BorrowerUser): String {
        if (selectedBook.category == "reference") {
            return "${selectedBook.title} is a reference book"
        }

        if (books.none { it.value == selectedBook }) {
            return "Book ${selectedBook.isbn} is not on the system"
        }

        reserveBook(selectedBook, user)
        return "Book $selectedBook successfully checked out for ${user.name}"
    }

    fun getCurrentBorrowedBooks(): Map<Long, Book> = books.filter { !it.value.isAvailable }.toMap()

    fun getAllBooks() = books

    private fun reserveBook(selectedBook: Book, user: BorrowerUser) {
        books[selectedBook.id] = selectedBook.copy(isAvailable = false)
        user.borrowedBooks.add(selectedBook)
    }
}