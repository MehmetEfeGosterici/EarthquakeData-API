package com.takeHomeProject.EarthquakeAPI.Controller;

import com.takeHomeProject.EarthquakeAPI.Model.BaseResponse;
import com.takeHomeProject.EarthquakeAPI.Model.OrderByEnum;
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

    public EarthquakeApiController(EarthquakeApiService earthquakeApiService) {
        this.earthquakeApiService = earthquakeApiService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getEarthquakeDataByCountry(@RequestParam String country, @RequestParam String days) throws IOException {
        return earthquakeApiService.getEarthquakeDataByCountry(country, days);
    }

    @GetMapping("/dateRange")
    public ResponseEntity<BaseResponse> getEarthquakeDataByCountryAndDateRange(@RequestParam String country, @RequestParam String startDate, @RequestParam String endDate) throws IOException {
        return earthquakeApiService.getEarthquakeDataByCountryAndDateRange(country, startDate, endDate);
    }

    @GetMapping("/filter")
    public ResponseEntity<BaseResponse> filterEarthquakeData(@RequestParam String country, @RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) Integer minMagnitude, @RequestParam(required = false) Integer maxMagnitude, @RequestParam(required = false) Integer minDepth, @RequestParam(required = false) Integer maxDepth, @RequestParam(required = false) Integer limit, @RequestParam(required = false) OrderByEnum orderBy) throws IOException {
        return earthquakeApiService.filterEarthquakes(country, startDate, endDate, minMagnitude, maxMagnitude, minDepth, maxDepth, limit, orderBy);
    }

}
