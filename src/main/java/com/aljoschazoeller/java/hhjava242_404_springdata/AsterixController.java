package com.aljoschazoeller.java.hhjava242_404_springdata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Characters", description = "All available Asterix characters")
@RestController
@RequestMapping("/api/asterix/characters")
public class AsterixController {

    private final AsterixService asterixService;

    public AsterixController(AsterixService asterixService) {
        this.asterixService = asterixService;
    }

    @Operation(summary = "Get all characters and filter by parameters")
    @GetMapping
    List<Character> getCharacters(
            @Parameter(description = "Search Characters by name", example = "Asterix") @RequestParam(required = false) String name,
            @Parameter(description = "Search Characters by profession", example = "Warrior") @RequestParam(required = false) String profession,
            @Parameter(description = "Search Characters by age", example = "35") @RequestParam(required = false) Integer age) {
        return asterixService.getAllCharacters(name, profession, age);
    }

    @Operation(summary = "Add a new character")
    @PostMapping
    Character addCharacter(@RequestBody NewCharacter newCharacter) {
        return asterixService.addCharacter(newCharacter);
    }

    @Operation(summary = "Get a single character")
    @GetMapping("{id}")
    Character getCharacter(@PathVariable String id) {
        return asterixService.getCharacterById(id);
    }

    @Operation(summary = "Delete a single character")
    @DeleteMapping("{id}")
    void deleteCharacter(@PathVariable String id) {
        asterixService.deleteCharacterById(id);
    }

    @Operation(summary = "Update a single character")
    @PutMapping("{id}")
    Character updateCharacter(@PathVariable String id, @RequestBody NewCharacter newCharacter) {
        if (asterixService.exitsCharacterById(id)) {
            return asterixService.updateCharacterById(id, newCharacter);
        }
        return asterixService.addCharacter(newCharacter);
    }
}
