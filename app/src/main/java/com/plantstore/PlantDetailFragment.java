package com.plantstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDetailFragment extends Fragment {

    private ImageView plantImageView;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;

    private String plantName;
    private String plantDescription;
    private double plantPrice;
    private String plantImage;

    public PlantDetailFragment() {
        // Required empty public constructor
    }

    public static PlantDetailFragment newInstance(String name, String description, double price, String image) {
        PlantDetailFragment fragment = new PlantDetailFragment();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("description", description);
        args.putDouble("price", price);
        args.putString("image", image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            plantName = getArguments().getString("name");
            plantDescription = getArguments().getString("description");
            plantPrice = getArguments().getDouble("price");
            plantImage = getArguments().getString("image");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plant_detail, container, false);

        // Retrieve plant data from fragment arguments
        if (getArguments() != null) {
            plantName = getArguments().getString("name");
            plantDescription = getArguments().getString("description");
            plantPrice = getArguments().getDouble("price");
            plantImage = getArguments().getString("image");
        }

        // Initialize views
        plantImageView = view.findViewById(R.id.plantImageView);
        nameTextView = view.findViewById(R.id.nameTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        priceTextView = view.findViewById(R.id.priceTextView);

        // Set plant data to the views
        nameTextView.setText(plantName);
        descriptionTextView.setText(plantDescription);
        priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", plantPrice));

        Glide.with(this).load(plantImage).into(plantImageView);

        return view;}
}