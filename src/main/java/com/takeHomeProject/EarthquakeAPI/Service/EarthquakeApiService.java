package com.takeHomeProject.EarthquakeAPI.Service;

import com.takeHomeProject.EarthquakeAPI.Model.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EarthquakeApiService extends BaseService {

    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(String country, String day){


    }

}
