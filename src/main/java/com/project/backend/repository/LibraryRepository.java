package com.project.backend.repository;

import com.project.backend.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByIsbn(String isbn);
    List<Library> findByUsersId(Long userId);
    Optional<Library> findById(Long id);
    boolean existsByIsbnAndUsersId(String isbn, Long userId);
}
