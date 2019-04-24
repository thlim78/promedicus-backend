package com.promedicus.backend.model.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by tolim on 14/08/2018.
 */
public enum Category {
    NORMAL("Normal"),
    INPATIENT("Inpatient"),
    EMERGENCY("Emergency"),
    OUTPATIENT("Outpatient");

    private String description;

    Category(String description) {
        this.description = description;
    }

    @JsonCreator
    public static Category fromString(final String description) {
        for (Category type : Category.values()) {
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
