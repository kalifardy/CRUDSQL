package com.example.ikitas27.mycrud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.1.7/myCrud/";
    private List<ResultItem>result=new ArrayList<>();
    private RecyclerViewAdapter viewAdapter;
    
     private RecyclerView recyclerView;
    ProgressBar progressBar;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DAFTAR MAHASISWA");

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        
        viewAdapter= new RecyclerViewAdapter(this,result);
        RecyclerView.LayoutManager mlayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mlayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        
        loadData();
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        RegisterApi api=retrofit.create(RegisterApi.class);
        Call<Value>call=api.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);

                if(value.equals("1")){
                    result=response.body().getResult();
                    viewAdapter=new RecyclerViewAdapter(ViewActivity.this,result);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
