package com.project.backend.service;

import com.project.backend.dto.BookDTO;
import com.project.backend.dto.LibraryDTO;
import com.project.backend.entity.Library;
import com.project.backend.entity.User;
import com.project.backend.repository.LibraryRepository;
import com.project.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            library.setImageUrl(bookDTO.getImageUrl());
            library.setBookAddedAt(new Date());
            library.setBookUpdatedAt(LocalDateTime.now());
            library.setGoogleBookId(bookDTO.getGoogleBookId());

            library = libraryRepository.save(library);
        }

        User user = userOptional.get();
        if (!user.getBooks().contains(library)) {
            user.getBooks().add(library);
        }

        userRepository.save(user);

        return library;
    }

    public List<LibraryDTO> getBooksByUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return libraryRepository.findBooksByUsers(user).stream()
                .map(book -> new LibraryDTO(
                        book.getId(),
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getGenre(),
                        book.getDescription(),
                        book.getRating(),
                        book.getImageUrl(),
                        book.getGoogleBookId()
                ))
                .collect(Collectors.toList());
    }

    public boolean isBookInLibrary(String googleBookId) {
        Optional<Library> book = libraryRepository.findByGoogleBookId(googleBookId);
        return book.isPresent();
    }



}

