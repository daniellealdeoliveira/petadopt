package com.waproject.petadopt.service;

import com.waproject.petadopt.dto.PetDto;
import com.waproject.petadopt.dto.PetRequest;
import com.waproject.petadopt.model.PetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PetService {

    Page<PetDto> findByFilter(PetRequest request, Pageable pageable);

    PetDto addPet(PetDto newPet);

    PetDto patchStatus(Long id, PetStatus status);

}
