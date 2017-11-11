package com.freelance.netanel.androidsearchapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Netanel on 24/09/2017.
 */

public class Product implements Parcelable {
    @SerializedName("mId")
    private int mId;

    @SerializedName("mName")
    private String mName;

    @SerializedName("mImageUrl")
    private String mImageUrl;

    @SerializedName("mDescription")
    private String mDescription;
    public Product(){}
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    private Product(Parcel inputParcel) {
        mId = inputParcel.readInt();
        mName = inputParcel.readString();
        mDescription = inputParcel.readString();
        mImageUrl = inputParcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mImageUrl);
    }
}
