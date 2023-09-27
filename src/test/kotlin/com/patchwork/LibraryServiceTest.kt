package com.patchwork

import com.patchwork.model.Book
import com.patchwork.model.BorrowerUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LibraryServiceTest {
    private val book1 = Book(id = 1, author = "J K Rowling", title = "Harry Potter", isbn = 9780747532743)
    private val book2 = Book(id = 2, author = "Roald Dahl", title = "Matilda", isbn = 9780241558317)
    private val user1 = BorrowerUser(id = 1, name = "John Doe")

    @Test
    fun `As a library user, I would like to be able to find books by my favourite author, so that I know if they are available in the library`() {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )

        val actual: String = library.find("Rowling")
        assertEquals(expected, actual)
    }

    @Test
    fun `As a library user, I would like to be able to find a book not available, so that I know to find another book by title`() {
        val expected = "No book found"
        val library = LibraryService()

        val actual: String = library!!.find("book not available")
        assertEquals(expected, actual)
    }

    @Test
    fun `As a library user, I would like to be able to find books by title, so that I know if they are available in the library` () {
        val expected = "Book found: Matilda by Roald Dahl"
        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )

        val actual: String = library.find("Matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books in lowercase, so that I know if they are available in the library` () {
        val expected = "Book found: Matilda by Roald Dahl"
        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )

        val actual: String = library.find("matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books by ISBN, so that I know if they are available in the library` () {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )

        val actual: String = library.find("9780747532743")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to borrow a book, so I can read it at home` () {
        val expected = "Book Harry Potter by J K Rowling successfully checked out for John Doe"

        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )
        val actual: String = library.borrow(book1, user1.id)
        assertEquals(expected, actual);
    }

    @Test
    fun `As the library owner, I would like to know how many books are being borrowed, so I can see how many are outstanding`() {
        val expectedBorrowedBook = 1
        val expectedOutstandingBooks = 1

        val library = LibraryService(
            mutableMapOf(book1.id to book1, book2.id to book2),
            setOf(user1)
        )
        library.borrow(book1, user1.id)

        val actualBorrowedBook: Int = library.getCurrentBorrowedBooks().size
        assertEquals(expectedBorrowedBook, actualBorrowedBook)
        val actualOutstandingBooks = library.getAllBooks().size - actualBorrowedBook
        assertEquals(expectedOutstandingBooks, actualOutstandingBooks)
    }
}