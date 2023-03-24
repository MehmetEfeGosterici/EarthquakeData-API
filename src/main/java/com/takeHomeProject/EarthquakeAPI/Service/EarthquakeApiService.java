package com.takeHomeProject.EarthquakeAPI.Service;

import com.takeHomeProject.EarthquakeAPI.Model.BaseResponse;
import com.takeHomeProject.EarthquakeAPI.Model.EarthquakeData;
import com.takeHomeProject.EarthquakeAPI.Model.Feature;
import com.takeHomeProject.EarthquakeAPI.Model.GeoJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class EarthquakeApiService extends BaseService {

    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(String country, String days) throws IOException {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().minusDays(Integer.parseInt(days));

        List<Double> countryBorderBox = getCountryBorderBox(country);
        String responseString = EntityUtils.toString(Request
                .Get(String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minlatitude=%s&maxlatitude=%s&minlongitude=%s&maxlongitude=%s",
                        startDate, endDate, countryBorderBox.get(1), countryBorderBox.get(3), countryBorderBox.get(0), countryBorderBox.get(2)))
                .execute()
                .returnResponse()
                .getEntity());

        GeoJson responseObject = gson.fromJson(responseString, GeoJson.class);

        if(responseObject.getFeatures().isEmpty()){
            return new ResponseEntity<>(new BaseResponse("No matching data found"),HttpStatus.NO_CONTENT);
        }

        List<EarthquakeData> earthquakeDataList = new ArrayList<>();
        for (Feature feature : responseObject.getFeatures()) {
            EarthquakeData earthquakeData = new EarthquakeData();
            earthquakeData.setCountry(StringUtils.capitalize(country.toLowerCase()));
            earthquakeData.setPlace(feature.getProperties().getPlace());
            earthquakeData.setMagnitude(feature.getProperties().getMag());
            earthquakeData.setType(feature.getProperties().getType());
            earthquakeData.setTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(feature.getProperties().getTime())), TimeZone.getDefault().toZoneId()));
            earthquakeData.setCoordinates(feature.getGeometry().getCoordinates());
            earthquakeData.setUrl(feature.getProperties().getUrl());
            earthquakeDataList.add(earthquakeData);
        }

        return new ResponseEntity<>(new BaseResponse(earthquakeDataList), HttpStatus.OK);
    }

}
