package com.takeHomeProject.EarthquakeAPI.Service;

import com.google.gson.Gson;
import com.takeHomeProject.EarthquakeAPI.Model.GeoJson;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    Gson gson = new Gson();

    public List<Double> getCountryBorderBox(String country) throws IOException {
        String jsonResponseString = EntityUtils.toString(Request
                .Get(String.format("https://nominatim.openstreetmap.org/search?country=%s&format=geojson", country))
                .execute()
                .returnResponse()
                .getEntity());
        GeoJson responseObject = gson.fromJson(jsonResponseString, GeoJson.class);
        return responseObject.getFeatures().get(0).getBbox();
    }
}
