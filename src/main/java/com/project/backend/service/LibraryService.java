package com.project.backend.service;

import com.project.backend.dto.BookDTO;
import com.project.backend.entity.Library;
import com.project.backend.entity.User;
import com.project.backend.repository.LibraryRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository userRepository;

    public Library addBook(BookDTO bookDTO, Long userId) {

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Library library = new Library();
        library.setIsbn(bookDTO.getIsbn());
        library.setTitle(bookDTO.getTitle());
        library.setAuthor(bookDTO.getAuthor());
        library.setGenre(bookDTO.getGenre());
        library.setDescription(bookDTO.getDescription());
        library.setRating(bookDTO.getRating());
        library.setBookAddedAt(new java.util.Date());
        library.setBookUpdatedAt(java.time.LocalDateTime.now());

        User user = userOptional.get();
        library.getUsers().add(user);

        return libraryRepository.save(library);
    }
}
