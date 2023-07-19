package com.plantstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plantstore.entities.Order;
import com.plantstore.entities.OrderItem;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDetailFragment extends Fragment {

    private TextView orderIdTextView;
    private TextView orderDateTextView;
    private TextView orderTotalTextView;
    private TextView orderStatusTextView;
    private LinearLayout orderItemsLayout;

    private Order order;

    public OrderDetailFragment(Order order) {
        this.order = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);

        orderIdTextView = view.findViewById(R.id.orderIdTextView);
        orderDateTextView = view.findViewById(R.id.orderDateTextView);
        orderTotalTextView = view.findViewById(R.id.orderTotalTextView);
        orderStatusTextView = view.findViewById(R.id.orderStatusTextView);
        orderItemsLayout = view.findViewById(R.id.orderItemsLayout);

        orderIdTextView.setText("Order ID: " + order.getId());
        orderDateTextView.setText("Order Date: " + formatDate(order.getDate()));
        orderTotalTextView.setText("Total: " + formatPrice(order.getTotal()));
        orderStatusTextView.setText("Status: " + order.getStatus());

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            addOrderItemView(item);
        }

        return view;
    }

    private void addOrderItemView(OrderItem item) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.order_item_view, null);
        TextView itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
        TextView itemQuantityTextView = itemView.findViewById(R.id.itemQuantityTextView);
        ImageView itemImageView = itemView.findViewById(R.id.itemImageView);
        itemNameTextView.setText(item.getPlant().getName());
        itemQuantityTextView.setText("Quantity: " + item.getQuantity());
        Glide.with(getActivity()).load(item.getPlant().getImage()).into(itemImageView);

        orderItemsLayout.addView(itemView);
    }

    private String formatDate(Date date) {
        // Format and return the date as desired
        // Example implementation:
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return formatter.format(date);
    }

    private String formatPrice(double price) {
        // Format and return the price as desired
        // Example implementation:
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(price);
    }
}