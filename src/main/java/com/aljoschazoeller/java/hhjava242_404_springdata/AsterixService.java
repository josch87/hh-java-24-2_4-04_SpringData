package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AsterixService {

    private final CharacterRepository characterRepository;

    public AsterixService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getAllCharacters () {
        return characterRepository.findAll();
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

    public Character updateCharacterById(String id, NewCharacter newCharacter) {
        Character characterToUpdate = getCharacterById(id);
        characterToUpdate.setName(newCharacter.name());
        characterToUpdate.setAge(newCharacter.age());
        characterToUpdate.setUpdatedAt(Instant.now());
        characterToUpdate.setProfession(newCharacter.profession());
        return characterRepository.save(characterToUpdate);
    }
}
