package com.example.demo_retrofit;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);
}