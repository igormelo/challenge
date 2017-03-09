package com.igormelo.challenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.igormelo.challenge.adapters.holders.ItemViewHolder;
import com.igormelo.challenge.models.PullRequests.PullResponse;
import com.igormelo.challenge.R;
import com.igormelo.challenge.utils.ImageUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Created by root on 27/01/17.
 */

public class PullRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PullResponse> items;
    private int page = 1;

    public PullRequestAdapter(Context context, List<PullResponse> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pull, parent, false);
        return new ItemViewHolder(v);
    }
    private void loadImage(ImageView imgUser, String url){
        ImageUtils.loadImage(context, url, imgUser);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final PullResponse item = items.get(position);
        DateTime dateTime = new DateTime(item.getCreatedAt());
        itemViewHolder.txtRepositoryName.setText(item.getTitle());
        itemViewHolder.txtRepositoryDesc.setText(item.getBody());
        itemViewHolder.txtDate.setText(DateTimeFormat.forPattern("HH:mm dd/MM/yyyy")
        .print(dateTime));
        itemViewHolder.linNumericInfo.setVisibility(View.GONE);
        if(item.getUser() != null) {
            if (item.getUser().getAvatarUrl() != null) {
                loadImage(itemViewHolder.imgUser, item.getUser().getAvatarUrl());
            } else {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "User vazio", Toast.LENGTH_SHORT).show();
        }
        
        itemViewHolder.txtUsername.setText(item.getUser().getLogin());
        itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetails(item.getHtmlUrl());
            }
        });
    }
    private void initDetails(String url){
        if (!TextUtils.isEmpty(url)) {
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browser);
        } else {
            Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(PullResponse pullResponse){
        items.add(pullResponse);
        notifyItemInserted(getItemCount());
    }
}
