package com.sqa.librarysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqa.librarysystem.model.Librarian;
import com.sqa.librarysystem.service.LibrarianServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class LibrarianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LibrarianServiceImpl librarianService;

    @InjectMocks
    private LibrarianController librarianController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void registerLibrarian_ShouldReturn200_WhenRegistrationIsSuccessful() throws Exception {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setName("Jane Doe");
        librarian.setPhoneNumber("1234567890");
        librarian.setEmail("jane.doe@example.com");
        librarian.setPassword("password123");

        when(librarianService.registerLibrarian(any(Librarian.class))).thenReturn(librarian);

        // Act & Assert
        mockMvc.perform(post("/api/librarians/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(librarian)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));
    }

    @Test
    void loginLibrarian_ShouldReturn200_WhenLoginIsSuccessful() throws Exception {
        // Arrange
        Librarian librarian = new Librarian();
        librarian.setEmail("john.smith@example.com");
        librarian.setPassword("password123");

        when(librarianService.loginLibrarian("john.smith@example.com", "password123"))
                .thenReturn(Optional.of(librarian));

        // Prepare login details as a Map
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "john.smith@example.com");
        loginDetails.put("password", "password123");

        // Act & Assert
        mockMvc.perform(post("/api/librarians/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Login successful!"));
    }
    @Test
    void loginLibrarian_ShouldReturn401_WhenCredentialsAreInvalid() throws Exception {
        // Arrange
        when(librarianService.loginLibrarian("invalid@example.com", "wrongPassword"))
                .thenReturn(Optional.empty());

        // Prepare invalid login details as a Map
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "invalid@example.com");
        loginDetails.put("password", "wrongPassword");

        // Act & Assert
        mockMvc.perform(post("/api/librarians/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDetails)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Invalid email or password."));
    }

}
