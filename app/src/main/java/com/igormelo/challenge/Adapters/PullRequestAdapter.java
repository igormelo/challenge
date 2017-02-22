package com.igormelo.challenge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.igormelo.challenge.Adapters.holders.ItemViewHolder;
import com.igormelo.challenge.Adapters.holders.PHolder;
import com.igormelo.challenge.Models.PullRequests.Response;
import com.igormelo.challenge.R;
import com.igormelo.challenge.Utils.ImageUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Created by root on 27/01/17.
 */

public class PullRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Response> items;

    public PullRequestAdapter(Context context, List<Response> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pull, parent, false);
        return new PHolder(v);
    }
    private void loadImage(ImageView imgUser, String url){
        ImageUtils.loadImage(context, url, imgUser);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Response item = items.get(position);
        DateTime dateTime = new DateTime(item.getCreatedAt());
        itemViewHolder.txtRepositoryName.setText(item.getTitle());
        itemViewHolder.txtRepositoryDesc.setText(item.getBody());
        itemViewHolder.txtDate.setText(DateTimeFormat.forPattern("HH:mm dd/MM/yyyy")
        .print(dateTime));
        itemViewHolder.linNumericInfo.setVisibility(View.GONE);
        loadImage(itemViewHolder.imgUser, item.getUser().getAvatarUrl());
        itemViewHolder.txtUsername.setText(item.getUser().getLogin());
        itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetails(item.getHtmlUrl());
            }
        });
    }
    private void initDetails(String url){
        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browser);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(Response response){
        items.add(response);
        notifyItemInserted(getItemCount());
    }
}
