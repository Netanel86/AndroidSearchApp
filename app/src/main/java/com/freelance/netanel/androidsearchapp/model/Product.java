package com.freelance.netanel.androidsearchapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Netanel on 24/09/2017.
 * Describes a product.
 * Implements {@link Parcelable}, for passing instances when using {@link android.content.Intent}
 */
public class Product implements Parcelable {
    @SerializedName("id")
    private int mId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (mId != product.mId) return false;
        if (mName != null ? !mName.equals(product.mName) : product.mName != null) return false;
        if (mImageUrl != null ? !mImageUrl.equals(product.mImageUrl) : product.mImageUrl != null) {
            return false;
        }
        return mDescription != null ? mDescription.equals(product.mDescription)
                : product.mDescription == null;
    }

    @Override
    public int hashCode() {
        int result = mId;
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mImageUrl != null ? mImageUrl.hashCode() : 0);
        result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
        return result;
    }

    @SerializedName("name")
    private String mName;

    @SerializedName("imageUrl")
    private String mImageUrl;

    @SerializedName("description")
    private String mDescription;

    ///region Parcelable
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
    ///endregion

    public Product(int id, String name, String description, String imageUrl) {
        mId = id;
        mName = name;
        mDescription = description;
        mImageUrl = imageUrl;
    }

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
}
