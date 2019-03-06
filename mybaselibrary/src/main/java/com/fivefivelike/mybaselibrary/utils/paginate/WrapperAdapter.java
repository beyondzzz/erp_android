package com.fivefivelike.mybaselibrary.utils.paginate;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;



class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_LOADING = Integer.MAX_VALUE - 50; // Magic
    private int pagesize;
    private final RecyclerView.Adapter wrappedAdapter;
    private final LoadingListItemCreator loadingListItemCreator;
    private boolean displayLoadingRow = true;

    public WrapperAdapter(RecyclerView.Adapter adapter, LoadingListItemCreator creator) {
        this.wrappedAdapter = adapter;
        this.loadingListItemCreator = creator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_LOADING) {
            return loadingListItemCreator.onCreateViewHolder(parent, viewType);
        } else {
            return wrappedAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadingRow(position)) {
            loadingListItemCreator.onBindViewHolder(holder, position);
        } else {
            wrappedAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return displayLoadingRow&&wrappedAdapter.getItemCount()>= pagesize ? wrappedAdapter.getItemCount() + 1 : wrappedAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadingRow(position)&&wrappedAdapter.getItemCount()>= pagesize  ? ITEM_VIEW_TYPE_LOADING : wrappedAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return isLoadingRow(position)&&wrappedAdapter.getItemCount()>= pagesize  ? RecyclerView.NO_ID : wrappedAdapter.getItemId(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        wrappedAdapter.setHasStableIds(hasStableIds);
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return wrappedAdapter;
    }

    boolean isDisplayLoadingRow() {
        return displayLoadingRow;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagesize() {
        return pagesize;
    }

    void displayLoadingRow(boolean displayLoadingRow) {
        if (this.displayLoadingRow != displayLoadingRow) {
            this.displayLoadingRow = displayLoadingRow;
            notifyDataSetChanged();
        }
    }

    boolean isLoadingRow(int position) {
        return displayLoadingRow&&wrappedAdapter.getItemCount()>= pagesize && position == getLoadingRowPosition();
    }

    private int getLoadingRowPosition() {
        return displayLoadingRow &&wrappedAdapter.getItemCount()>= pagesize ? getItemCount() - 1 : -1;
    }
}