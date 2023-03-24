package com.danielproject.petadopt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.danielproject.petadopt.controller.exception.ResourceNotFoundException;
import com.danielproject.petadopt.dto.PetDto;
import com.danielproject.petadopt.dto.PetRequest;
import com.danielproject.petadopt.model.Pet;
import com.danielproject.petadopt.model.PetCategory;
import com.danielproject.petadopt.model.PetStatus;
import com.danielproject.petadopt.repository.PetRepository;
import com.danielproject.petadopt.service.PetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PetController.class)
public class PetControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PetRepository petRepository;

    @MockBean
    PetService petService;

    private PetRequest createPetRequest() {
        PetRequest pet = new PetRequest();
        pet.setTerm("Test");
        pet.setStatus(PetStatus.AVAILABLE);
        pet.setCreatedDate(LocalDate.now());
        pet.setCategory(PetCategory.DOG);
        return pet;
    }

    private Pet createPet() {
        Pet pet = new Pet();
        pet.setName("Test");
        pet.setStatus(PetStatus.ADOPTED);
        pet.setCreatedDate(LocalDate.now());
        pet.setCategory(PetCategory.DOG);
        return pet;
    }

    private PetDto createPetDto() {
        PetDto pet = new PetDto();
        pet.setName("Test");
        pet.setStatus(PetStatus.ADOPTED);
        pet.setCreatedDate(LocalDate.now());
        pet.setCategory(PetCategory.DOG);
        return pet;
    }

    @Before
    public void setUp() {
        Mockito.reset(petService);
        Mockito.reset(petRepository);
    }

    @Test
    public void shouldAddPet() throws Exception {
        String json = "{ \"name\":\"Leo\" }";

        PetDto createdPet = new PetDto();
        createdPet.setName("Leo");

        when(petService.addPet(createdPet)).thenReturn(createdPet);

        mockMvc.perform(post("/api/pets").accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnFindByFilter() throws Exception {
        PetRequest petRequest = createPetRequest();

        List<PetDto> petDtoList = new ArrayList<>();
        petDtoList.add(createPetDto());

        when(petService.findByFilter(any(), any())).thenReturn(new PageImpl<>(petDtoList));
        mockMvc.perform(get("/api/pets")
                        .param("size", "10")
                        .param("page", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.content.[0].name", is("Test")))
                .andExpect(jsonPath("$.content.[0].status", is("ADOPTED")))
                .andExpect(jsonPath("$.content.[0].category", is("DOG")));
    }

    @Test
    public void shouldReturnPatchStatus() throws Exception {
        when(petRepository.findById(1L)).thenReturn(Optional.of(createPet()));

        mockMvc.perform(patch(("/api/pets/1/updateStatus"))
                        .param("status", "ADOPTED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotReturnPatchStatus() throws Exception {
        when(petService.patchStatus(any(), any())).thenThrow(new ResourceNotFoundException("Test"));

        mockMvc.perform(patch(("/api/pets/1/updateStatus"))
                        .param("status", "ADOPTED")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
