package com.example.volleyassignment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volleyassignment.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsPage extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView discountVal;
    private TextView stockVal;
    private TextView brandName;
    private TextView categoryName;
    private RatingBar ratingVal;
    private ViewPager imageViewPager;
    private TabLayout imageIndicator;
    private ProductImageAdapter imageAdapter;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        imageViewPager = findViewById(R.id.imageViewPager);
        imageIndicator = findViewById(R.id.imageIndicator);
        titleTextView = findViewById(R.id.detailTitle);
        descriptionTextView = findViewById(R.id.detailDescription);
        priceTextView = findViewById(R.id.detailPrice);
        discountVal = findViewById(R.id.discountPercentage);
        stockVal = findViewById(R.id.stock);
        brandName = findViewById(R.id.brand);
        categoryName = findViewById(R.id.category);
        ratingVal = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            double price = intent.getDoubleExtra("price", 0.0);
            images = intent.getStringArrayListExtra("images");
            double discount = intent.getDoubleExtra("discount", 0.0);
            double rating = intent.getDoubleExtra("rating", 0.0);
            int stock = intent.getIntExtra("stock", 0);
            String brand = intent.getStringExtra("brand");
            String category = intent.getStringExtra("category");

            titleTextView.setText(title);
            descriptionTextView.setText(description);
            ratingVal.setRating((float)rating);
            priceTextView.setText(String.format("$%.2f", price));
            discountVal.setText(String.format("$%.2f", discount));
            stockVal.setText(String.valueOf(stock));
            brandName.setText(brand);
            categoryName.setText(category);
            imageAdapter = new ProductImageAdapter(images);
            imageViewPager.setAdapter(imageAdapter);
            imageIndicator.setupWithViewPager(imageViewPager);

        }
    }

    private class ProductImageAdapter extends PagerAdapter {
        private List<String> imageUrls;

        public ProductImageAdapter(ArrayList<String> imageUrls) {
            if (imageUrls != null) {
                this.imageUrls = imageUrls;
            } else {
                this.imageUrls = new ArrayList<>();
                Toast.makeText(DetailsPage.this, "No images", Toast.LENGTH_SHORT).show();
            }
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(DetailsPage.this);
            Picasso.get().load(imageUrls.get(position)).fit().into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}

