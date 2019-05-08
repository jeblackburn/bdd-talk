package com.jeb.bdd.testhelpers;

import com.jeb.bdd.entities.LibraryNodeEntity;

import java.util.List;

public class TestMothers {
    public static List<LibraryNodeEntity> masterLibrary() {
        return List.of(
                LibraryNodeEntity.builder().id(1).parentSK(-1).name("F1").typeCode("F").build(),
                LibraryNodeEntity.builder().id(2).parentSK(1).name("F2").typeCode("F").build(),
                LibraryNodeEntity.builder().id(3).parentSK(2).name("F3").typeCode("F").build(),
                LibraryNodeEntity.builder().id(4).parentSK(1).name("F4").typeCode("F").build(),
                LibraryNodeEntity.builder().id(5).parentSK(2).name("T1").typeCode("T").build(),
                LibraryNodeEntity.builder().id(6).parentSK(4).name("T2").typeCode("T").build(),
                LibraryNodeEntity.builder().id(7).parentSK(4).name("T3").typeCode("T").build()
        );
    }
}
