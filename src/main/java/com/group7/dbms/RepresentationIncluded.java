package com.group7.dbms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.group7.dbms.RepresentationType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RepresentationIncluded {
    RepresentationType value();
}
