package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

import java.util.List;

@Data
public class Geometry {

    String type;
    List<Double> coordinates;
}
