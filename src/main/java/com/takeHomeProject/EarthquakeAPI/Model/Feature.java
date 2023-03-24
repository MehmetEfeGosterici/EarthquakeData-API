package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

import java.util.List;

@Data
public class Feature {
    String type;
    Object properties;
    List<Double> bbox;
    Object geometry;

}
