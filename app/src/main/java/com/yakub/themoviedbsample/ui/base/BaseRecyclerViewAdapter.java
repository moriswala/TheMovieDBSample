package com.yakub.themoviedbsample.ui.base;

import android.support.v7.widget.RecyclerView;
import io.reactivex.annotations.NonNull;

/**
 * A general RecyclerViewAdapter which supports OnItemClickListener and OnItemLongClickListener.
 *
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  protected OnLoadMoreListener onLoadMoreListener;

  public interface OnLoadMoreListener {
    void onLoadMore();
  }

  private RecyclerViewListener.OnItemClickListener itemClickListener;
  private RecyclerViewListener.OnItemLongClickListener itemLongClickListener;

  public void setOnItemClickListener(
      @NonNull RecyclerViewListener.OnItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
    this.onLoadMoreListener = onLoadMoreListener;
  }
  public void setOnItemLongClickListener(
      @NonNull RecyclerViewListener.OnItemLongClickListener itemLongClickListener) {
    this.itemLongClickListener = itemLongClickListener;
  }

  /**
   * Override onBindViewHolder to support OnItemClick and OnItemLongClick listener.
   */
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
      if (itemClickListener != null) {
        viewHolder.itemView.setOnClickListener(view -> itemClickListener.OnItemClick(view, i));
      }
      if (itemLongClickListener != null) {
        viewHolder.itemView.setOnLongClickListener(view -> {
          itemLongClickListener.OnItemLongClick(view, i);
          return true;
        });
      }
  }
}
