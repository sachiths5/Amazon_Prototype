package com.example.amazon_prototype2.fragments;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.INVISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amazon_prototype2.R;
import com.example.amazon_prototype2.activities.MainActivity;
import com.example.amazon_prototype2.activities.PaymentActivity;
import com.example.amazon_prototype2.model.CartManager;
import com.example.amazon_prototype2.model.CategoryDataModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart_Fragment extends Fragment {

    private RecyclerView cartRecyclerView;
    static Context context;
    SharedPreferences preferences;
    private CartAdapter cartAdapter;
    private ArrayList<CategoryDataModel.product_details> cartList;
    private TextView totalamount;
    Button clear_list;

    public Cart_Fragment() {}

    public static Cart_Fragment newInstance() {
        return new Cart_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cart_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        totalamount = view.findViewById(R.id.totalamount);
        clear_list = view.findViewById(R.id.clear_list);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Button orderbutton=view.findViewById(R.id.checkoutButton);
        orderbutton.setOnClickListener(v -> {
            if(cartList!=null&&!cartList.isEmpty()){
            Intent intent=new Intent(requireActivity(), PaymentActivity.class);
            startActivity(intent);}

        });

        cartList = CartManager.getCartList();

        cartAdapter = new CartAdapter(cartList);
        if (cartList == null || cartList.isEmpty()) {
            clear_list.setVisibility(INVISIBLE);
            cartAdapter.setShowRemoveButton(false);
        } else {
            cartAdapter.setShowRemoveButton(true);
        }

        cartRecyclerView.setAdapter(cartAdapter);

        clear_list.setOnClickListener(v -> {
            CartManager.clearCart();
            cartList.clear();
            cartAdapter.notifyDataSetChanged();
            calculateAndDisplayTotal();
            clear_list.setVisibility(INVISIBLE);
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).removeBadgeFromCart();
            }
        });

        calculateAndDisplayTotal();
    }

    public void calculateAndDisplayTotal() {
        double total = 0;

        if (cartList != null) {
            for (CategoryDataModel.product_details item : cartList) {
                try {
                    total += Double.parseDouble(String.valueOf(item.getPrice()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        totalamount.setText("Total: " + format.format(total));
    }

    static class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

        private final ArrayList<CategoryDataModel.product_details> cartItems;
        private boolean showRemoveButton = true;

        public CartAdapter(ArrayList<CategoryDataModel.product_details> cartItems) {
            this.cartItems = cartItems;
        }

        public void setShowRemoveButton(boolean show) {
            this.showRemoveButton = show;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_product_layout, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            CategoryDataModel.product_details item = cartItems.get(position);
            holder.productName.setText(item.getName());
            holder.productDescription.setText(item.getDesrciption());
            holder.productPrice.setText("₹" + item.getPrice());
            holder.productImage.setImageResource(item.getImage());
            Context context = holder.itemView.getContext();

            if (context instanceof MainActivity) {
                ((MainActivity) context).updateCartBadge(cartItems.size());
            }

            holder.remove.setVisibility(showRemoveButton ? View.VISIBLE : View.INVISIBLE);

            holder.remove.setOnClickListener(v -> {
                int pos = holder.getAdapterPosition();
                try {


                if (pos != RecyclerView.NO_POSITION) {
                    cartItems.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, cartItems.size());
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).updateCartBadge(cartItems.size());}

                }}
                catch (Exception ignored){};
            });
        }

        @Override
        public int getItemCount() {
            return cartItems != null ? cartItems.size() : 0;
        }

        static class CartViewHolder extends RecyclerView.ViewHolder {
            TextView productName, productDescription, productPrice;
            ImageView productImage;
            Button remove;

            public CartViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.product_name);
                productDescription = itemView.findViewById(R.id.product_description);
                productPrice = itemView.findViewById(R.id.product_price);
                productImage = itemView.findViewById(R.id.product_image);
                remove = itemView.findViewById(R.id.removeproduct);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (cartList != null) {
            preferences = requireActivity().getSharedPreferences("list", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.apply();
        }
    }
}
