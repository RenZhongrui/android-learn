package com.lib.ui.recyclerview.base;

/**
 *
 */
public interface ItemViewDelegate<T> {

  int getItemViewLayoutId();

  boolean isForViewType(T item, int position);

  void convert(ViewHolder holder, T t, int position);
}
