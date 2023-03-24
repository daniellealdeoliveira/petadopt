package com.waproject.petadopt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waproject.petadopt.model.PetCategory;
import com.waproject.petadopt.model.PetStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PetDto implements Serializable {

    private String name;
    private String description;
    private String image;
    private PetCategory category;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createdDate;
    private PetStatus status;
}
