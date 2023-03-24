package com.danielproject.petadopt.dto;

import com.danielproject.petadopt.model.PetCategory;
import com.danielproject.petadopt.model.PetStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetRequest {

    private String term;
    private PetCategory category;

    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createdDate;
    private PetStatus status;
}