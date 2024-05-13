package com.aljoschazoeller.java.hhjava242_404_springdata;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("characters")
@Builder
public record Character (
        String id,
        String name,
        int age,
        String profession,
        Instant createdAt,
        Instant updatedAt) {
}
