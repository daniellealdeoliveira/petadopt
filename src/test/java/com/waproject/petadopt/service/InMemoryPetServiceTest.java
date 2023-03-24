package com.waproject.petadopt.service;

import com.waproject.petadopt.dto.PetDto;
import com.waproject.petadopt.dto.PetRequest;
import com.waproject.petadopt.model.Pet;
import com.waproject.petadopt.model.PetCategory;
import com.waproject.petadopt.model.PetStatus;
import com.waproject.petadopt.repository.PetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InMemoryPetServiceTest {

    @MockBean
    PetRepository petRepository;

    @MockBean
    PetService petService;

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
        pet.setDescription("Test");
        pet.setImage("Image");
        pet.setStatus(PetStatus.ADOPTED);
        pet.setCreatedDate(LocalDate.now());
        pet.setCategory(PetCategory.DOG);
        return pet;
    }

    private PetRequest createPetRequest() {
        PetRequest pet = new PetRequest();
        pet.setTerm("Test");
        pet.setStatus(PetStatus.AVAILABLE);
        pet.setCreatedDate(LocalDate.now());
        pet.setCategory(PetCategory.DOG);
        return pet;
    }

    @Before
    public void setUp() {
        Mockito.reset(petRepository);
        Mockito.reset(petService);
    }

    @Test
    public void shouldAddPet() {
        when(petService.addPet(any())).thenReturn(createPetDto());

        PetDto createdPet = petService.addPet(createPetDto());
        assertNotNull(createdPet);

        assertEquals(createdPet.getName(), "Test");
        assertEquals(createdPet.getCategory(),PetCategory.DOG);
    }

    @Test
    public void shouldReturnPatchStatus() {
        when(petService.patchStatus(any(),any())).thenReturn(createPetDto());
        PetDto createdPet = petService.patchStatus(1L, PetStatus.ADOPTED);

        assertNotNull(createdPet);
        assertEquals(createdPet.getName(), "Test");
        assertEquals(createdPet.getStatus(), PetStatus.ADOPTED);
    }

    @Test
    public void shouldReturnFindByFilter() {
        List<Pet> petList = new ArrayList<>();
        petList.add(createPet());

        List<PetDto> petDtoList = new ArrayList<>();
        petDtoList.add(createPetDto());

        PetRequest petRequest = createPetRequest();
        Pageable pageable = PageRequest.of(0, 10);

        when(petService.findByFilter(any(), any())).thenReturn(new PageImpl<>(petDtoList));

        Page<PetDto> byFilter = petService.findByFilter(petRequest, pageable);

        assertEquals(1, byFilter.getNumberOfElements());
        assertEquals("Test", byFilter.stream().findFirst().get().getName());
    }

}
