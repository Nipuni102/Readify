package com.sqa.librarysystem.service;


import com.sqa.librarysystem.model.Librarian;
import com.sqa.librarysystem.repository.LibrarianRepository;
import com.sqa.librarysystem.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    @Autowired
    private LibrarianRepository librarianRepository;

    // Method to register a new librarian
    @Override
    public Librarian registerLibrarian(Librarian librarian) {
        // Save the librarian to the database
        return librarianRepository.save(librarian);
    }

    // Method to login a librarian using email and password
    @Override
    public Optional<Librarian> loginLibrarian(String email, String password) {
        Optional<Librarian> librarian = librarianRepository.findByEmail(email);
        // Check if the password matches
        return librarian.filter(l -> l.getPassword().equals(password));
    }
}
