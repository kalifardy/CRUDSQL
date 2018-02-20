package com.example.ikitas27.mycrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

public class UpdateActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.1.7/myCrud/";
    private RadioButton radioSexButton;
    private ProgressDialog progress;


     EditText editTextNPM;
     EditText editTextNama;
     EditText editTextKelas;
     Button buttonUpdate;
    RadioGroup radioJadwal;
    RadioButton pagi;
    RadioButton siang;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        editTextNPM = (EditText) findViewById(R.id.editTextNPM);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        radioJadwal = (RadioGroup) findViewById(R.id.radioJadwal);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        pagi=(RadioButton)findViewById(R.id.radioPagi) ;
        siang=(RadioButton)findViewById(R.id.radioSiang) ;


        Intent intent = getIntent();
        String npm = intent.getStringExtra("npm");
        String nama = intent.getStringExtra("nama");
        String kelas = intent.getStringExtra("kelas");
        String sesi = intent.getStringExtra("jadwal");

        editTextNPM.setText(npm);
        editTextNama.setText(nama);
        editTextKelas.setText(kelas);

        if (sesi.equals("Pagi (09.00-11.00 WIB)")) {
            pagi.setChecked(true);
        } else {
            siang.setChecked(true);
        }


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = new ProgressDialog(UpdateActivity.this);
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
                Call<Value> call = api.ubah(npm, nama, kelas, jadwal);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        Log.d("response", response.body().toString());

                        if (response.isSuccessful()) {
                            Log.d("val", String.valueOf(response.body().getValue()));


                            String value = String.valueOf(response.body().getValue());
                            String message = response.body().getMessage();
                            progress.dismiss();

                            if (value.equals("1")) {
                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(UpdateActivity.this, "Jaringan eror!", Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
