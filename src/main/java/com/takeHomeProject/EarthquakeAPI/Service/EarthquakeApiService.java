package com.takeHomeProject.EarthquakeAPI.Service;

import com.takeHomeProject.EarthquakeAPI.Model.BaseResponse;
import com.takeHomeProject.EarthquakeAPI.Model.GeoJson;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EarthquakeApiService extends BaseService {

    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(String country, String days) throws IOException {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().minusDays(Integer.parseInt(days));

        List<Double> countryBorderBox = getCountryBorderBox(country);
        String responseString = EntityUtils.toString(Request
                .Get(String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minlatitude=%s&maxlatitude=%s&minlongitude=%s&maxlongitude=%s",
                        startDate, endDate, countryBorderBox.get(0), countryBorderBox.get(2), countryBorderBox.get(1), countryBorderBox.get(3)))
                .execute()
                .returnResponse()
                .getEntity());

        GeoJson responseObject = gson.fromJson(responseString, GeoJson.class);

        return new ResponseEntity<>(new BaseResponse(responseObject), HttpStatus.OK);
    }

}
