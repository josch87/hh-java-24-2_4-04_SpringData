package com.aljoschazoeller.java.hhjava242_404_springdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.print.attribute.standard.Media;
import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AsterixControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CharacterRepository characterRepository;

    @Test
    @DirtiesContext
    void getCharactersIntegrationTest_WhenRequestAllCharacter_ThenReturnJsonWith3Characters() throws Exception {
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        characterRepository.save(c1);
        characterRepository.save(c2);
        characterRepository.save(c3);

        mockMvc.perform(get("/api/asterix/characters"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        [
                          {
                            "id":"1",
                            "name":"Asterix",
                            "age":35,
                            "profession":"Warrior"
                          },
                          {
                            "id":"2",
                            "name":"Obelix",
                            "age":25,
                            "profession":"Deliveryman"
                          },
                          {
                            "id":"3",
                            "name":"Omnifix",
                            "age":17,"profession":"Deliveryman"
                          }
                        ]
                        """
                ));
    }

    @Test
    @DirtiesContext
    void addCharacterIntegrationTest_WhenPostCharacter_ReturnCharacter() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Asterix",
                                    "age": 35,
                                    "profession": "Warrior"
                                }
                                """))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                            "name": "Asterix",
                            "age": 35,
                            "profession": "Warrior"
                        }
                        """))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists())
                .andReturn();

        String id = JsonPath.parse(result.getResponse().getContentAsString()).read("$.id");
        Assertions.assertTrue(characterRepository.existsById(id));
    }

    @Test
    @DirtiesContext
    void getCharacterIntegrationTest_WhenGet1_ThenReturnJsonWithCharacter1() throws Exception {
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        characterRepository.save(c1);

        mockMvc.perform(get("/api/asterix/characters/1"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                          "id":"1",
                          "name":"Asterix",
                          "age":35,
                          "profession":"Warrior"
                        }
                        """));
    }

    @Test
    @DirtiesContext
    void deleteCharacterIntegrationTest_WhenDelete1_Then200AndDeletedInDb() throws Exception {
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        characterRepository.save(c1);
        characterRepository.save(c2);
        characterRepository.save(c3);

        mockMvc.perform(delete("/api/asterix/characters/1"))
                .andExpect(status().is(200));

        Assertions.assertFalse(characterRepository.existsById("1"));
        Assertions.assertTrue(characterRepository.existsById("2"));
        Assertions.assertTrue(characterRepository.existsById("3"));
    }

    @Test
    @DirtiesContext
    void updateCharacter() throws Exception {
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        characterRepository.save(c1);
        characterRepository.save(c2);
        characterRepository.save(c3);

        mockMvc.perform(put("/api/asterix/characters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id":"17",
                                  "name":"Asterix",
                                  "age":36,
                                  "profession":"Warrior",
                                  "createdAt": "2030-05-14T11:58:28.838215Z",
                                  "updatedAt": "2032-05-14T11:58:28.838215Z"
                                }
                                """))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                          "id":"1",
                          "name":"Asterix",
                          "age":36,
                          "profession":"Warrior"
                        }
                        """));

        Character persistedCharacter = characterRepository.findById("1").orElseThrow();

        Assertions.assertEquals(36, persistedCharacter.getAge());
        Assertions.assertEquals("Asterix", persistedCharacter.getName());
    }
}