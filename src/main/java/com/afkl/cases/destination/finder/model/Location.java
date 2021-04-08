package com.afkl.cases.destination.finder.model;

import lombok.Data;

import java.util.Set;

@Data
public class Location {
    private String code;
    private String name;
    private String description;
    private Coordinates coordinates;
    private Location parent;
    private Set<Location> children;
}
