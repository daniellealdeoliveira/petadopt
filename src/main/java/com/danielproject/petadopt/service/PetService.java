package com.danielproject.petadopt.service;

import com.danielproject.petadopt.dto.PetDto;
import com.danielproject.petadopt.dto.PetRequest;
import com.danielproject.petadopt.model.PetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetService {

    Page<PetDto> findByFilter(PetRequest request, Pageable pageable);

    PetDto addPet(PetDto newPet);

    PetDto patchStatus(Long id, PetStatus status);

}
