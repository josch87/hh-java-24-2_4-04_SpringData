package com.aljoschazoeller.java.hhjava242_404_springdata;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("characters")
@Builder
@Data
@With
public class Character {
    private String id;
    private String name;
    private int age;
    private String profession;
    private Instant createdAt;
    private Instant updatedAt;
}
