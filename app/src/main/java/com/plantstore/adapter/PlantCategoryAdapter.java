package com.plantstore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plantstore.R;
import com.plantstore.entities.PlantCategory;

import java.util.List;

public class PlantCategoryAdapter extends RecyclerView.Adapter<PlantCategoryAdapter.CategoryViewHolder> {

    private List<PlantCategory> plantCategories;

    public PlantCategoryAdapter(List<PlantCategory> plantCategories) {
        this.plantCategories = plantCategories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the plant category
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Bind the plant category data to the item view
        PlantCategory category = plantCategories.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return plantCategories.size();
    }

    // ViewHolder class for the plant category item view
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTextView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView);
        }

        public void bind(PlantCategory category) {
            categoryNameTextView.setText(category.getName());
            // Set any other data or event handling for the item view
        }
    }
}
