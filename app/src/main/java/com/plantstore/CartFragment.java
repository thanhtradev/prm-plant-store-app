package com.plantstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.plantstore.adapter.CartItemAdapter;
import com.plantstore.entities.CartItem;
import com.plantstore.entities.User;
import com.plantstore.models.api.CartItemListResponse;
import com.plantstore.models.api.CartItemResponse;
import com.plantstore.models.api.CartResponse;
import com.plantstore.models.api.CreateOrderItemRequest;
import com.plantstore.models.api.CreateOrderRequest;
import com.plantstore.models.api.OrderResponse;
import com.plantstore.models.api.UserResponse;
import com.plantstore.network.RetrofitClient;
import com.plantstore.services.CartApiService;
import com.plantstore.services.CartItemService;
import com.plantstore.services.OrderApiService;
import com.plantstore.ultils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private CartApiService cartApiService;
    private CartItemService cartItemService;
    private OrderApiService orderApiService;
    private RecyclerView cartRecyclerView;
    private CartItemAdapter cartItemAdapter;
    private TextView totalPlantsTextView;
    private TextView totalPriceTextView;
    private Button orderButton;
    private UserResponse userResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        totalPlantsTextView = view.findViewById(R.id.totalPlantsTextView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        orderButton = view.findViewById(R.id.orderButton);


        // Init cartRecyclerView
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemAdapter = new CartItemAdapter(new ArrayList<>());
        cartRecyclerView.setAdapter(cartItemAdapter);
        // Init cartApiService
        cartApiService = RetrofitClient.getClient().create(CartApiService.class);
        cartItemService = RetrofitClient.getClient().create(CartItemService.class);
        orderApiService = RetrofitClient.getClient().create(OrderApiService.class);

        userResponse = SharedPrefUtils.getSavedUserResponse(getContext());
        getCart(userResponse.getData().getId());

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrderRequest createOrderRequest = new CreateOrderRequest();
                createOrderRequest.setUserId(userResponse.getData().getId());
                createOrderRequest.setOrderItems(new ArrayList<>());
                for (CartItem cartItem : cartItemAdapter.getCartItems()) {
                    CreateOrderItemRequest createOrderItemRequest = new CreateOrderItemRequest();
                    createOrderItemRequest.setPlantId(cartItem.getPlant().getId());
                    createOrderItemRequest.setQuantity(cartItem.getAmount());
                    createOrderRequest.getOrderItems().add(createOrderItemRequest);
                }
                createOrder(createOrderRequest);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void getCart(Long userId) {
        Call<CartResponse> call = cartApiService.getCartByUserId(userId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    // Process the cart items
                    if (cartResponse != null) {
                        String ids = "";
                        List<Long> cartItemIds = cartResponse.getData().getCartItemIds();
                        for (int i = 0; i < cartItemIds.size(); i++) {
                            ids += cartItemIds.get(i);
                            if (i < cartItemIds.size() - 1) {
                                ids += ",";
                            }
                        }

                        if (ids != null && !ids.isEmpty()) {
                            getCartItemByIds(ids);
                        } else {
                            Toast.makeText(getContext(), "No items in cart", Toast.LENGTH_SHORT).show();
                            updateTotalValues(new ArrayList<>());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCartItemByIds(String ids) {
        Call<CartItemListResponse> call = cartItemService.getCartItemsByIds(ids);
        call.enqueue(new Callback<CartItemListResponse>() {
            @Override
            public void onResponse(Call<CartItemListResponse> call, Response<CartItemListResponse> response) {
                if (response.isSuccessful()) {
                    CartItemListResponse cartItemResponse = response.body();
                    // Process the cart items
                    if (cartItemResponse != null) {
                        cartItemAdapter.setCartItems(cartItemResponse.getData());
                        cartItemAdapter.notifyDataSetChanged();
                        updateTotalValues(cartItemResponse.getData());
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CartItemListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateTotalValues(List<CartItem> cartItems) {
        int totalPlants = 0;
        double totalPrice = 0;

        for (CartItem cartItem : cartItems) {
            totalPlants += cartItem.getAmount();
            totalPrice += cartItem.getPlant().getPrice() * cartItem.getAmount();
        }


        // Update the TextViews with the total values
        totalPlantsTextView.setText("Total plants: " + totalPlants);
        totalPriceTextView.setText("Total price: " + String.format(Locale.US, "%.2f", totalPrice) + " $");
    }

    private void createOrder(CreateOrderRequest createOrderRequest) {
        Call<OrderResponse> call = orderApiService.createOrder(createOrderRequest);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    OrderResponse createOrderResponse = response.body();
                    if (createOrderResponse != null) {
                        Toast.makeText(getContext(), "Order created", Toast.LENGTH_SHORT).show();
                        deleteCartByUserId(userResponse.getData().getId());
                        // Clear the cart
                        cartItemAdapter.setCartItems(new ArrayList<>());
                        cartItemAdapter.notifyDataSetChanged();
                        updateTotalValues(new ArrayList<>());

                    }
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCartByUserId(Long userId) {
        Call<Void> call = cartApiService.deleteCartByUserId(userId);
        call.enqueue(new Callback<Void>() {
                         @Override
                         public void onResponse(Call<Void> call, Response<Void> response) {
                         }

                         @Override
                         public void onFailure(Call<Void> call, Throwable t) {
                         }
                     }
        );
    }
}