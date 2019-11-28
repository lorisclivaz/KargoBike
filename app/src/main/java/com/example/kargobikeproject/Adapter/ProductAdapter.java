package com.example.kargobikeproject.Adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.R;
import com.example.kargobikeproject.Utils.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> data;
    private RecyclerViewItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_productName;
        TextView tv_productDescription;
        TextView tv_productPrice;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.tv_productName = itemView.findViewById(R.id.tv_productName);
            this.tv_productDescription= itemView.findViewById(R.id.tv_productDescription);
            this.tv_productPrice= itemView.findViewById(R.id.tv_productPrice);


        }
    }

    public ProductAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(view1 -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        view.setOnLongClickListener(view1 -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        Product item = data.get(position);
        holder.tv_productName.setText(item.getName());
        holder.tv_productDescription.setText(item.getDescription());
        holder.tv_productPrice.setText(item.getPrice()+" CHF");
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<Product> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProductAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (ProductAdapter.this.data instanceof Product) {
                        return (ProductAdapter.this.data.get(oldItemPosition)).getName().equals(
                                (data.get(newItemPosition)).getName());
                    }
                    return false;
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (ProductAdapter.this.data instanceof Product) {
                        Product newProduct = data.get(newItemPosition);
                        Product oldProduct = ProductAdapter.this.data.get(newItemPosition);
                        return Objects.equals(newProduct.getName(), oldProduct.getName())
                                && Objects.equals(newProduct.getDescription(), oldProduct.getDescription())
                                && Objects.equals(newProduct.getPrice(), oldProduct.getPrice());
                    }
                    return false;
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
