package com.igormelo.challenge;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.igormelo.challenge.Adapters.RepoAdapters;
import com.igormelo.challenge.Models.Item;
import com.igormelo.challenge.Services.RetrofitService;
import com.igormelo.challenge.Repo;
import com.igormelo.challenge.Utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RepoAdapters repoAdapters;
    private List<Item> repositories = new ArrayList<>();
    private int page = 1;
    private int totalPages;
    private SwipeRefreshLayout swipeContainer;
    private Handler handler;
    private LinearLayoutManager linearLayoutManager;
    private RetrofitService retrofitService;
    private final String languageSatic = "language:Java", stars = "stars";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setActionbar();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);

        configRecyclerView();

        retrofitService = RetrofitService.retrofit.create(RetrofitService.class);
        getRepositories(languageSatic, stars, page);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                repositories = new ArrayList<Item>();
                repoAdapters = new RepoAdapters(getApplicationContext(), repositories);
                recyclerView.setAdapter(repoAdapters);
                getRepositories(languageSatic, stars, 1);
            }
        });
    }

    private void configRecyclerView() {
        repoAdapters = new RepoAdapters(getApplicationContext(), repositories);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(repoAdapters);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= totalPages) {
                    Toast.makeText(MainActivity.this, languageSatic + " Pag:" + current_page, Toast.LENGTH_SHORT).show();
                    getRepositories(languageSatic, stars, current_page);
                } else {
                    Toast.makeText(MainActivity.this, "Total" + totalPages, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setActionbar() {
        getSupportActionBar().setTitle("Language: Java");

    }

    private void getRepositories(String language, String sort, int page) {
        Toast.makeText(MainActivity.this, "buscando: " + language, Toast.LENGTH_SHORT).show();
        Call<Repo> call = retrofitService.getRepositories(language, sort, page);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                if (response.isSuccessful()) {
                    totalPages = response.body().getTotalCount();
                    final Repo repo = response.body();
                    repositories.addAll(repo.getItems());
                    repoAdapters.notifyDataSetChanged();
                }
                if(swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                if(swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
