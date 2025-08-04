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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Limited_Offer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Limited_Offer_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView limitedoffer_recyclerview;

    // TODO: Rename and change types of parameters

    ArrayList<CategoryDataModel.product_details>offers_List;

    public Limited_Offer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Limited_Offer_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Limited_Offer_Fragment newInstance(String param1, String param2) {
        Limited_Offer_Fragment fragment = new Limited_Offer_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        offers_List=new ArrayList<>();
        String[]offer_name=getResources().getStringArray(R.array.limited_offer_names);
        String[]offer_description=getResources().getStringArray(R.array.limited_offer_descriptions);
        int[]offer_prices=getResources().getIntArray(R.array.limited_offer_prices);
        int[]offer_images={R.drawable.limited_offer1,R.drawable.limited_offer6,R.drawable.limited_offer7,R.drawable.limited_offer8};
        int lenght=Math.min(Math.min(offer_name.length,offer_description.length),Math.min(offer_prices.length,offer_images.length));
        for(int i=0;i<lenght;i++){
            offers_List.add(new CategoryDataModel.product_details(offer_name[i],offer_description[i],offer_prices[i],offer_images[i]));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.limited_offer_layout, container, false);
        limitedoffer_recyclerview=view.findViewById(R.id.limitedoffer_recyclerview);
        limitedoffer_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        limitedoffer_adapter limitedofferAdapter= new limitedoffer_adapter(getActivity(), offers_List);
        limitedoffer_recyclerview.setAdapter(limitedofferAdapter);
        return view;
    }
    //Limited Offer category adapter
    static class limitedoffer_adapter extends RecyclerView.Adapter<limitedoffer_adapter.limitedoffer_viewholder>{
        Context context;
        ArrayList<CategoryDataModel.product_details>offers_List;

        public limitedoffer_adapter(Context context, ArrayList<CategoryDataModel.product_details> offers_List) {
            this.context = context;
            this.offers_List = offers_List;
        }

        @NonNull
        @Override
        public limitedoffer_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.product_layout_fragment,parent,false);
            return new limitedoffer_viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull limitedoffer_viewholder holder, int position) {
            CategoryDataModel.product_details offer=offers_List.get(position);
            holder.offer_image.setImageResource(offer.getImage());
            holder.offer_name.setText(offer.getName());
            holder.offer_price.setText(String.valueOf(offer.getPrice()));
            holder.offer_description.setText(offer.getDesrciption());


        }

        @Override
        public int getItemCount() {
            return offers_List.size();
        }

        static class limitedoffer_viewholder extends RecyclerView.ViewHolder{
            ImageView offer_image;
            TextView offer_name,offer_description,offer_price;
            public limitedoffer_viewholder(@NonNull View itemView) {
                super(itemView);
                offer_description=itemView.findViewById(R.id.product_description);
                offer_name=itemView.findViewById(R.id.product_name);
                offer_price=itemView.findViewById(R.id.product_price);
                offer_image=itemView.findViewById(R.id.product_image);
        }
    }
}}