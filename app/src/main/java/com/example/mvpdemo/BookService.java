package com.example.mvpdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BookService {
    @POST("/hzed/loan-api/index/V2/getProducts")
    Call<ResponseBody> getBook();
}
