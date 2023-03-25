package com.takeHomeProject.EarthquakeAPI.Service;

import com.google.gson.Gson;
import com.takeHomeProject.EarthquakeAPI.Model.EarthquakeData;
import com.takeHomeProject.EarthquakeAPI.Model.Feature;
import com.takeHomeProject.EarthquakeAPI.Model.GeoJson;
import com.takeHomeProject.EarthquakeAPI.Model.OrderByEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BaseService {

    private final Map<String, List<Double>> countryBorderBoxMap = new HashMap<>();
    Gson gson = new Gson();

    public List<Double> getCountryBorderBox(String country) throws IOException {

        if (countryBorderBoxMap.containsKey(StringUtils.capitalize(country.toLowerCase()))) {
            return countryBorderBoxMap.get(StringUtils.capitalize(country.toLowerCase()));
        }

        String jsonResponseString = EntityUtils.toString(Request
                .Get(String.format("https://nominatim.openstreetmap.org/search?country=%s&format=geojson", country))
                .execute()
                .returnResponse()
                .getEntity());
        GeoJson responseObject = gson.fromJson(jsonResponseString, GeoJson.class);

        if (responseObject.getFeatures().isEmpty()) {
            return null;
        }

        countryBorderBoxMap.put(StringUtils.capitalize(country.toLowerCase()), responseObject.getFeatures().get(0).getBbox());
        return responseObject.getFeatures().get(0).getBbox();
    }

    public List<EarthquakeData> castFeatureList(List<Feature> featureList, String country) {

        List<EarthquakeData> responseList = new ArrayList<>();
        for (Feature feature : featureList) {
            EarthquakeData earthquakeData = new EarthquakeData();
            earthquakeData.setCountry(StringUtils.capitalize(country.toLowerCase()));
            earthquakeData.setPlace(feature.getProperties().getPlace());
            earthquakeData.setMagnitude(feature.getProperties().getMag());
            earthquakeData.setType(feature.getProperties().getType());
            earthquakeData.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(feature.getProperties().getTime())), TimeZone.getDefault().toZoneId()));
            earthquakeData.setCoordinates(feature.getGeometry().getCoordinates());
            earthquakeData.setUrl(feature.getProperties().getUrl());
            responseList.add(earthquakeData);
        }
        return responseList;
    }

    public GeoJson earthquakeDataRequest(LocalDate endDate, LocalDate startDate, List<Double> countryBorderBox) throws IOException {
        String responseString = EntityUtils.toString(Request
                .Get(String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minlatitude=%s&maxlatitude=%s&minlongitude=%s&maxlongitude=%s",
                        endDate, startDate, countryBorderBox.get(1), countryBorderBox.get(3), countryBorderBox.get(0), countryBorderBox.get(2)))
                .execute()
                .returnResponse()
                .getEntity());
        return gson.fromJson(responseString, GeoJson.class);
    }

    public GeoJson filterEarthquakeDataRequest(LocalDate endDate, LocalDate startDate, List<Double> countryBorderBox, Integer minMag, Integer maxMag, Integer minDep, Integer maxDep, Integer limit, OrderByEnum orderBy) throws IOException {

        String requestUrl = String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minlatitude=%s&maxlatitude=%s&minlongitude=%s&maxlongitude=%s",
                endDate, startDate, countryBorderBox.get(1), countryBorderBox.get(3), countryBorderBox.get(0), countryBorderBox.get(2));

        if (Objects.nonNull(minMag))
            requestUrl = requestUrl.concat("&minmagnitude=" + minMag);
        if (Objects.nonNull(maxMag))
            requestUrl = requestUrl.concat("&maxmagnitude=" + maxMag);
        if (Objects.nonNull(minDep))
            requestUrl = requestUrl.concat("&mindepth=" + minDep);
        if (Objects.nonNull((maxDep)))
            requestUrl = requestUrl.concat("&maxdepth=" + maxDep);
        if (Objects.nonNull((limit)))
            requestUrl = requestUrl.concat("&limit=" + limit);
        if (Objects.nonNull(orderBy)) {
            String orderByValue = orderBy.toString().replace("_", "-");
            requestUrl = requestUrl.concat("&orderby=" + orderByValue);
        }

        String responseString = EntityUtils.toString(Request
                .Get(requestUrl)
                .execute()
                .returnResponse()
                .getEntity());

        return gson.fromJson(responseString, GeoJson.class);
    }
}
