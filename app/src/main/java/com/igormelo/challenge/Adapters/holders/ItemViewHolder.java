package com.igormelo.challenge.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.igormelo.challenge.PullRequestActivity;
import com.igormelo.challenge.R;
import com.igormelo.challenge.models.Item;
import com.igormelo.challenge.utils.ImageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 26/01/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @Bind(R.id.card_view)
    public CardView cardView;
    @Bind(R.id.img_user)
    public AppCompatImageView imgUser;
    @Bind(R.id.txt_username)
    public AppCompatTextView txtUsername;
    @Bind(R.id.txt_full_name)
    public AppCompatTextView txtFullName;
    @Bind(R.id.txt_repository_name)
    public AppCompatTextView txtRepositoryName;
    @Bind(R.id.txt_repository_desc)
    public AppCompatTextView txtRepositoryDesc;
    @Bind(R.id.txt_repository_forks)
    public AppCompatTextView txtRepositoryForks;
    @Bind(R.id.txt_repository_stars)
    public AppCompatTextView txtRepositoryStars;
    @Bind(R.id.txt_date)
    public AppCompatTextView txtDate;
    @Bind(R.id.lin_numeric_info)
    public LinearLayout linNumericInfo;


    public ItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(null, "oi", Toast.LENGTH_SHORT).show();
    }

    public void bindView(final Item item, int position, final Context context) {
        this.txtRepositoryName.setText(item.getName());
        this.txtRepositoryDesc.setText(item.getDescription());
        this.txtRepositoryForks.setText(String.valueOf(item.getForks()));
        this.txtRepositoryStars.setText(String.valueOf(item.getStargazersCount()));
        this.txtUsername.setText(item.getOwner().getLogin());
        ImageUtils.loadImage(context, item.getOwner().getAvatarUrl(), this.imgUser);
        this.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PullRequestActivity.class);
                intent.putExtra("creator", item.getOwner().getLogin());
                intent.putExtra("repository", item.getName());
                context.startActivity(intent);
            }
        });
    }
}
