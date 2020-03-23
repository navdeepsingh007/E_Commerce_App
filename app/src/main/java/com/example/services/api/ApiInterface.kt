package com.example.services.api

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("mobile/auth/login")
    fun callLogin(@Body jsonObject : JsonObject) : Call<JsonObject>

    /*@POST("login/")
    fun callLogin(@Body jsonObject : JsonObject) : Call<JsonObject>*/
    @Multipart
    @POST("register/")
    fun finishRegistartion(
        @PartMap mHashMap : HashMap<String,
                RequestBody>, @Part image : MultipartBody.Part?
    ) : Call<JsonObject>

    @Multipart
    @POST("mobile/profile/updateprofile")
    fun callUpdateProfile(
        @PartMap mHashMap : HashMap<String,
                RequestBody>, @Part image : MultipartBody.Part?
    ) : Call<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("checkPhoneNumber/")
    fun checkPhoneExistence(@Body jsonObject : JsonObject) : Call<JsonObject>

    @POST("mobile/auth/logout")
    fun callLogout(@Body mJsonObject : JsonObject) : Call<JsonObject>

    @POST("resetpassword/")
    fun resetPassword(@Body mJsonObject : JsonObject) : Call<JsonObject>

    //@POST("resetpassword/")
    //fun getProfile(@Body mJsonObject : JsonObject) : Call<JsonObject>
    @POST("users/changepassword/")
    fun chnagePassword(@Body mJsonObject : JsonObject) : Call<JsonObject>

    @GET("mobile/profile/getProfile")
    fun getProfile() : Call<JsonObject>

    @GET("driver/vehicle/latLongList")
    fun getVehicleList() : Call<JsonObject>

    @GET("service/driver/getServiceList")
    fun getServicesList(@Query("status") status : String) : Call<JsonObject>

    @GET("fuel/driver/getFuelList")
    fun getFuelEntryList() : Call<JsonObject>

    @GET("notification/driver/getList")
    fun getNotificationList() : Call<JsonObject>

    @DELETE("notification/driver/clearAll")
    fun clearAllNotification() : Call<JsonObject>

    @GET("vendor/getVendorList")
    fun getVendorList() : Call<JsonObject>

    @GET("job/getDriverJob")
    fun getJobs(@Query("acceptStatus") userId : String) : Call<JsonObject>

    @GET("job/driver/jobsHistory")
    fun getJobsHistory(@Query("progressStatus") userId : String) : Call<JsonObject>

    @POST("job/driver/changeJobStatus")
    fun startCompleteJob(@Body mJsonObject : JsonObject) : Call<JsonObject>

    @POST("job/driver/acceptRejectJob")
    fun acceptRejectJob(@Body mJsonObject : JsonObject) : Call<JsonObject>

    @Multipart
    @POST("fuel/addFuel")
    fun callAddFuelEntry(
        @PartMap mHashMap : HashMap<String,
                RequestBody>, @Part image : MultipartBody.Part?
    ) : Call<JsonObject>

    @Multipart
    @POST("service/updateServiceEntry")
    fun callUpdateService(
        @PartMap mHashMap : HashMap<String,
                RequestBody>, @Part image : MultipartBody.Part?
    ) : Call<JsonObject>

}