package com.takeHomeProject.EarthquakeAPI.Controller;

import com.takeHomeProject.EarthquakeAPI.Model.BaseResponse;
import com.takeHomeProject.EarthquakeAPI.Service.EarthquakeApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/EarthquakeData")
@RestController
public class EarthquakeApiController {

    // If Autowiring is not welcome we can always do a constructor injection
    //@Autowired
    //EarthquakeApiService earthquakeApiService;
    EarthquakeApiService earthquakeApiService;

    public EarthquakeApiController(EarthquakeApiService earthquakeApiService){
        this.earthquakeApiService = earthquakeApiService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(@RequestParam String country, @RequestParam String days) throws IOException {
            return earthquakeApiService.getEarthquakeDataByCountry(country,days);
    }

}
