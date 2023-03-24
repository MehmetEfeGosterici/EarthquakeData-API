package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

@Data
public class Properties {

    int mag;
    String place;
    String type;
    String title;
    String url;
    String display_name;

}
