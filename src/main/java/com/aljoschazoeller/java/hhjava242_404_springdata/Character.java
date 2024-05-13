package com.aljoschazoeller.java.hhjava242_404_springdata;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("characters")
public record Character (
        String id,
        String name,
        int age,
        String profession) {
}
