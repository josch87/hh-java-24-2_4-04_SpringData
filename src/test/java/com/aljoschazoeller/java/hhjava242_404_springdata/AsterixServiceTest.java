package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
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

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters(null, null, null);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c1, c2);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenAge35_ReturnAsterix() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters(null, null, 35);

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

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters(null, "Deliveryman", null);

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

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters("Asterix", null, null);

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

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters(null, "Deliveryman", 25);

        //THEN
        verify(characterRepository).findAll();
        List<Character> expected = List.of(c2);
        assertEquals(expected, actual);
    }

    @Test
    void getAllCharactersTest_WhenProfessionDeliverymanAngAge25AndNameTestifix_ReturnEmptyList() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        when(characterRepository.findAll()).thenReturn(List.of(c1, c2, c3));

        //WHEN
        List<Character> actual = asterixService.getAllCharacters("Testifix", "Deliveryman", 25);

        //THEN
        verify(characterRepository).findAll();
        assertTrue(actual.isEmpty());
    }

    @Test
    void getCharacterByIdTest_When1_GetCharacter1() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        when(characterRepository.findById("1")).thenReturn(Optional.of(c1));

        //WHEN
        Character actual = asterixService.getCharacterById("1");

        //THEN
        verify(characterRepository).findById("1");
        assertEquals(c1, actual);
    }

    @Test
    void getCharacterByIdTest_When20_ThrowException() {
        //GIVEN
        Instant currentDateTime = Instant.now();
        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c2 = new Character("2", "Obelix", 25, "Deliveryman", currentDateTime, currentDateTime);
        Character c3 = new Character("3", "Omnifix", 17, "Deliveryman", currentDateTime, currentDateTime);

        when(characterRepository.findById("20")).thenReturn(Optional.empty());

        //THEN
        assertThrowsExactly(NoSuchElementException.class, () -> asterixService.getCharacterById("20"));
        verify(characterRepository).findById("20");
    }

    @Test
    void updateCharacterByIdTest_WhenAsterixTurns36_UpdateAge() {
        //GIVEN
        Instant currentDateTime = Instant.ofEpochSecond(50000);

        Character c1 = new Character("1", "Asterix", 35, "Warrior", currentDateTime, currentDateTime);
        Character c1Update = new Character("1", "Asterix", 36, "Warrior", currentDateTime, currentDateTime);

        when(characterRepository.findById("1")).thenReturn(Optional.of(c1));
        when(characterRepository.save(any(Character.class))).thenAnswer(returnsFirstArg());
        //WHEN
        Character actual = asterixService.updateCharacterById(c1Update);

        //THEN
        verify(characterRepository).findById("1");
        verify(characterRepository).save(any(Character.class));

        Character expected = new Character("1", "Asterix", 36, "Warrior", currentDateTime, currentDateTime);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getProfession(), actual.getProfession());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
    }


}