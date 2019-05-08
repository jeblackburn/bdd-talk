package com.jeb.bdd.controllers;

import com.jeb.bdd.entities.LibraryNodeEntity;
import com.jeb.bdd.jpa.LibraryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("master-library")
public class MasterLibraryController {

    private com.jeb.bdd.jpa.LibraryRepository libraryRepository;

    public MasterLibraryController(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @GetMapping("/{userId}")
    public List<LibraryNodeEntity> getMasterLibrary(@PathVariable String userId) {
        return libraryRepository.read(userId);
    }

}
