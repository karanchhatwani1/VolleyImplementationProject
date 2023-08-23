package com.example.volleyassignment.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyassignment.Activities.DetailsPage;
import com.example.volleyassignment.Models.Product;
import com.example.volleyassignment.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText("$" + product.getPrice());
        holder.ratingBar.setRating((float) product.getRating());
        holder.discountPercentage.setText("Discount: " + product.getDiscountPercentage() + "%OFF");
        // Load thumbnail image using Picasso library
        Picasso.get().load(product.getThumbnail()).into(holder.thumbnailImageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsPage.class);
                intent.putExtra("title", product.getTitle());
                intent.putExtra("description", product.getDescription());
                intent.putExtra("price", product.getPrice());
                intent.putStringArrayListExtra("images", product.getImages());
                intent.putExtra("discount", product.getDiscountPercentage());
                intent.putExtra("stock", product.getStock());
                intent.putExtra("brand", product.getBrand());
                intent.putExtra("category", product.getCategory());
                intent.putExtra("rating", product.getRating());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView priceTextView;
        ImageView thumbnailImageView;
        LinearLayout layout;
        RatingBar ratingBar;
        TextView discountPercentage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            layout = itemView.findViewById(R.id.product_details);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            discountPercentage = itemView.findViewById(R.id.discountPercentage);
        }
    }
}

