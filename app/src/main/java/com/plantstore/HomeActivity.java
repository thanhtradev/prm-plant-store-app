package com.plantstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.plantstore.adapter.PlantAdapter;
import com.plantstore.adapter.PlantCategoryAdapter;
import com.plantstore.entities.Plant;
import com.plantstore.entities.PlantCategory;
import com.plantstore.models.api.CartResponse;
import com.plantstore.models.api.GetAllPlantResponse;
import com.plantstore.models.api.PlantCategoryResponse;
import com.plantstore.models.api.UserResponse;
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.CartApiService;
import com.plantstore.services.PlantApiService;
import com.plantstore.services.PlantCategoryApiService;
import com.plantstore.ultils.SharedPrefUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private CartApiService cartService;
    private UserResponse userResponse;
    private boolean hasCheckedCartEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cartService = RetrofitClient.getClient().create(CartApiService.class);
        userResponse = SharedPrefUtils.getSavedUserResponse(this);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        // Set the initial selected item
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        isCartEmpty();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_home) {
            // Handle Home tab selection
            // Replace the content fragment with the home fragment
            // For example: getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new HomeFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new HomeFragment()).commit();
            return true;
        } else if (itemId == R.id.action_cart) {
            // Handle Cart tab selection
            // Replace the content fragment with the cart fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new CartFragment()).commit();
            return true;
        } else if (itemId == R.id.action_profile) {
            // Handle Profile tab selection
            // Replace the content fragment with the profile fragment
            // For example: getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new ProfileFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new ProfileFragment()).commit();
            return true;
        } else if (itemId == R.id.action_order) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new OrderFragment()).commit();
            return true;
        }

        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void isCartEmpty() {
        Call<CartResponse> call = cartService.getCartByUserId(userResponse.getData().getId());
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    // Process the cart items
                    if (cartResponse != null) {
                        if (!cartResponse.getData().getCartItemIds().isEmpty()) {
                            if (!hasCheckedCartEmpty) {
                                hasCheckedCartEmpty = true;
                                showCartNotEmptyDialog();
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {

            }
        });
    }

    private void showCartNotEmptyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cart Notification")
                .setMessage("Your cart is not empty. Please check your items.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the OK button click if needed
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}