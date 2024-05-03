package com.group7.dbms;


public enum RepresentationType {

    PARTIAL,
    FULL;

    public boolean covers(RepresentationType that) {
        return this.ordinal() >= that.ordinal();
    }

}
