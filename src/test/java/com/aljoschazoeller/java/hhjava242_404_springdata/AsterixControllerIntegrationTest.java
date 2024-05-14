package com.aljoschazoeller.java.hhjava242_404_springdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AsterixControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() throws Exception {
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        mockMvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c1)))
                .andExpect(status().is(200));
        mockMvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c2)))
                .andExpect(status().is(200));
        mockMvc.perform(post("/api/asterix/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c3)))
                .andExpect(status().is(200));
    }

    @Test
    @DirtiesContext
    void getCharactersIntegrationTest_WhenRequestAllCharacter_ThenReturnJsonWith3Characters() throws Exception {
        mockMvc.perform(get("/api/asterix/characters"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        [
                          {
                            "name":"Asterix",
                            "age":35,
                            "profession":"Warrior"
                          },
                          {
                            "name":"Obelix",
                            "age":25,
                            "profession":"Deliveryman"
                          },
                          {
                            "name":"Omnifix",
                            "age":17,
                            "profession":"Deliveryman"
                          }
                        ]
                        """
                ))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[2].id").exists());
    }

    @Test
    @DirtiesContext
    void getCharactersIntegrationTest_WhenRequestDeliverymanWithAge25_ThenReturnJsonWithObelix() throws Exception {
        mockMvc.perform(get("/api/asterix/characters?profession=Deliveryman&age=25"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        [
                          {
                            "name":"Obelix",
                            "age":25,
                            "profession":"Deliveryman"
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

        String response = result.getResponse().getContentAsString();
        Character responseCharacter = objectMapper.readValue(response, Character.class);

        mockMvc.perform(get("/api/asterix/characters/" + responseCharacter.getId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                            "name": "Asterix",
                            "age": 35,
                            "profession": "Warrior"
                        }
                        """));
    }

    @Test
    @DirtiesContext
    void getCharacterIntegrationTest_WhenGet1_ThenReturnJsonWithCharacter1() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/asterix/characters?name=Asterix&age=35&profession=Warrior"))
                .andExpect(status().is(200))
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        List<Character> resultList = objectMapper.readValue(resultAsString, new TypeReference<List<Character>>() {
        });
        Character asterix = resultList.getFirst();

        mockMvc.perform(get("/api/asterix/characters/" + asterix.getId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                          "name":"Asterix",
                          "age":35,
                          "profession":"Warrior"
                        }
                        """))
                .andExpect(jsonPath("$.id").value(asterix.getId()));
    }

    @Test
    @DirtiesContext
    void deleteCharacterIntegrationTest_WhenDelete1_Then200AndDeletedInDb() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/asterix/characters?name=Asterix"))
                .andExpect(status().is(200))
                .andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        List<Character> resultAsList = objectMapper.readValue(resultAsString, new TypeReference<List<Character>>() {
        });
        Character asterix = resultAsList.getFirst();

        mockMvc.perform(delete("/api/asterix/characters/" + asterix.getId()))
                .andExpect(status().is(200));

        mockMvc.perform(get("/api/asterix/characters"))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        [
                            {
                              "name":"Obelix",
                              "age":25,
                              "profession":"Deliveryman"
                            },
                            {
                              "name":"Omnifix",
                              "age":17,
                              "profession":"Deliveryman"
                            }
                        ]
                        """));
    }

    @Test
    @DirtiesContext
    void updateCharacterIntegrationTest_WhenAsterixGetsOlder_ThenReturnAndPersistInDb() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/asterix/characters?name=Asterix"))
                .andExpect(status().is(200))
                .andReturn();
        String resultAsString = result.getResponse().getContentAsString();
        List<Character> resultAsList = objectMapper.readValue(resultAsString, new TypeReference<List<Character>>() {
        });
        Character asterix = resultAsList.getFirst();

        mockMvc.perform(put("/api/asterix/characters/" + asterix.getId())
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
                          "name":"Asterix",
                          "age":36,
                          "profession":"Warrior"
                        }
                        """))
                .andExpect(jsonPath("$.id").value(asterix.getId()));

        mockMvc.perform(get("/api/asterix/characters/" + asterix.getId()))
                .andExpect(status().is(200))
                .andExpect(content().json("""
                        {
                          "name":"Asterix",
                          "age":36,
                          "profession":"Warrior"
                        }
                        """));
    }
}