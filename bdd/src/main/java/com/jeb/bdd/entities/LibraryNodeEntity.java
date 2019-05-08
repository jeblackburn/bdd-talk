package com.jeb.bdd.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Entity
@Builder
public class LibraryNodeEntity {

    @Id
    private Integer id;

    @JsonProperty("parentId")
    private Integer parentSK;

    private String name;

    @JsonProperty("nodeType")
    private String typeCode;

    private String description;

    private String path;

    @JsonProperty("reportId")
    private Integer templateSK;

    private String templateId;
}
