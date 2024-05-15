package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsterixService {

    private final CharacterRepository characterRepository;

    public AsterixService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getAllCharacters (String name, String profession, Integer age) {
        return characterRepository.findAll().stream()
                .filter(character -> name == null || character.getName().equals(name))
                .filter(character -> profession == null || character.getProfession().equals(profession))
                .filter(character -> age == null || character.getAge() == age)
                .collect(Collectors.toList());
    }

    public Character addCharacter(NewCharacter newCharacter) {
        Instant currentTime = Instant.now();
        Character newCharacterToAdd = Character.builder()
                .name(newCharacter.name())
                .age(newCharacter.age())
                .profession(newCharacter.profession())
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        return characterRepository.insert(newCharacterToAdd);
    }

    public Character getCharacterById(String id) {
        return characterRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Can not find character with id " + id));
    }

    public void deleteCharacterById(String id) {
        characterRepository.deleteById(id);
    }

    public boolean exitsCharacterById (String id) {
        return characterRepository.existsById(id);
    }

    public Character updateCharacterById(Character characterToUpdate) {
        Character existingCharacter = characterRepository.findById(characterToUpdate.getId())
                .orElseThrow(() -> new NoSuchElementException("Could not find character with id " + characterToUpdate.getId()));
        Character characterToUpdateWithTime = characterToUpdate.withUpdatedAt((Instant.now())).withCreatedAt(existingCharacter.getCreatedAt());
        return characterRepository.save(characterToUpdateWithTime);
    }
}
