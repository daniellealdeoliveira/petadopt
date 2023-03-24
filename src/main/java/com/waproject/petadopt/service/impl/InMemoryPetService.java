package com.waproject.petadopt.service.impl;

import com.waproject.petadopt.controller.exception.ResourceNotFoundException;
import com.waproject.petadopt.dto.PetDto;
import com.waproject.petadopt.dto.PetRequest;
import com.waproject.petadopt.model.Pet;
import com.waproject.petadopt.model.PetStatus;
import com.waproject.petadopt.repository.PetRepository;
import com.waproject.petadopt.repository.specification.PetSpecification;
import com.waproject.petadopt.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InMemoryPetService implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<PetDto> findByFilter(PetRequest request, Pageable pageable) {
        Page<Pet> pets = petRepository.findAll(new PetSpecification(request), pageable);

        return pets.map(entity -> modelMapper.map(entity, PetDto.class));
    }

    @Override
    public PetDto addPet(PetDto newPet) {
        Pet pet = modelMapper.map(newPet, Pet.class);
        petRepository.save(pet);

        return modelMapper.map(pet, PetDto.class);
    }

    @Override
    @Transactional
    public PetDto patchStatus(Long id, PetStatus status) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not Found"));
        pet.setStatus(status);
        petRepository.save(pet);
        return modelMapper.map(pet, PetDto.class);
    }

}
