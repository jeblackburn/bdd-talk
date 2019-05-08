package com.jeb.bdd.jpa.impl;

import com.jeb.bdd.entities.LibraryNodeEntity;
import com.jeb.bdd.jpa.LibraryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryRepositoryImpl implements LibraryRepository {
    @Override
    public List<LibraryNodeEntity> read(String userId) {
        throw new RuntimeException("Implement me please");
    }
}
