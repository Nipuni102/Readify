package com.sqa.librarysystem.service;



import com.sqa.librarysystem.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Book addBook(Book book);
    Optional<Book> getBookById(Long id);
    void deleteBook(Long id);
    Book updateBook(Long id, Book updatedBook);
}
