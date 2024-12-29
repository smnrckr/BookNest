package com.project.backend.repository;

import com.project.backend.entity.Library;
import com.project.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    Optional<Library> findByIsbn(String isbn);
    Optional<Library> findById(Long id);
    List<Library> findBooksByUsers(User user);
    Optional<Library> findByGoogleBookId(String googleBookId);

}
