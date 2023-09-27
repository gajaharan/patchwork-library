package com.patchwork

import com.patchwork.model.Book
import com.patchwork.model.BorrowerUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LibraryServiceTest {
    private val book1 =
        Book(id = 1, author = "J K Rowling", title = "Harry Potter", isbn = 9780747532743, category = "fiction")
    private val book2 =
        Book(id = 2, author = "Roald Dahl", title = "Matilda", isbn = 9780241558317, category = "fiction")
    private val book3 =
        Book(id = 3, author = "Denis Diderot", title = "Encyclopedia", isbn = 9780716600770, category = "reference")
    private val books = mutableMapOf(book1.id to book1, book2.id to book2, book3.id to book3)
    private val user1 = BorrowerUser(id = 1, name = "John Doe")
    private val users = setOf(user1)

    @Test
    fun `As a library user, I would like to be able to find books by my favourite author, so that I know if they are available in the library`() {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService(
            books,
            users
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
    fun `As a library user, I would like to be able to find books by title, so that I know if they are available in the library`() {
        val expected = "Book found: Matilda by Roald Dahl"
        val library = LibraryService(
            books,
            users
        )

        val actual: String = library.find("Matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books in lowercase, so that I know if they are available in the library`() {
        val expected = "Book found: Matilda by Roald Dahl"
        val library = LibraryService(
            books,
            users
        )

        val actual: String = library.find("matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books by ISBN, so that I know if they are available in the library`() {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService(
            books,
            users
        )

        val actual: String = library.find("9780747532743")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to borrow a book, so I can read it at home`() {
        val expected = "Book Harry Potter by J K Rowling successfully checked out for John Doe"

        val library = LibraryService(
            books,
            users
        )
        val actual: String = library.borrow(book1, user1)
        assertEquals(expected, actual);
    }

    @Test
    fun `As the library owner, I would like to know how many books are being borrowed, so I can see how many are outstanding`() {
        val expectedBorrowedBook = 1
        val expectedOutstandingBooks = 2

        val library = LibraryService(
            books,
            users
        )
        library.borrow(book1, user1)

        val actualBorrowedBook: Int = library.getCurrentBorrowedBooks().size
        assertEquals(expectedBorrowedBook, actualBorrowedBook)
        val actualOutstandingBooks = library.getAllBooks().size - actualBorrowedBook
        assertEquals(expectedOutstandingBooks, actualOutstandingBooks)
    }

    @Test
    fun `As a library user, I should be to prevented from borrowing reference books, so that they are always available`() {
        val expected = "Encyclopedia is a reference book"

        val library = LibraryService(
            books,
            users
        )

        val actualText: String = library.borrow(book3, user1)
        assertEquals(expected, actualText)
        val actualSize: Int = library.getCurrentBorrowedBooks().size
        assertEquals(0, actualSize)
    }
}