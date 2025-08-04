package com.example.amazon_prototype2.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.amazon_prototype2.R;
import com.example.amazon_prototype2.Category_Adapter;
import com.example.amazon_prototype2.model.CategoryDataModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements Category_Adapter.MyClickListener {

    private ViewPager2 slider_images;
    private Handler handler;

    private List<Integer> imageres;
    private List<Integer> bannerImages;
    private ArrayList<CategoryDataModel> categoryList;
    private ArrayList<CategoryDataModel> offerList;
    private List<CategoryDataModel.product_details> productDetailsList;
    private List<CategoryDataModel.product_details>fashiondetails;
    private List<CategoryDataModel.product_details> productDetailsList2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageres = List.of(R.drawable.img, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4);
        bannerImages = new ArrayList<>();
        categoryList = new ArrayList<>();
        offerList = new ArrayList<>();
        productDetailsList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            bannerImages.add(R.drawable.smartphone_offer);
            bannerImages.add(R.drawable.laptop_offer);
        }
        // adds data to categories
        categoryList.add(new CategoryDataModel("Electronics", R.drawable.electronics, "Electronics"));
        categoryList.add(new CategoryDataModel("Grocery", R.drawable.grocery, "Grocery"));
        categoryList.add(new CategoryDataModel("Fashion", R.drawable.fashion, "Fashion"));
        categoryList.add(new CategoryDataModel("Medicine", R.drawable.medicine, "Medicine"));
        //add data to offers
        offerList.add(new CategoryDataModel("Limited Offer", R.drawable.limited_offer1, "offer1"));
        offerList.add(new CategoryDataModel("Limited Offer 2", R.drawable.limited_offer6, "offer2"));
        offerList.add(new CategoryDataModel("Limited Offer 3", R.drawable.limited_offer7, "offer3"));
        offerList.add(new CategoryDataModel("Limited Offer 4", R.drawable.limited_offer8, "offer4"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        slider_images = view.findViewById(R.id.viewpagerid);
        RecyclerView categories_recycleview1 = view.findViewById(R.id.recyclerview1);
        RecyclerView categories_recyclview1 = view.findViewById(R.id.recyclerview2);
        RecyclerView categories_recyclerview = view.findViewById(R.id.recyclerview3);
        imageadapter viewPagerAdapter = new imageadapter(imageres);
        slider_images.setAdapter(viewPagerAdapter);
        setupAutoScroll();

        //categories recycler
        Category_Adapter categoryAdapter = new Category_Adapter( 1,categoryList,this,getActivity());
        categories_recyclview1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categories_recyclview1.setAdapter(categoryAdapter);

        //limited  offer recycler
        categoryAdapter = new Category_Adapter(2,offerList,this,getActivity());
        categories_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categories_recyclerview.setAdapter(categoryAdapter);
        banner_images_adapter bannerAdapter = new banner_images_adapter(bannerImages);
        
        categories_recycleview1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categories_recycleview1.setAdapter(bannerAdapter);


        return view;
    }

// image slider method
    private void setupAutoScroll() {
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int current = slider_images.getCurrentItem();
                int next = (current + 1) % imageres.size();
                slider_images.setCurrentItem(next, true);
                slider_images.setOffscreenPageLimit(imageres.size());
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable,2000);

    }

    // sends data to product fragment
    private void Electronicsdata() {
        String[] names = getResources().getStringArray(R.array.electronics_names);
        String[] descriptions = getResources().getStringArray(R.array.electronics_descriptions);
        int[] prices = getResources().getIntArray(R.array.electronics_prices);
        int[] imageNames = {R.drawable.phone_samsunga71, R.drawable.laptop_ideapad5, R.drawable.smart_watch, R.drawable.bluethoot_speaker};
        int length = Math.min(
                Math.min(names.length, descriptions.length),
                Math.min(prices.length, imageNames.length)
        );
        for (int i = 0; i < length; i++) {
            productDetailsList.add(new CategoryDataModel.product_details(names[i], descriptions[i], prices[i], imageNames[i]));
        }
    }
    private void Grocerydata() {
        productDetailsList2 = new ArrayList<>();
        String[] names2 = getResources().getStringArray(R.array.grocery_names);
        String[] descriptions2 = getResources().getStringArray(R.array.grocery_descriptions);
        int[] prices2 = getResources().getIntArray(R.array.grocery_prices);
        int []imageNames2={R.drawable.rice,R.drawable.almonds,R.drawable.green_tea};
        for (int i = 0; i < names2.length; i++) {
            productDetailsList2.add(new CategoryDataModel.product_details(names2[i], descriptions2[i], prices2[i], imageNames2[i]));
        }
    }
    private void Fashiondata(){
        fashiondetails=new ArrayList<>();
        String[] dress_name=getResources().getStringArray(R.array.fashion_names);
        String[] dress_description=getResources().getStringArray(R.array.fashion_descriptions);
        int[] dress_price=getResources().getIntArray(R.array.fashion_prices);
        int[] dress_image={R.drawable.cotton_tshirt,R.drawable.denims_jacket,R.drawable.sneaker};
        int length=Math.min(Math.min(dress_name.length,dress_description.length),Math.min(dress_price.length, dress_image.length));
        for(int i=0;i<length;i++){
            fashiondetails.add(new CategoryDataModel.product_details(dress_name[i],dress_description[i],dress_price[i],dress_image[i]));
        }
    }


    //Interface for onlick in recyclerview
    @Override
    public void onclickitem(int position, int source) {
        if(source==1){
        CategoryDataModel clickedItem = categoryList.get(position);
        if (clickedItem.getTitle().equals("Electronics")) {
            Electronicsdata();
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("productlist",new ArrayList<>(productDetailsList));
            Fragment product=new Product_Fragment();
            product.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,product)
                    .addToBackStack(null)
                    .commit();
            //Intent intent = new Intent(getActivity(), Product_activity.class);
            //intent.putParcelableArrayListExtra("productlist", new ArrayList<>(productDetailsList));
            //startActivity(intent);
        }
        if (clickedItem.getTitle().equals("Grocery")) {
            //Intent intent2 = new Intent(getActivity(), Product_activity.class);
            //intent2.putParcelableArrayListExtra("productlist", new ArrayList<>(productDetailsList2));
            //startActivity(intent2);
            Grocerydata();

            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("productlist",new ArrayList<>(productDetailsList2));
            Fragment product=new Product_Fragment();
            product.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,product)
                    .addToBackStack(null)
                    .commit();
        }
        if(clickedItem.getTitle().equals("Fashion")){
            Fashiondata();
            Bundle bundle=new Bundle();
            bundle.putParcelableArrayList("productlist",new ArrayList<>(fashiondetails));
            Fragment product=new Product_Fragment();
            product.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,product)
                    .addToBackStack(null)
                    .commit();

        }}
        if(source==2){

            CategoryDataModel clickedItem = offerList.get(position);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new Limited_Offer_Fragment())
                    .addToBackStack(null)
                    .commit();}
            /*
            if(clickedItem.getTitle().equals("Limited Offer1")){
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentid,new Limited_Offer_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        }*/


    }
    // sliding images adapter
    static class imageadapter extends RecyclerView.Adapter<imageadapter.imageviewholder> {
        private final List<Integer> imageList;

        public imageadapter(List<Integer> imageList) {
            this.imageList = imageList;
        }
        @NonNull
        @Override
        public imageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_images_layout, parent, false);
            return new imageviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull imageviewholder holder, int position) {
            holder.imageView.setImageResource(imageList.get(position));
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        static class imageviewholder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public imageviewholder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.viewpager_imageid);
            }
        }
    }
    //Banner Images Adapter
    static class banner_images_adapter extends RecyclerView.Adapter<banner_images_adapter.viewholder5> {
        private final List<Integer> imageList;

        public banner_images_adapter(List<Integer> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public viewholder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_images_layout, parent, false);
            return new viewholder5(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder5 holder, int position) {
            holder.imageView.setImageResource(imageList.get(position));
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        static class viewholder5 extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewholder5(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.bannerImage);
            }
        }
    }
}


