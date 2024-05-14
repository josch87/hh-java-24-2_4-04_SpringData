package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    CharacterRepository characterRepository = mock(CharacterRepository.class);
    AsterixService asterixService = new AsterixService(characterRepository);

    @Test
    void getAllCharactersTest_WhenNoParams_ReturnAllCharacters() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1,c2));
        List<Character> actual = asterixService.getAllCharacters(null, null , null);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c1,c2);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenAge35_ReturnAsterix() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1,c2));
        List<Character> actual = asterixService.getAllCharacters(null, null , 35);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c1);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenProfessionDeliveryman_ReturnTwoCharacters() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));
        List<Character> actual = asterixService.getAllCharacters(null, "Deliveryman" , null);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c2, c3);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenNameAsterix_ReturnAsterix() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1,c2));
        List<Character> actual = asterixService.getAllCharacters("Asterix", null , null);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c1);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenProfessionDeliverymanAngAge25_ReturnObelix() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));
        List<Character> actual = asterixService.getAllCharacters(null, "Deliveryman" , 25);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c2);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenProfessionDeliverymanAngAge25AndNameTestifix_ReturnOEmptyList() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        //WHEN
        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));
        List<Character> actual = asterixService.getAllCharacters("Testifix", "Deliveryman" , 25);

        //THEN
        verify(characterRepository).findAll();
        assertTrue(actual.isEmpty());
    }


}