package com.sqa.librarysystem.service;


import com.sqa.librarysystem.model.Librarian;
import java.util.Optional;

public interface LibrarianService {

    // Method to register a new librarian
    Librarian registerLibrarian(Librarian librarian);

    // Method to login a librarian using email and password
    Optional<Librarian> loginLibrarian(String email, String password);
}
