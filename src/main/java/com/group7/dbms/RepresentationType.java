package com.group7.dbms;


public enum RepresentationType {

    IDENTIFIER,
    PARTIAL,
    FULL;

    public boolean covers(RepresentationType that) {
        return this.ordinal() >= that.ordinal();
    }

}
