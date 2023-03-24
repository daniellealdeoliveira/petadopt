package com.waproject.petadopt.controller;

import com.waproject.petadopt.dto.PetDto;
import com.waproject.petadopt.dto.PetRequest;
import com.waproject.petadopt.model.PetStatus;
import com.waproject.petadopt.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<Page<PetDto>> findByFilter(@RequestBody PetRequest request, Pageable pageable) {
        Page<PetDto> petDTOList = petService.findByFilter(request, pageable);

        return new ResponseEntity<>(petDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PetDto> addPet(@RequestBody PetDto newPet) {
        return new ResponseEntity<>(petService.addPet(newPet), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/updateStatus")
    public ResponseEntity<PetDto> patchStatus(@PathVariable("id") Long id, @RequestParam PetStatus status) {
        PetDto petDto = petService.patchStatus(id, status);
        return new ResponseEntity<>(petDto, HttpStatus.OK);
    }
}
