package com.plantstore;

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
import android.widget.Toast;

import com.plantstore.adapter.OrderAdapter;
import com.plantstore.entities.Order;
import com.plantstore.models.api.GetListOrderResponse;
import com.plantstore.models.api.UserResponse;
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.OrderApiService;
import com.plantstore.ultils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment implements OrderAdapter.OnOrderClickListener {


    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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

    UserResponse userResponse ;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private OrderApiService orderApiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        orderApiService = RetrofitClient.getClient().create(OrderApiService.class);
        userResponse = SharedPrefUtils.getSavedUserResponse(getContext());

        recyclerView = view.findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        orderAdapter.setOnOrderClickListener(this);
        recyclerView.setAdapter(orderAdapter);

        loadOrders();
        return view;
    }

    private void loadOrders() {
        // Make API call or fetch orders from local storage
        // Example: Assume you have a method to retrieve orders for a specific user
        Call<GetListOrderResponse> call = orderApiService.getOrdersByUserId(userResponse.getData().getId());
        call.enqueue(new retrofit2.Callback<GetListOrderResponse>() {
            @Override
            public void onResponse(Call<GetListOrderResponse> call, retrofit2.Response<GetListOrderResponse> response) {
                if (response.isSuccessful()) {
                    orderList.addAll(response.body().getData());
                    orderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetListOrderResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOrderClick(Order order) {
        // Navigate to order detail screen
        // Example: Assume you have a method to navigate to order detail screen
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentLayout,new OrderDetailFragment(order));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}