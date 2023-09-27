package com.patchwork

import com.patchwork.model.Book
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LibraryServiceTest {
    private val book1 =
        Book(id = 1, author = "J K Rowling", title = "Harry Potter", isbn = 9780747532743)
    private val book2 =
        Book(id = 2, author = "Roald Dahl", title = "Matilda", isbn = 9780241558317)

    @Test
    fun `As a library user, I would like to be able to find books by my favourite author, so that I know if they are available in the library`() {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService()
        library.add(book1)
        library.add(book2)

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
        val library = LibraryService()
        library.add(book1)
        library.add(book2)

        val actual: String = library.find("Matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books in lowercase, so that I know if they are available in the library` () {
        val expected = "Book found: Matilda by Roald Dahl"
        val library = LibraryService()
        library.add(book1)
        library.add(book2)

        val actual: String = library.find("matilda")
        assertEquals(expected, actual);
    }

    @Test
    fun `As a library user, I would like to be able to find books by ISBN, so that I know if they are available in the library` () {
        val expected = "Book found: Harry Potter by J K Rowling"
        val library = LibraryService()
        library.add(book1)
        library.add(book2)

        val actual: String = library.find("9780747532743")
        assertEquals(expected, actual);
    }
}