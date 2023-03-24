package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EarthquakeData {

    LocalDateTime time;
    String country;
    String place;
    Double magnitude;
    String type;
    List<Double> coordinates;
    String url;
}
