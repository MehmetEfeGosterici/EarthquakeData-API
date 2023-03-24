package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

import java.util.List;

@Data
public class GeoJson {
    String type;
    String license;
    List<Feature> features;
}
