package com.example.amazon_prototype2.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amazon_prototype2.R;
import com.example.amazon_prototype2.activities.MainActivity;
import com.example.amazon_prototype2.model.CartManager;
import com.example.amazon_prototype2.model.CategoryDataModel;

import java.util.ArrayList;
import java.util.List;

public class Product_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView product_recyclerview;
    ArrayList<CategoryDataModel.product_details> product_detail;

    public Product_Fragment() {}

    public static Product_Fragment newInstance(String param1, String param2) {
        Product_Fragment fragment = new Product_Fragment();
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

        product_detail = getArguments().getParcelableArrayList("productlist");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_, container, false);

        product_recyclerview = view.findViewById(R.id.product_recycler);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        product_adapter adapter = new product_adapter(product_detail, getContext());
        product_recyclerview.setAdapter(adapter);

        return view;
    }

    static class product_adapter extends RecyclerView.Adapter<product_adapter.viewholder> {

        private final ArrayList<CategoryDataModel.product_details> productList;
        Context context;

        public product_adapter(List<CategoryDataModel.product_details> productList, Context context) {
            this.context = context;
            this.productList = (ArrayList<CategoryDataModel.product_details>) productList;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.product_layout_fragment, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            CategoryDataModel.product_details prod = productList.get(position);

            holder.productimage.setImageResource(prod.getImage());
            holder.productname.setText(prod.getName());
            holder.productdescription.setText(prod.getDesrciption());
            holder.productprice.setText("₹" + prod.getPrice());

            holder.quantity.setText(String.valueOf(prod.getQuantity()));

            holder.addbutton.setOnClickListener(v -> {
                int currentQuantity = prod.getQuantity();
                prod.setQuantity(currentQuantity + 1);

                CartManager.addToCart(prod);

                holder.quantity.setText(String.valueOf(prod.getQuantity()));

                Toast.makeText(context, "Added 1 more", Toast.LENGTH_SHORT).show();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).updateCartBadge(CartManager.getCartList().size());
                }
            });

            holder.minusbutton.setOnClickListener(v -> {
                int currentQuantity = prod.getQuantity();
                if (currentQuantity > 0) {
                    prod.setQuantity(currentQuantity - 1);
                    holder.quantity.setText(String.valueOf(prod.getQuantity()));

                    CartManager.removecart(prod); // remove from cart
                    Toast.makeText(context, "Removed 1 item", Toast.LENGTH_SHORT).show();

                    if (context instanceof MainActivity) {
                        ((MainActivity) context).updateCartBadge(CartManager.getCartList().size());
                    }
                }
            });

            holder.add_product.setOnClickListener(v -> {
                if (prod.getQuantity() == 0) {
                    prod.setQuantity(1);
                    holder.quantity.setText("1");

                    CartManager.addToCart(prod);

                    Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).updateCartBadge(CartManager.getCartList().size());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        static class viewholder extends RecyclerView.ViewHolder {
            ImageView productimage;
            TextView productname, productdescription, productprice, quantity;
            Button add_product, addbutton, minusbutton;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                productdescription = itemView.findViewById(R.id.product_description);
                productname = itemView.findViewById(R.id.product_name);
                productprice = itemView.findViewById(R.id.product_price);
                productimage = itemView.findViewById(R.id.product_image);
                add_product = itemView.findViewById(R.id.addtocart);
                addbutton = itemView.findViewById(R.id.addproductid);
                minusbutton = itemView.findViewById(R.id.productminus);
                quantity = itemView.findViewById(R.id.productquantity);
            }
        }
    }
}
