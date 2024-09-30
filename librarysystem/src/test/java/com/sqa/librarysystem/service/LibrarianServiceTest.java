package com.sqa.librarysystem.service;



import com.sqa.librarysystem.model.Librarian;
import com.sqa.librarysystem.repository.LibrarianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibrarianServiceTest {

    @InjectMocks
    private LibrarianServiceImpl librarianService;

    @Mock
    private LibrarianRepository librarianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test for registerLibrarian
    @Test
    void registerLibrarian_ShouldReturnSavedLibrarian() {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setName("John Doe");
        librarian.setEmail("john.doe@example.com");
        librarian.setPassword("password123");

        when(librarianRepository.save(any(Librarian.class))).thenReturn(librarian);

        // Act
        Librarian savedLibrarian = librarianService.registerLibrarian(librarian);

        // Assert
        assertNotNull(savedLibrarian);
        assertEquals("John Doe", savedLibrarian.getName());
        verify(librarianRepository, times(1)).save(any(Librarian.class));
    }

    // Test for loginLibrarian
    @Test
    void loginLibrarian_ShouldReturnLibrarian_WhenCredentialsAreValid() {
        // Arrange
        String email = "john.doe@example.com";
        String password = "password123";

        Librarian librarian = new Librarian();
        librarian.setEmail(email);
        librarian.setPassword(password);

        when(librarianRepository.findByEmail(email)).thenReturn(Optional.of(librarian));

        // Act
        Optional<Librarian> result = librarianService.loginLibrarian(email, password);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(librarianRepository, times(1)).findByEmail(email);
    }

    @Test
    void loginLibrarian_ShouldReturnEmpty_WhenPasswordIsInvalid() {
        // Arrange
        String email = "john.doe@example.com";
        String correctPassword = "password123";
        String incorrectPassword = "wrongPassword";

        Librarian librarian = new Librarian();
        librarian.setEmail(email);
        librarian.setPassword(correctPassword);

        when(librarianRepository.findByEmail(email)).thenReturn(Optional.of(librarian));

        // Act
        Optional<Librarian> result = librarianService.loginLibrarian(email, incorrectPassword);

        // Assert
        assertFalse(result.isPresent());
        verify(librarianRepository, times(1)).findByEmail(email);
    }
}
