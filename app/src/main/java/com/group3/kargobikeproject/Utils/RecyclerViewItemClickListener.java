package com.group3.kargobikeproject.Utils;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onItemClick(View v, int position);
    void onItemLongClick(View v, int position);
}