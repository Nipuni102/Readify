package com.sqa.librarysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.librarysystem.model.Book;
import com.sqa.librarysystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;
    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author Name");
        book.setIsbn("1234567890");
    }

    @Test
    void getAllBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookService.getAllBooks()).thenReturn(bookList);

        mockMvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Author Name"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void addBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author Name"));

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void deleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(anyLong());

        mockMvc.perform(delete("/api/book/{id}", 1L))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void updateBook() throws Exception {
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/book/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author Name"));

        verify(bookService, times(1)).updateBook(anyLong(), any(Book.class));
    }
}
