package com.takeHomeProject.EarthquakeAPI.Service;

import com.takeHomeProject.EarthquakeAPI.Model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EarthquakeApiService extends BaseService {

    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(String country, String days) throws IOException {

        LocalDate endDate = LocalDate.now().plusDays(1);
        LocalDate startDate = LocalDate.now().minusDays(Integer.parseInt(days));

        List<Double> countryBorderBox = getCountryBorderBox(country);

        if(Objects.isNull(countryBorderBox)){
            return new ResponseEntity<>(new BaseResponse("Invalid Country Name"),HttpStatus.BAD_REQUEST);
        }

        GeoJson responseObject = earthquakeDataRequest(startDate, endDate, countryBorderBox);

        if (responseObject.getFeatures().isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(String.format("No earthquakes were recorded in the past %s days", days)), HttpStatus.NOT_FOUND);
        }

        List<EarthquakeData> earthquakeDataList = castFeatureList(responseObject.getFeatures(), country);
        return new ResponseEntity<>(new BaseResponse(earthquakeDataList), HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> getEarthquakeDataByCountryAndDateRange(String country, String sDate, String eDate) throws IOException {
        LocalDate startDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(eDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Double> countryBorderBox = getCountryBorderBox(country);

        if(Objects.isNull(countryBorderBox)){
            return new ResponseEntity<>(new BaseResponse("Invalid Country Name"),HttpStatus.BAD_REQUEST);
        }

        GeoJson responseObject = earthquakeDataRequest(startDate, endDate, countryBorderBox);

        if (responseObject.getFeatures().isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(String.format("No earthquakes were recorded between %s and %s", endDate, startDate)), HttpStatus.NOT_FOUND);
        }

        List<EarthquakeData> earthquakeDataList = castFeatureList(responseObject.getFeatures(), country);
        return new ResponseEntity<>(new BaseResponse(earthquakeDataList), HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> filterEarthquakes(String country, String sDate, String eDate, Integer minMagnitude, Integer maxMagnitude, Integer minDepth, Integer maxDepth, OrderByEnum orderBy) throws IOException {

        LocalDate startDate = LocalDate.parse(sDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse(eDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Double> countryBorderBox = getCountryBorderBox(country);

        if(Objects.isNull(countryBorderBox)){
            return new ResponseEntity<>(new BaseResponse("Invalid Country Name"),HttpStatus.BAD_REQUEST);
        }

        GeoJson responseObject = filterEarthquakeDataRequest(startDate, endDate, countryBorderBox,minMagnitude,maxMagnitude,minDepth,maxDepth,orderBy);

        if (responseObject.getFeatures().isEmpty()) {
            return new ResponseEntity<>(new BaseResponse(String.format("No matching earthquakes were recorded between %s and %s", startDate, endDate)), HttpStatus.NOT_FOUND);
        }
        List<EarthquakeData> earthquakeDataList = castFeatureList(responseObject.getFeatures(), country);
        return new ResponseEntity<>(new BaseResponse(earthquakeDataList), HttpStatus.OK);
    }

}
