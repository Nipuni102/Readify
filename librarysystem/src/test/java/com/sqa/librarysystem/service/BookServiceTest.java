package com.sqa.librarysystem.service;

import com.sqa.librarysystem.model.Book;
import com.sqa.librarysystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author Name");
        book.setIsbn("1234567890");
    }

    @Test
    void addBook() {
        when(bookRepository.save(book)).thenReturn(book);  // Mock the save behavior

        Book savedBook = bookService.addBook(book);  // Call the method under test

        // Assertions to verify the result
        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());

        // Verify that the save method was called once
        verify(bookRepository, times(1)).save(book);
    }
    @Test
    void deleteBook() {
        // Assume the book exists in the repository
        when(bookRepository.existsById(1L)).thenReturn(true);  // Mock the existsById behavior

        bookService.deleteBook(1L);  // Call the method under test

        // Verify that the delete method was called once
        verify(bookRepository, times(1)).deleteById(1L);
    }


    @Test
    void updateBook() {
        // Mock the findById behavior to return the existing book
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Update the book details
        book.setTitle("Updated Book Title");
        book.setAuthor("Updated Author Name");

        // Mock the save behavior
        when(bookRepository.save(book)).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, book);  // Call the method under test

        // Assertions to verify the result
        assertNotNull(updatedBook);
        assertEquals("Updated Book Title", updatedBook.getTitle());
        assertEquals("Updated Author Name", updatedBook.getAuthor());

        // Verify that the findById and save methods were called once
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book);
    }


    @Test
    void getAllBooks() {
        // Create a list of books
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        // Mock the findAll behavior to return the list of books
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> foundBooks = bookService.getAllBooks();  // Call the method under test

        // Assertions to verify the result
        assertNotNull(foundBooks);
        assertEquals(1, foundBooks.size());
        assertEquals(book.getId(), foundBooks.get(0).getId());
        assertEquals(book.getTitle(), foundBooks.get(0).getTitle());
        assertEquals(book.getAuthor(), foundBooks.get(0).getAuthor());

        // Verify that the findAll method was called once
        verify(bookRepository, times(1)).findAll();
    }



}
