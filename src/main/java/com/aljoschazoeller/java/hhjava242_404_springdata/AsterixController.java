package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/asterix/characters")
public class AsterixController {

    private final AsterixService asterixService;

    public AsterixController(AsterixService asterixService) {
        this.asterixService = asterixService;
    }

    @GetMapping
    List<Character> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String profession,
            @RequestParam(required = false) Integer age) {
        return asterixService.getAllCharacters(name, profession, age);
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

    @PutMapping("{id}")
    Character updateCharacter(@PathVariable String id, @RequestBody NewCharacter newCharacter) {
        if (asterixService.exitsCharacterById(id)) {
            return asterixService.updateCharacterById(id, newCharacter);
        }
        return asterixService.addCharacter(newCharacter);
    }
}
