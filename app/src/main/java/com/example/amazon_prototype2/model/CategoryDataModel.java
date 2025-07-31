package com.example.amazon_prototype2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CategoryDataModel implements Parcelable {
    private String text, title;
    private int recyclerimage;

    // You can optionally add this if you have a list of product_details
    private ArrayList<product_details> productList;

    public CategoryDataModel(String text, int recyclerimage, String title) {
        this.text = text;
        this.recyclerimage = recyclerimage;
        this.title = title;
    }

    public CategoryDataModel(String text, int recyclerimage, String title, ArrayList<product_details> productList) {
        this.text = text;
        this.recyclerimage = recyclerimage;
        this.title = title;
        this.productList = productList;
    }

    protected CategoryDataModel(Parcel in) {
        text = in.readString();
        title = in.readString();
        recyclerimage = in.readInt();
        productList = in.createTypedArrayList(product_details.CREATOR);
    }

    public static final Creator<CategoryDataModel> CREATOR = new Creator<CategoryDataModel>() {
        @Override
        public CategoryDataModel createFromParcel(Parcel in) {
            return new CategoryDataModel(in);
        }

        @Override
        public CategoryDataModel[] newArray(int size) {
            return new CategoryDataModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(title);
        dest.writeInt(recyclerimage);
        dest.writeTypedList(productList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter and Setter methods
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<product_details> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<product_details> productList) {
        this.productList = productList;
    }

    // Inner class: product_details remains the same as you posted
    public static class product_details implements Parcelable {
        private String name, desrciption;
        private int price, image;

        public product_details(String name, String desrciption, int price, int image) {
            this.name = name;
            this.desrciption = desrciption;
            this.price = price;
            this.image = image;
        }

        protected product_details(Parcel in) {
            name = in.readString();
            desrciption = in.readString();
            price = in.readInt();
            image = in.readInt();
        }

        public static final Creator<product_details> CREATOR = new Creator<product_details>() {
            @Override
            public product_details createFromParcel(Parcel in) {
                return new product_details(in);
            }

            @Override
            public product_details[] newArray(int size) {
                return new product_details[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(desrciption);
            dest.writeInt(price);
            dest.writeInt(image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Getter and Setter methods
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesrciption() {
            return desrciption;
        }

        public void setDesrciption(String desrciption) {
            this.desrciption = desrciption;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }
}
