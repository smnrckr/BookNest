package com.project.backend.entity;
import jakarta.persistence.*;

@Entity
@Table(name="library_entity")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}