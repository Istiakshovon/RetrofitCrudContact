package com.istiak.retrofitcrudcontact.remote;

import com.istiak.retrofitcrudcontact.Constant;
import com.istiak.retrofitcrudcontact.model.Contacts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("retrofit/POST/readcontacts.php")
    Call<List<Contacts>> getContacts();
//    @FormUrlEncoded
//    @POST("retrofit/POST/addcontact.php")
//    public Call<Contacts> insertUser(
//            @Field("name") String name,
//            @Field("email") String email);


    //for signup
    @FormUrlEncoded
    @POST("retrofit/POST/signup.php")
    Call<Contacts> signUp(
            @Field(Constant.KEY_NAME) String username,
            @Field(Constant.KEY_CONTACT) String contact,
            @Field(Constant.KEY_PASSWORD) String password);



    //for login
    @FormUrlEncoded
    @POST("retrofit/POST/login.php")
    Call<Contacts> login(
            @Field(Constant.KEY_NAME) String username,
            @Field(Constant.KEY_PASSWORD) String password);
//
//
    @FormUrlEncoded
    @POST("retrofit/POST/editcontact.php")
    Call<Contacts> editUser(
            @Field("id") String id,
            @Field(Constant.KEY_NAME) String name,
            @Field(Constant.KEY_CONTACT) String contact,
            @Field(Constant.KEY_ADDRESS) String address);
//
//
    @FormUrlEncoded
    @POST("retrofit/POST/deletecontact.php")
    Call<Contacts> deleteUser(
            @Field("id") int id
    );
}
