package com.project.backend.controller;

import com.project.backend.dto.BookDTO;
import com.project.backend.dto.LibraryDTO;
import com.project.backend.entity.Library;
import com.project.backend.entity.User;
import com.project.backend.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/books/addBook")
    public ResponseEntity<Library> addBook(@RequestBody BookDTO bookDTO) {
        try {
            Library library = libraryService.addBook(bookDTO, bookDTO.getUserId());
            return new ResponseEntity<>(library, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/library")
    public ResponseEntity<List<LibraryDTO>> getUserLibrary() {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<LibraryDTO> books = libraryService.getBooksByUser(loggedInUser);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/checkBook")
    public ResponseEntity<Boolean> checkBook(@RequestParam String googleBookId) {
        boolean isInLibrary = libraryService.isBookInLibrary(googleBookId);
        return ResponseEntity.ok(isInLibrary);
    }




}
