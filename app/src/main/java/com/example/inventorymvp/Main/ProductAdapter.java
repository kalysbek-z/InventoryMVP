package com.example.inventorymvp.Main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inventorymvp.data.Product;
import com.example.inventorymvp.data.ProductDatabase;
import com.example.inventorymvp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> dataList = new ArrayList<>();
    private Activity context;
    private ProductDatabase database;

    private OnItemClickListener listener;

    public ProductAdapter(Activity context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        if (dataList != null) {
            Product product = dataList.get(position);

            holder.Name.setText(product.getName());
            holder.Supplier.setText(product.getSupplier());
            holder.Quantity.setText(product.getQuantity());
            holder.Price.setText(product.getPrice());

            if (product.getImageUri() != null) {
                Glide.with(context).load(product.getImageUri()).into(holder.Photo);
            } else {
                Glide.with(context).load(R.drawable.no_photo).into(holder.Photo);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<Product> products) {
        if (products != null) {
            this.dataList.clear();
            this.dataList.addAll(products);
        }
        notifyDataSetChanged();
    }

    public Product getProductAt(int pos) {
        return dataList.get(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Photo;
        TextView Name;
        TextView Supplier;
        TextView Quantity;
        TextView Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Photo = itemView.findViewById(R.id.photo);
            Name = itemView.findViewById(R.id.name);
            Supplier = itemView.findViewById(R.id.supplier);
            Quantity = itemView.findViewById(R.id.quantity);
            Price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getAdapterPosition(), dataList.get(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
