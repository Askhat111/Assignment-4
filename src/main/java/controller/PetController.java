package com.example.petadoptionplatform.controller;

import com.example.petadoptionplatform.model.Pet;
import com.example.petadoptionplatform.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    // GET All Pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // GET Pet by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return petRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST Create Pet
    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    // PUT Update Pet
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet updatedPet) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(updatedPet.getName());
                    existingPet.setType(updatedPet.getType());
                    existingPet.setAge(updatedPet.getAge());
                    return ResponseEntity.ok(petRepository.save(existingPet));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        return petRepository.findById(id)
                .map(pet -> {
                    petRepository.delete(pet);
                    return ResponseEntity.ok().<Void>build(); // Explicitly specify the generic type
                })
                .orElse(ResponseEntity.notFound().<Void>build()); // Explicitly specify the generic type
    }
}

