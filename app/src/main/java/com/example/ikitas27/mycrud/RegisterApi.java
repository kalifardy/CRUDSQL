package com.example.ikitas27.mycrud;

import com.example.ikitas27.mycrud.Response.ResponseInsert;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ikitas27 on 2/12/2018.
 */

public interface RegisterApi {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseInsert> daftar(@Field("npm") String npm,
                                @Field("nama") String nama,
                                @Field("kelas") String kelas,
                                @Field("jadwal") String jadwal
    );

    @POST("update.php")
    Call<Value> ubah(@Field("npm") String npm,
                                @Field("nama") String nama,
                                @Field("kelas") String kelas,
                                @Field("jadwal") String jadwal
    );
    @GET("view.php")
    Call<Value>view();

}
