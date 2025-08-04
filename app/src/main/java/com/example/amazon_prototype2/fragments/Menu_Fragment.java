  package com.example.amazon_prototype2.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.amazon_prototype2.R;

  /**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Menu_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Menu_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Menu_Fragment newInstance(String param1, String param2) {
        Menu_Fragment fragment = new Menu_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView home,chatbox,cart,cart2;

        View view= inflater.inflate(R.layout.menu_fragment_layout, container, false);
        home=view.findViewById(R.id.homemenu);
        chatbox=view.findViewById(R.id.chatmenu);
        cart=view.findViewById(R.id.cartmenu);
        cart2=view.findViewById(R.id.cartmenu2);
        home.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new HomeFragment())
                    .commit();
        });chatbox.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new Aichatbox_Fragment())
                    .commit();
        });cart.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new Cart_Fragment())
                    .commit();
        });cart2.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new Cart_Fragment())
                    .commit();
        });
        return view;
    }
}