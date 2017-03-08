package com.igormelo.challenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.igormelo.challenge.adapters.PullRequestAdapter;
import com.igormelo.challenge.models.PullRequests.PullResponse;
import com.igormelo.challenge.services.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class PullRequestActivity extends AppCompatActivity {
    private PullRequestAdapter adapter;
    private List<PullResponse> items = new ArrayList<>();
    private int page = 1;
    String repo;
    String creator;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pull_request);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_pull);
        creator = getIntent().getStringExtra("creator");
        repo = getIntent().getStringExtra("repository");
        setActionBar();
        getPullRequests();




}
    private void configRecyclerView(){

    }
    private void setActionBar(){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(creator);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void getPullRequests(){
        RetrofitService retrofitService = RetrofitService.retrofit.create(RetrofitService.class);
        Call<List<PullResponse>> call = retrofitService.getPull(creator, repo, page);
        call.enqueue(new Callback<List<PullResponse>>() {
            @Override
            public void onResponse(Call<List<PullResponse>> call, Response<List<PullResponse>> response) {
                if(response.isSuccessful()) {
                    List<PullResponse> res = response.body();
                    items = res;
                    adapter = new PullRequestAdapter(getApplicationContext(),items);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.smoothScrollToPosition(0);

                } else {
                    Toast.makeText(PullRequestActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PullResponse>> call, Throwable t) {

            }

        });

    }
}
