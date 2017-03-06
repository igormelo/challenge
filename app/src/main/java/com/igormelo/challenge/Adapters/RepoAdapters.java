package com.igormelo.challenge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private RecyclerView recyclerView;


    public RepoAdapters(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position){
        return items.get(position) !=null? 1 :0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder viewHolder =null;
        if(viewType == 1) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            viewHolder = new ItemViewHolder(layoutView);
        } else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            viewHolder = new ItemViewHolder(layoutView);
        }
        return viewHolder;
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
                intent.putExtra("creator", item.getOwner().getLogin());
                intent.putExtra("repository", item.getName());
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
