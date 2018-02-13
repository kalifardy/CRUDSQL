package com.example.ikitas27.mycrud;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class MainActivity extends AppCompatActivity {

    public static final String URL="http://192.168.1.6/myCrud/";
    private RadioButton radioSexButton;
     private ProgressDialog progress;


     EditText editTextNPM;
     EditText editTextNama;
     RadioGroup radioGrup;
     EditText editTextKelas;
     Button buttonDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNPM = (EditText) findViewById(R.id.editTextNPM);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextKelas = (EditText) findViewById(R.id.editTextKelas);
        buttonDaftar = (Button) findViewById(R.id.buttonDaftar);

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

               int selectedId = radioGrup.getCheckedRadioButtonId();

               radioSexButton = (RadioButton) findViewById(selectedId);
               String jadwal = radioSexButton.getText().toString();

                Retrofit retrofit =new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
                RegisterApi api = retrofit.create(RegisterApi.class);
                Call<Value>call=api.daftar(npm, nama, kelas);
                call.enqueue(new Callback<Value>() {
                    @Override
                    public void onResponse(Call<Value> call, Response<Value> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();
                        progress.dismiss();
                        if (value.equals("1")){
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Value> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(MainActivity.this, "Jaringan eror!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}