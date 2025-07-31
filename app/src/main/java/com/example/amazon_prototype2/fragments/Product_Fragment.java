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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amazon_prototype2.R;
import com.example.amazon_prototype2.model.CategoryDataModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Product_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Product_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView product_recyclerview;
    ArrayList<CategoryDataModel.product_details>product_detail;

    public Product_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Product_Fragment.
     */
    // TODO: Rename and change types and number of parameters
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
        assert getArguments() != null;
        product_detail=new ArrayList<>();
        product_detail = getArguments().getParcelableArrayList("productlist");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_, container, false);
        product_recyclerview=view.findViewById(R.id.product_recycler);
        product_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        product_adapter product_adapter=new product_adapter(product_detail,getContext());
        product_recyclerview.setAdapter(product_adapter);

        return view;
    }static class product_adapter extends RecyclerView.Adapter<product_adapter.viewholder>{
        private final ArrayList<CategoryDataModel.product_details> productList;
        Context context;

        public product_adapter(List<CategoryDataModel.product_details> productList,Context context) {
            this.context=context;
            this.productList = (ArrayList<CategoryDataModel.product_details>) productList;
        }


        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(context).inflate(R.layout.product_layout_fragment,parent,false);
                return new viewholder(view);
        }

        @NonNull

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            CategoryDataModel.product_details prod=productList.get(position);
            try {holder.productimage.setImageResource(prod.getImage());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            holder.productname.setText(prod.getName());
            holder.productdescription.setText(prod.getDesrciption());
            holder.productprice.setText(String.valueOf(prod.getPrice()));

        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        static class viewholder extends RecyclerView.ViewHolder{
            ImageView productimage;
            TextView productname,productdescription,productprice;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                productdescription=itemView.findViewById(R.id.product_description);
                productname=itemView.findViewById(R.id.product_name);
                productprice=itemView.findViewById(R.id.product_price);
                productimage=itemView.findViewById(R.id.product_image);

            }
        }
    }
}