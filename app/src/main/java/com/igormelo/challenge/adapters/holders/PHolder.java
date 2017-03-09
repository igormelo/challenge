package com.igormelo.challenge.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.igormelo.challenge.R;

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

    public void bindView(int totalPage, int currentpage) {
        progressBar.setVisibility(currentpage < totalPage ? View.VISIBLE : View.GONE);
    }
}
