package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
}
