package com.example.amazon_prototype2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amazon_prototype2.model.CategoryDataModel;

import java.util.ArrayList;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.recycleradapter>{
    private final int source;


    public interface MyClickListener {
        void onclickitem(int position, int source);
    }

    private final ArrayList<CategoryDataModel> data;
    private final MyClickListener listener;
Context context;
    public Category_Adapter(int source, ArrayList<CategoryDataModel> data, MyClickListener listener, Context
            context) {
        this.source = source;
        this.data = data;
        this.listener = listener;
        this.context = context;
    }
    @NonNull
    @Override
    public Category_Adapter.recycleradapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_recycler_adapter_layout, parent, false);
        return new recycleradapter(view, listener,source);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleradapter holder, int position) {
        CategoryDataModel dat = data.get(position);
        holder.imageView3.setImageResource(dat.getRecyclerimage());
        holder.textView3.setText(dat.getText());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class recycleradapter extends RecyclerView.ViewHolder {
        ImageView imageView3;
        TextView textView3;

        public recycleradapter(@NonNull View itemView, MyClickListener listener, int source) {
            super(itemView);
            imageView3 = itemView.findViewById(R.id.recyclerimgid);
            textView3 = itemView.findViewById(R.id.recyclertextid);
            itemView.setOnClickListener(v -> listener.onclickitem(getAdapterPosition(), Category_Adapter.this.source));
        }
    }
}

 class recyclerdata {
    private String text,title;
    private int recyclerimage;

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public recyclerdata(String text, int recyclerimage,String title) {
        this.text = text;
        this.recyclerimage = recyclerimage;
        this.title=title;
    }

    public String getText() {
        return text;
    }

    public int getRecyclerimage() {
        return recyclerimage;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRecyclerimage(int recyclerimage) {
        this.recyclerimage = recyclerimage;
    }
}
