package com.project.backend.service;

import com.project.backend.dto.BookDTO;
import com.project.backend.entity.Library;
import com.project.backend.entity.User;
import com.project.backend.repository.LibraryRepository;
import com.project.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Library addBook(BookDTO bookDTO, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<Library> existingLibrary = libraryRepository.findByIsbn(bookDTO.getIsbn());
        Library library;

        if (existingLibrary.isPresent()) {
            library = existingLibrary.get();
        } else {
            library = new Library();
            library.setIsbn(bookDTO.getIsbn());
            library.setTitle(bookDTO.getTitle());
            library.setAuthor(bookDTO.getAuthor());
            library.setGenre(bookDTO.getGenre());
            library.setDescription(bookDTO.getDescription());
            library.setRating(bookDTO.getRating());
            library.setBookAddedAt(new Date());
            library.setBookUpdatedAt(LocalDateTime.now());

            library = libraryRepository.save(library);
        }

        User user = userOptional.get();
        if (!user.getBooks().contains(library)) {
            user.getBooks().add(library);
        }

        userRepository.save(user);

        return library;
    }

    public List<Library> getBooksByUser(User user) {
        return libraryRepository.findBooksByUsers(user);
    }




}
