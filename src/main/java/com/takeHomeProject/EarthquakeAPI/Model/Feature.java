package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

import java.util.List;

@Data
public class Feature {
    String type;
    Properties properties;
    List<Double> bbox;
    Geometry geometry;

}
