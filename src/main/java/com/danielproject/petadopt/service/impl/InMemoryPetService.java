package com.danielproject.petadopt.service.impl;

import com.danielproject.petadopt.controller.exception.ResourceNotFoundException;
import com.danielproject.petadopt.repository.specification.PetSpecification;
import com.danielproject.petadopt.dto.PetDto;
import com.danielproject.petadopt.dto.PetRequest;
import com.danielproject.petadopt.model.Pet;
import com.danielproject.petadopt.model.PetStatus;
import com.danielproject.petadopt.repository.PetRepository;
import com.danielproject.petadopt.service.PetService;
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
