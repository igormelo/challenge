package com.igormelo.challenge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.igormelo.challenge.Adapters.RepoAdapters;
import com.igormelo.challenge.Models.Item;
import com.igormelo.challenge.Repo;
import com.igormelo.challenge.Services.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RepoAdapters repoAdapters;
    private List<Item> repositories = new ArrayList<>();
    private int page = 3;
    private SwipeRefreshLayout swipeContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        RetrofitService retrofitService = RetrofitService.retrofit.create(RetrofitService.class);

        Call<Repo> call = retrofitService.getRepositories("language:Java", "stars", page);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Repo repo = response.body();
                    repositories = repo.getItems();
                    repoAdapters = new RepoAdapters(getApplicationContext(),repositories);
                    recyclerView.setAdapter(repoAdapters);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
