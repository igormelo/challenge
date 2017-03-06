package com.igormelo.challenge.Adapters.holders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.igormelo.challenge.R;

import butterknife.Bind;

/**
 * Created by root on 27/01/17.
 */

public class PHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ProgressBar progressBar;
    public PHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {

    }
}
