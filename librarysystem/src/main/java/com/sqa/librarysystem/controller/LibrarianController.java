package com.sqa.librarysystem.controller;



import com.sqa.librarysystem.model.Librarian;
import com.sqa.librarysystem.service.LibrarianServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/librarians")
@CrossOrigin(origins = "http://localhost:3000")
public class LibrarianController {

    @Autowired
    private LibrarianServiceImpl librarianService;

    // Endpoint to register a new librarian
    @PostMapping("/register")
    public ResponseEntity<Librarian> register(@RequestBody Librarian librarian) {
        Librarian savedLibrarian = librarianService.registerLibrarian(librarian);
        return ResponseEntity.ok(savedLibrarian);
    }

    // Endpoint for librarian login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginDetails) {
        String email = loginDetails.get("email");
        String password = loginDetails.get("password");

        Optional<Librarian> librarian = librarianService.loginLibrarian(email, password);
        if (librarian.isPresent()) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }
}

