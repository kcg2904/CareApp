package com.KingsStory.Application

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @POST("test.php")
    @FormUrlEncoded
    fun test(
        @Field("id") id: String,
        @Field("pass") pass: String
    ): Call<User2>

    @POST("test4.php")
    @FormUrlEncoded
    fun companylogin(
        @Field("id") id: String,
        @Field("pass") pass: String
    ): Call<CompanyDto>

    @POST("test2.php")
    @FormUrlEncoded
    fun insert(
        @Field("id") id: String,
        @Field("pass") pass: String,
        @Field("objectivearea") objectivearea: String,
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("birthday") birthday: String,
        @Field("phone") phone: String,
        @Field("email") email: String
    ): Call<text>


    @Multipart
    @POST("test123.php")
    fun resume(
        @Part("fileName") filename: String, //파일이름
        @Part imageFile: MultipartBody.Part // 이게 파일
    ): Call<String>

    @POST("resume.php")
    @FormUrlEncoded
    fun resumedata(
        @Field("type")type:String,
        @Field("title") title:String,
        @Field("name") name : String,
        @Field("phone") phone : String,
        @Field("email") email : String,
        @Field("address") address : String,
        @Field("objectivearea") objectivearea : String,
        @Field("pattime") pattime : String,
        @Field("career") career : String?,
        @Field("certificate") certificate : String,
        @Field("specialty") specialty : String,
        @Field("img") img : String,
        @Field("birthyear") birthyear : String,
        @Field("suggestion") suggestion : String,
        @Field("emailopen") emailopen : String,
        @Field("addressopen") addressopen : String,
        @Field("resumeopen") resumeopen : String,
        @Field("id")id : String,
        @Field("lat")lat:String,
        @Field("lng")lng:String,
    ):Call<String>

    @POST("resume.php")
    @FormUrlEncoded
    fun resumeselect(
        @Field("type")type:String,
        @Field("id")id:String
    ):Call<resumeDto>

    @POST("cookie.php")
    @FormUrlEncoded
    fun cookie(
        @Field("type")type:String,
        @Field("account")account:String,
        @Field("cashAmount")cashAmount:Int,
        @Field("amount")amount:Int
    ):Call<String>

    @POST("cookie.php")
    @FormUrlEncoded
    fun cookieSelect(
        @Field("type")type:String,
        @Field("id")account:String,
    ):Call<Int>

    @POST("test3.php")
    @FormUrlEncoded
    fun insertcompany(
        @Field("id") id: String,
        @Field("pass") pass: String,
        @Field("businessnumber") businessnumber: String,
        @Field("companyname") companyname: String,
        @Field("ceo") ceo: String,
        @Field("business") business: String,
        @Field("zipcode") zipcode: String,
        @Field("address") address: String,
        @Field("name") name: String,
        @Field("date") date: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("sms") sms: Boolean
    ): Call<text>

    @POST("job_offer.php")
    @FormUrlEncoded
    fun getUcaregiverList(
        @Field("type")type:String,
        @Field("objectivearea")objectivearea:String
    ):Call<UcareDto>

    @POST("job_offer.php")
    @FormUrlEncoded
    fun joboffer(
        @Field("type")type:String,
        @Field("id")id:String,
        @Field("num")num:Int,
        @Field("lat")lat:String,
        @Field("lng")lng:String,
        @Field("carename")carenmae:String,
        @Field("address")address:String,
        @Field("parttime")parttime:String,
        @Field("gender")gender:String,
        @Field("rank")rank:String,
        @Field("phone")phone:String,
        @Field("company")company:String,
        @Field("name")name:String,
        @Field("endday")endday:String,

    ):Call<String>

    @POST("resume.php")
    @FormUrlEncoded
    fun getUcareList(
        @Field("type")type:String,
        @Field("objectivearea")objectivearea:String
    ): Call<UcaregiverDao>

}