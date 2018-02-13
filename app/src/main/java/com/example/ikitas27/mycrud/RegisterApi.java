package com.example.ikitas27.mycrud;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ikitas27 on 2/12/2018.
 */

public interface RegisterApi {

    @FormUrlEncoded
    @POST("/insert.php")
    Call<Value> daftar(@Field("npm") String npm,
                       @Field("nama") String nama,
                       @Field("kelas") String kelas);
//                       @Field("jadwal") String jadwal

}
