package com.takeHomeProject.EarthquakeAPI.Service;

import com.google.gson.Gson;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BaseService {

    Gson gson;

    public String getCountryBorderBox(String country) throws IOException {
        String jsonResponseString = EntityUtils.toString(Request
                .Get(String.format("https://nominatim.openstreetmap.org/search?country=%s&format=geojson", country))
                .execute()
                .returnResponse()
                .getEntity());

    }
}
