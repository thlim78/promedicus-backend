package com.promedicus.backend.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    INTERSEX ("Intersex"),
    UNKNOWN ("Unknown");

    private String description;

    Gender (String description) {
        this.description = description;
    }

    @JsonCreator
    public static Gender fromString(final String description) {
        for (Gender type : Gender.values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

}
