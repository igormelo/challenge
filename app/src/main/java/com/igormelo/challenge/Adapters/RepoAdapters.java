package com.igormelo.challenge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.igormelo.challenge.Adapters.holders.ItemViewHolder;
import com.igormelo.challenge.Models.Item;
import com.igormelo.challenge.PullRequest;
import com.igormelo.challenge.R;
import com.igormelo.challenge.Utils.ImageUtils;

import java.util.List;

/**
 * Created by root on 26/01/17.
 */

public class RepoAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Item> items;
    private static final String PULL_CREATOR = "creator";
    private static final String PULL_REPO = "repository";


    public RepoAdapters(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final Item item = items.get(position);
            itemViewHolder.txtRepositoryName.setText(item.getName());
            itemViewHolder.txtRepositoryDesc.setText(item.getDescription());
            itemViewHolder.txtRepositoryForks.setText(String.valueOf(item.getForks()));
            itemViewHolder.txtRepositoryStars.setText(String.valueOf(item.getStargazersCount()));
            itemViewHolder.txtUsername.setText(item.getOwner().getLogin());
            loadImage(itemViewHolder.imgUser, item.getOwner().getAvatarUrl());

        itemViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PullRequest.class);
                intent.putExtra("creator", item.getName());
                intent.putExtra("repository", item.getOwner().getLogin());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private void loadImage(AppCompatImageView imgUser, String avatarUrl) {
        ImageUtils.loadImage(context, avatarUrl, imgUser);
    }

}
