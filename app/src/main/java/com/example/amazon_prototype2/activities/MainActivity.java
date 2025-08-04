package com.example.amazon_prototype2.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.amazon_prototype2.R;
import com.example.amazon_prototype2.fragments.Aichatbox_Fragment;
import com.example.amazon_prototype2.fragments.Cart_Fragment;
import com.example.amazon_prototype2.fragments.HomeFragment;
import com.example.amazon_prototype2.fragments.Menu_Fragment;
import com.example.amazon_prototype2.fragments.Profile_Fragment;
import com.example.amazon_prototype2.model.CartManager;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Fragment home=new HomeFragment();
        Fragment profile=new Profile_Fragment();
        Fragment menu=new Menu_Fragment();
        Fragment cart=new Cart_Fragment();
        Fragment aichat=new Aichatbox_Fragment();
        bottomNavigationView = findViewById(R.id.bottomid);

        updateCartBadge(CartManager.getCartList().size());
        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentid,new HomeFragment())
                    .commit();
        }
        bottomNavigationView=findViewById(R.id.bottomid);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemid=item.getItemId();
            if(itemid==R.id.homeid){
                frag(home);
            } else if (itemid==R.id.profileid) {
                frag(profile);
            } else if (itemid==R.id.menuid) {
                frag(menu);

            } else if (itemid==R.id.cartid) {
                frag(cart);

            }
            else if (itemid==R.id.rufusid)
                frag(aichat);
            return true;
        });
    }
    public void frag(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentid,fragment)
                .addToBackStack(null)
                .commit();
    }
    public void updateCartBadge(int count) {
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cartid);
        if (count > 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(count);
        } else {
            badgeDrawable.setVisible(false);
        }
    }
    public void removeBadgeFromCart() {
        BottomNavigationView nav = findViewById(R.id.bottomid); // replace with your nav ID
        nav.removeBadge(R.id.cartid);
    }

}