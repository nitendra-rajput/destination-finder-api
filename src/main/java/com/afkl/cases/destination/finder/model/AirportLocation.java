package com.afkl.cases.destination.finder.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AirportLocation {
    private String code;
    private String name;
    private String description;
}
