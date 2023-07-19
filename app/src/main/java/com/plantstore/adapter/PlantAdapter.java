package com.plantstore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.plantstore.R;
import com.plantstore.entities.CartItem;
import com.plantstore.entities.Plant;
import com.plantstore.models.api.AddToCartRequest;
import com.plantstore.models.api.CartItemResponse;
import com.plantstore.models.api.UserResponse;
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.CartItemService;
import com.plantstore.ultils.SharedPrefUtils;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Plant> plants;
    private OnPlantClickListener onPlantClickListener;


    public void setPlants(List<Plant> plants) {
        this.plants = plants;
        notifyDataSetChanged();
    }
    public void setOnPlantClickListener(OnPlantClickListener listener) {
        this.onPlantClickListener = listener;
    }
    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plants.get(position);
        holder.bind(plant);
    }

    @Override
    public int getItemCount() {
        return plants != null ? plants.size() : 0;
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private TextView priceTextView;
        private ImageView plantImageView;
        private ImageButton addToCartButton;
        private CartItemService cartItemService;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            plantImageView = itemView.findViewById(R.id.plantImageView);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            itemView.setOnClickListener(this);
            cartItemService = RetrofitClient.getClient().create(CartItemService.class);
        }

        public void bind(Plant plant) {
            nameTextView.setText(plant.getName());
            priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", plant.getPrice()));
            if (plant.getImage() != null) {
                Glide.with(itemView.getContext()).load(plant.getImage()).into(plantImageView);
                // Set any other data or event handling for the item view
            }
            addToCartButton.setOnClickListener(v -> {
                // Make a network request to add the plant to the cart
                UserResponse userResponse = SharedPrefUtils.getSavedUserResponse(itemView.getContext());
                if (userResponse != null) {
                    AddToCartRequest addToCartRequest = new AddToCartRequest();
                    addToCartRequest.setPlantId(plant.getId());
                    addToCartRequest.setUserId(userResponse.getData().getId());
                    addToCartRequest.setAmount(1);
                    addToCart(addToCartRequest);
                }
            });
        }

        private void addToCart(AddToCartRequest addToCartRequest) {
            Call<CartItemResponse> call = cartItemService.addToCart(addToCartRequest);
            call.enqueue(new Callback<CartItemResponse>() {
                @Override
                public void onResponse(Call<CartItemResponse> call, Response<CartItemResponse> response) {
                    if (response.isSuccessful()) {
                        CartItemResponse cartItemResponse = response.body();
                        if (cartItemResponse != null && cartItemResponse.isResult()) {
                            Toast.makeText(itemView.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(itemView.getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(itemView.getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CartItemResponse> call, Throwable t) {
                    // Handle network or API call failure
                    Toast.makeText(itemView.getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Plant clickedPlant = plants.get(position);
                if (v == itemView && onPlantClickListener != null) {
                    onPlantClickListener.onPlantClick(clickedPlant);
                }
            }
        }
    }

    public interface OnPlantClickListener {
        void onPlantClick(Plant plant);
    }
}

