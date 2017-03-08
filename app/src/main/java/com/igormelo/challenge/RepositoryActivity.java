package com.igormelo.challenge;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.igormelo.challenge.adapters.RepoAdapter;
import com.igormelo.challenge.models.Item;
import com.igormelo.challenge.services.RetrofitService;
import com.igormelo.challenge.utils.EndlessRecyclerOnScrollListener;
import java.util.ArrayList;
import com.igormelo.challenge.Repo;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepositoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RepoAdapter repoAdapter;
    private List<Item> repositories = new ArrayList<>();
    private int page = 1;
    private int totalPages;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager linearLayoutManager;
    private RetrofitService retrofitService;
    private final String languageStatic = "language:Java", stars = "stars";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setActionbar();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe);
        button = (Button)findViewById(R.id.button);
        configRecyclerView();
        configToTheTop();
        retrofitService = RetrofitService.retrofit.create(RetrofitService.class);
        getRepositories(languageStatic, stars, page);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                repositories = new ArrayList<Item>();
                repoAdapter = new RepoAdapter(getApplicationContext(), repositories);
                recyclerView.setAdapter(repoAdapter);
                getRepositories(languageStatic, stars, 1);
            }
        });
    }
    private void configToTheTop(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibility = (linearLayoutManager.findFirstCompletelyVisibleItemPosition()!=0) ? View.VISIBLE : View.GONE;
                button.setVisibility(visibility);
            }
        });
        button.setOnClickListener(e -> recyclerView.scrollToPosition(0));
    }

    private void configRecyclerView() {
        repoAdapter = new RepoAdapter(getApplicationContext(), repositories);
        linearLayoutManager = new LinearLayoutManager(RepositoryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(repoAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= totalPages) {
                    repoAdapter.setTotalPages(totalPages);
                    repoAdapter.setCurrentPage(current_page);
                    getRepositories(languageStatic, stars, current_page);
                } else {
                    Toast.makeText(RepositoryActivity.this, "Total" + totalPages, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setActionbar() {
        getSupportActionBar().setTitle(languageStatic);

    }

    private void getRepositories(String language, String sort, int page) {
        Call<Repo> call = retrofitService.getRepositories(language, sort, page);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                if (response.isSuccessful()) {
                    totalPages = 3;
                    final Repo repo = response.body();
                    repositories.addAll(repo.getItems());
                    repoAdapter.notifyDataSetChanged();
                }
                if(swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                if(swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
                Toast.makeText(RepositoryActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
