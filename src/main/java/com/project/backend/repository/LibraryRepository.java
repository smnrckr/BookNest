package com.project.backend.repository;

import com.project.backend.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
    Optional<Library> findByIsbn(Long isbn);
    Optional<Library> findById(Long id);
}
