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
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.CartItemService;

import java.util.List;

import retrofit2.Call;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private List<CartItem> cartItems;

    public CartItemAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.itemNameTextView.setText(cartItem.getPlant().getName());
        holder.itemAmountTextView.setText(String.valueOf(cartItem.getAmount()));
        Glide.with(holder.itemView.getContext())
                .load(cartItem.getPlant().getImage())
                .into(holder.itemImageView);

        holder.removeItemButton.setOnClickListener(v -> {
            deleteCartItem(cartItem);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemAmountTextView;
        ImageButton removeItemButton;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemAmountTextView = itemView.findViewById(R.id.itemAmountTextView);
            removeItemButton = itemView.findViewById(R.id.removeItemButton);
        }
    }
    private void deleteCartItem(CartItem cartItem){
        CartItemService cartItemService = RetrofitClient.getClient().create(CartItemService.class);
        Call<Void> call = cartItemService.deleteCartItem(cartItem.getId());
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if(response.isSuccessful()){
                    cartItems.remove(cartItem);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}
