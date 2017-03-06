package com.igormelo.challenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igormelo.challenge.adapters.holders.ItemViewHolder;
import com.igormelo.challenge.adapters.holders.PHolder;
import com.igormelo.challenge.models.Item;
import com.igormelo.challenge.R;

import java.util.List;

/**
 * Created by root on 26/01/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Item> items;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private RecyclerView recyclerView;
    private int totalPages = 1;
    private int currentPage = 1;

    public RepoAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position){
        //return items.get(position) !=null? 1 :0;
        if(position == items.size()) return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder =null;
        if(viewType == VIEW_TYPES.Normal) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
            viewHolder = new ItemViewHolder(layoutView);
        } else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_footer, parent, false);
            viewHolder = new PHolder(layoutView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            try {
                if (holder instanceof ItemViewHolder) {
                    ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                    final Item item = items.get(position);
                    itemViewHolder.bindView(item, position, context);
                } else if (holder instanceof PHolder) {
                    PHolder pHolder = (PHolder) holder;
                    pHolder.bindView(totalPages, currentPage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int getItemCount() {
        if(items.size() > 0) return items.size() +1;
        else return items.size();
    }

    private class VIEW_TYPES {
        public static final int Header = 1;
        public static final int Normal = 2;
        public static final int Footer = 3;
    }
}
