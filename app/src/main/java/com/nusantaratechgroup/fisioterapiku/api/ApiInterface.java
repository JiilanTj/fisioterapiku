package com.nusantaratechgroup.fisioterapiku.api;

import com.nusantaratechgroup.fisioterapiku.model.LoginRequest;
import com.nusantaratechgroup.fisioterapiku.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}
