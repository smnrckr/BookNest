package com.project.backend.controller;

import com.project.backend.dto.BookDTO;
import com.project.backend.entity.Library;
import com.project.backend.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/books/addBook")
    public ResponseEntity<Library> addBook(@RequestBody BookDTO bookDTO) {
        try {
            System.out.println("Received BookDTO: " + bookDTO);
            System.out.println("User ID from DTO: " + bookDTO.getUserId());
            System.out.println("Book ISBN: " + bookDTO.getIsbn());
            Library library = libraryService.addBook(bookDTO, bookDTO.getUserId());
            return new ResponseEntity<>(library, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
