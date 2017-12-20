package com.hdh.common.http;

import com.hdh.common.http.pojo.RequestEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *  api最后封装
 */

public interface APIService {
    @GET("/router")
    Observable<String> get(@Query("sysParam") String syParams, @Query("bizParam") String bizParams);

    @FormUrlEncoded
    @POST("/router")
    Observable<String> post(@Field("sysParam") String syParams, @Field("bizParam") String bizParams);

    @FormUrlEncoded
    @PUT("/router")
    Observable<String> put(@Field("sysParam") String syParams, @Field("bizParam") String bizParams);

    @FormUrlEncoded
    @DELETE("/router")
    Observable<String> delete(@Field("sysParam") String syParams, @Field("bizParam") String bizParams);

    @retrofit2.http.HTTP(method = "DELETE", path = "/router", hasBody = true)
    Observable<String> deleteInvoke(@Body RequestEntity request);

    @GET("/{path}")
    Observable<String> path(@Path("path") String path);
}
