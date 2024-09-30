package com.sqa.librarysystem.repository;



import com.sqa.librarysystem.model.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByEmail(String email);
}
