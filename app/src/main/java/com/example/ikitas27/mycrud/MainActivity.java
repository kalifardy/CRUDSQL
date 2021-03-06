package com.example.ikitas27.mycrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ikitas27.mycrud.Response.ResponseInsert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.1.7/myCrud/";
    private RadioButton radioSexButton;
    private ProgressDialog progress;


    private EditText editTextNPM;
    private EditText editTextNama;
    private EditText editTextKelas;
    private Button buttonDaftar;
    private Button buttonLihat;
     RadioGroup radioJadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNPM = (EditText) findViewById(R.id.editTextNPM);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        radioJadwal = (RadioGroup) findViewById(R.id.radioJadwal);
        buttonDaftar = (Button) findViewById(R.id.buttonDaftar);
        buttonLihat = (Button) findViewById(R.id.btnLihat);

        buttonLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(i);
            }
        });

        buttonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(MainActivity.this);
                progress.setCancelable(false);
                progress.setMessage("Loading....");
                progress.show();

                String npm = editTextNPM.getText().toString();
                String nama = editTextNama.getText().toString();
                String kelas = editTextKelas.getText().toString();

                int selectedId = radioJadwal.getCheckedRadioButtonId();

                radioSexButton = (RadioButton) findViewById(selectedId);
                String jadwal = radioSexButton.getText().toString();

                Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
                RegisterApi api = retrofit.create(RegisterApi.class);
                Call<ResponseInsert> call = api.daftar(npm, nama, kelas, jadwal);
                call.enqueue(new Callback<ResponseInsert>() {
                    @Override
                    public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                        Log.d("response", response.body().toString());

                        if (response.isSuccessful()) {
                            Log.d("val", String.valueOf(response.body().getValue()));


                            String value = String.valueOf(response.body().getValue());
                            String message = response.body().getMessage();
                            progress.dismiss();

                            if (value.equals("1")) {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseInsert> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(MainActivity.this, "Jaringan eror!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



}