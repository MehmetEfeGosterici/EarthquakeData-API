package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

@Data
public class Properties {

    Double mag;
    String place;
    String type;
    String title;
    String url;
    String display_name;
    String time;

}
