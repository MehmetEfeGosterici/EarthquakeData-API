package com.takeHomeProject.EarthquakeAPI.Model;

import lombok.Data;

@Data
public class BaseResponse {

    Object data;
    String message;

    public BaseResponse(Object data){
        this.data = data;
    }
    public BaseResponse(String message){
        this.message = message;
    }
    public BaseResponse(Object data, String message){
        this.data = data;
        this.message = message;
    }

}
