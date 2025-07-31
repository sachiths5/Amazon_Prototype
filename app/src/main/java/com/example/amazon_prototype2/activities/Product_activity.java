    package com.example.amazon_prototype2.activities;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.amazon_prototype2.R;
    import com.example.amazon_prototype2.model.CategoryDataModel;

    import java.util.ArrayList;
    import java.util.List;

    public class Product_activity extends AppCompatActivity {
        static ArrayList<CategoryDataModel.product_details> productDetailsArrayList;
        RecyclerView recyclerView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_product);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            productDetailsArrayList=getIntent().getParcelableArrayListExtra("productlist");
            adapter10 adapter10=new adapter10(productDetailsArrayList);
            recyclerView=findViewById(R.id.recyclerview10);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter10);



        }
        static class adapter10 extends RecyclerView.Adapter<adapter10.viewholder>{
            public adapter10(List<CategoryDataModel.product_details> productDetailsList) {
                this.productDetailsList = productDetailsList;
            }

            List<CategoryDataModel.product_details>productDetailsList;
            @NonNull
            @Override
            public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout_fragment,parent,false);
                return new viewholder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull viewholder holder, int position) {
                CategoryDataModel.product_details prod=productDetailsArrayList.get(position);
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
                return productDetailsArrayList.size();
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