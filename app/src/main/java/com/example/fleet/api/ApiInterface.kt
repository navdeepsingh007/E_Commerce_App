package com.example.fleet.api

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList
import java.util.HashMap

interface ApiInterface {


    @Headers("Content-Type: application/json")
    @POST("login/")
    fun callLogin(@Body jsonObject: JsonObject):Call<JsonObject>



    @Multipart
    @POST("register/")
    fun finishRegistartion(@PartMap mHashMap: HashMap<String,
            RequestBody>,@Part image :MultipartBody.Part?
    ): Call<JsonObject>



    @Headers("Content-Type: application/json")
    @POST("checkPhoneNumber/")
    fun checkPhoneExistence(@Body jsonObject: JsonObject):Call<JsonObject>

    @POST("logout/")
    fun callLogout(@Body mJsonObject:JsonObject):Call<JsonObject>


    @POST("resetpassword/")
    fun resetPassword(@Body mJsonObject: JsonObject): Call<JsonObject>



    @POST("users/changepassword/")
    fun chnagePassword(@Body mJsonObject: JsonObject): Call<JsonObject>







}