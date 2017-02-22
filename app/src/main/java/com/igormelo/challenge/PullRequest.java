package com.igormelo.challenge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.igormelo.challenge.Adapters.PullRequestAdapter;
import com.igormelo.challenge.Models.PullRequests.Response;
import com.igormelo.challenge.Services.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;

public class PullRequest extends AppCompatActivity {
    private PullRequestAdapter adapter;
    private List<Response> items;// = new ArrayList<>();
    private int page = 1;
    private String repo;
    private String creator;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_request);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
            creator = getIntent().getStringExtra("creator");
            repo = getIntent().getStringExtra("repository");
            RetrofitService retrofitService = RetrofitService.retrofit.create(RetrofitService.class);
            Call<List<Response>> call = retrofitService.getPull(creator, repo);
            call.enqueue(new Callback<List<Response>>() {
                @Override
                public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                    if(response.isSuccessful()) {
                        List<Response> res = response.body();
                        //Response res = response.body();
                        items = res;
                        adapter = new PullRequestAdapter(getApplicationContext(), items);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(PullRequest.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Response>> call, Throwable t) {

                }

            });

    }
}
