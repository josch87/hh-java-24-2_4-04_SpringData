package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asterix/characters")
public class AsterixController {

    private final AsterixService asterixService;

    public AsterixController(AsterixService asterixService) {
        this.asterixService = asterixService;
    }

    @GetMapping
    List<Character> getCharacters() {
        return asterixService.getAllCharacters();
    }

    @PostMapping
    Character addCharacter(@RequestBody NewCharacter newCharacter) {
        return asterixService.addCharacter(newCharacter);
    }

    @GetMapping("{id}")
    Character getCharacter(@PathVariable String id) {
        return asterixService.getCharacterById(id);
    }

    @DeleteMapping("{id}")
    void deleteCharacter(@PathVariable String id) {
        asterixService.deleteCharacterById(id);
    }
}
