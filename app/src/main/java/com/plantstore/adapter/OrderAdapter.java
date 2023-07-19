package com.plantstore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plantstore.R;
import com.plantstore.entities.Order;
import com.plantstore.entities.Plant;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private OnOrderClickListener onOrderClickListener;

    public void setOnOrderClickListener(OnOrderClickListener listener) {
        this.onOrderClickListener = listener;
    }
    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private TextView orderIdTextView;
        private TextView orderDateTextView;
        private TextView orderTotalTextView;
        private TextView orderStatusTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderTotalTextView);
            orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
            itemView.setOnClickListener(this);
        }

        public void bind(Order order) {
            orderIdTextView.setText("Order ID: " + order.getId());
            orderDateTextView.setText("Order Date: " + formatDate(order.getDate()));
            orderTotalTextView.setText("Order Total: " + formatPrice(order.getTotal()));
            orderStatusTextView.setText("Order Status: " + order.getStatus());
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

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Order clickedPlant = orderList.get(position);
                if (v == itemView && onOrderClickListener != null) {
                    onOrderClickListener.onOrderClick(clickedPlant);
                }
            }
        }
    }
    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

}
