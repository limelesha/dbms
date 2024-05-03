package com.group7.dbms;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import com.group7.dbms.RepresentationIncluded;


public class RepresentationExclusionStrategy implements ExclusionStrategy {

    final RepresentationType type;

    public RepresentationExclusionStrategy(RepresentationType type) {
        this.type = type;
    }

    public boolean shouldSkipClass(Class<?> cls) {
        // no exclusion rules bases on field type
        return false;
    }

    public boolean shouldSkipField(FieldAttributes field) {
        RepresentationIncluded annotation =
            field.getAnnotation(RepresentationIncluded.class);
        return (
            annotation == null
            || !this.type.covers(annotation.value())
        );
    }

}
