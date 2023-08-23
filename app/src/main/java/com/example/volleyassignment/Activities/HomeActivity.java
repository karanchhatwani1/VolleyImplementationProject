package com.example.volleyassignment.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.volleyassignment.Models.Product;
import com.example.volleyassignment.Adapters.ProductAdapter;
import com.example.volleyassignment.R;
import com.example.volleyassignment.SingletonClasses.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        fetchDataFromAPI();
    }

    private void fetchDataFromAPI() {
        String url = "https://dummyjson.com/products";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray productsArray = response.getJSONArray("products");
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObject = productsArray.getJSONObject(i);
                                Product product = new Product();
                                // Parse and set fields in 'product' object
                                product.setId(productObject.getInt("id"));
                                product.setTitle(productObject.getString("title"));
                                product.setDescription(productObject.getString("description"));
                                product.setPrice(productObject.getDouble("price"));
                                product.setDiscountPercentage(productObject.getDouble("discountPercentage"));
                                product.setRating(productObject.getDouble("rating"));
                                product.setStock(productObject.getInt("stock"));
                                product.setBrand(productObject.getString("brand"));
                                product.setCategory(productObject.getString("category"));
                                product.setThumbnail(productObject.getString("thumbnail"));
                                JSONArray imagesArray = productObject.getJSONArray("images");
                                ArrayList<String> images = new ArrayList<>();
                                for (int j = 0; j < imagesArray.length(); j++) {
                                    images.add(imagesArray.getString(j));
                                }
                                product.setImages(images);
                                // Add 'product' object to the list
                                productList.add(product);
                            }
                            productAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        // Add the request to the Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
