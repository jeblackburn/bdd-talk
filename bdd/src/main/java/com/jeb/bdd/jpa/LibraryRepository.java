package com.jeb.bdd.jpa;

import com.jeb.bdd.entities.LibraryNodeEntity;

import java.util.List;

public interface LibraryRepository {
    List<LibraryNodeEntity> read(String userId);
}
