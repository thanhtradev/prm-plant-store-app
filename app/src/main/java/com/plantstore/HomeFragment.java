package com.plantstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements PlantAdapter.OnPlantClickListener{
    private PlantCategoryApiService plantCategoryApiService;
    private PlantApiService plantApiService;
    private TextView greetingTextView;
    private UserResponse userResponse;
    private ImageButton cartButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        greetingTextView = view.findViewById(R.id.greetingTextView);
        plantCategoryApiService = RetrofitClient.getClient().create(PlantCategoryApiService.class);
        plantApiService = RetrofitClient.getClient().create(PlantApiService.class);
        cartButton = view.findViewById(R.id.cartIcon);
        cartButton.setOnClickListener(v -> {
            // Navigate to cart fragment
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentLayout, new CartFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        userResponse = SharedPrefUtils.getSavedUserResponse(requireContext());
        greetingTextView.setText("Hello, " + userResponse.getData().getUsername());

        // Fetch plant categories
        fetchPlantCategories();
        fetchPlants();

        // Inflate the layout for this fragment
        return view;
    }

    private void fetchPlantCategories() {
        Call<PlantCategoryResponse> call = plantCategoryApiService.getPlantCategories();
        call.enqueue(new Callback<PlantCategoryResponse>() {
            @Override
            public void onResponse(Call<PlantCategoryResponse> call, Response<PlantCategoryResponse> response) {
                if (response.isSuccessful()) {
                    PlantCategoryResponse plantCategoryResponse = response.body();
                    List<PlantCategory> plantCategories = plantCategoryResponse.getData();
                    // Process the retrieved plant categories
                    if (plantCategories != null) {
                        // Update the UI with the plant categories
                        updatePlantCategories(plantCategories);
                    }
                } else {

                    Toast.makeText(requireContext(), "Error when getting plant category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlantCategoryResponse> call, Throwable t) {
                // Handle network or API call failure
                // ...
                Toast.makeText(requireContext(), "Error when getting plant category", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePlantCategories(List<PlantCategory> plantCategories) {
        // Update the UI with the plant categories
        RecyclerView categoryRecyclerView = getView().findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);

        PlantCategoryAdapter adapter = new PlantCategoryAdapter(plantCategories);
        categoryRecyclerView.setAdapter(adapter);
    }

    private void fetchPlants() {
        Call<GetAllPlantResponse> call = plantApiService.getAllPlants();
        call.enqueue(new Callback<GetAllPlantResponse>() {
            @Override
            public void onResponse(Call<GetAllPlantResponse> call, Response<GetAllPlantResponse> response) {
                if (response.isSuccessful()) {
                    GetAllPlantResponse plantResponse = response.body();
                    List<Plant> plants = plantResponse.getData();
                    // Process the retrieved plant categories
                    if (plants != null) {
                        // Update the UI with the plant categories
                        updatePlants(plants);
                    }
                } else {

                    Toast.makeText(requireContext(), "Error when getting plant category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllPlantResponse> call, Throwable t) {
                // Handle network or API call failure
                // ...
                Toast.makeText(requireContext(), "Error when getting plant category", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePlants(List<Plant> plants) {
        // Update the UI with the plant categories
        RecyclerView plantRecyclerView = getView().findViewById(R.id.plantRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        plantRecyclerView.setLayoutManager(layoutManager);

        PlantAdapter adapter = new PlantAdapter();
        adapter.setPlants(plants);
        adapter.setOnPlantClickListener(this);
        plantRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onPlantClick(Plant plant) {
        // Navigate to plant detail fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentLayout, new PlantDetailFragment().newInstance(plant.getName(), plant.getDescription(), plant.getPrice(), plant.getImage()));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}