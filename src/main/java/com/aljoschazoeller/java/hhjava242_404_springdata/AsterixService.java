package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.stereotype.Service;

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
}
