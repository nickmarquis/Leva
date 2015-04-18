package com.leva.nick.leva;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.leva.nick.leva.openingHours.ItemOpeningHours;
import com.leva.nick.leva.promotions.ItemPromotion;

import java.util.ArrayList;

/**
 * Created by nick on 19/03/15.
 */

public class SpotsMarker implements Parcelable {

    private String mLabel;
    private String mIcon;
    private LatLng mLatLng;
    private String mPicture;
    private String mImage;
    private ArrayList<ItemOpeningHours> mOpeningHours;
    private ArrayList<ItemPromotion> mPromotions;

    public SpotsMarker(String label, String icon, LatLng latiLong, String picture, ArrayList<ItemOpeningHours> mOpeningHours,
                       ArrayList<ItemPromotion> mPromotions)
    {
        this.mLabel = label;
        this.mIcon = icon;
        this.mLatLng = latiLong;
        this.mPicture = picture;
        this.mOpeningHours = mOpeningHours;
        this.mPromotions = mPromotions;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public String getmIcon()
    {
        return mIcon;
    }

    public void setmIcon(String icon)
    {
        this.mIcon = icon;
    }

    public LatLng getmLatLng()
    {
        return mLatLng;
    }

    public void setmLatitude(LatLng latiLong)
    {
        this.mLatLng = latiLong;
    }

    public String getmPicture()
    {
        return mPicture;
    }

    public void setmPicture(String picture)
    {
        this.mPicture = picture;
    }

    public void setmOpeningHours(ArrayList<ItemOpeningHours> mOpeningHours) { this.mOpeningHours = mOpeningHours; }

    public void setmPromotions(ArrayList<ItemPromotion> mPromotions) { this.mPromotions = mPromotions;}

    public ArrayList<ItemOpeningHours> getmOpeningHours() { return mOpeningHours;}

    public ArrayList<ItemPromotion> getmPromotions() { return mPromotions;}

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    protected SpotsMarker(Parcel in) {
        mLabel = in.readString();
        mIcon = in.readString();
        mLatLng = (LatLng) in.readValue(LatLng.class.getClassLoader());
        mPicture = in.readString();
        mImage = in.readString();
        if (in.readByte() == 0x01) {
            mOpeningHours = new ArrayList<ItemOpeningHours>();
            in.readList(mOpeningHours, ItemOpeningHours.class.getClassLoader());
        } else {
            mOpeningHours = null;
        }
        if (in.readByte() == 0x01) {
            mPromotions = new ArrayList<ItemPromotion>();
            in.readList(mPromotions, ItemPromotion.class.getClassLoader());
        } else {
            mPromotions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLabel);
        dest.writeString(mIcon);
        dest.writeValue(mLatLng);
        dest.writeString(mPicture);
        dest.writeString(mImage);
        if (mOpeningHours == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mOpeningHours);
        }
        if (mPromotions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mPromotions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SpotsMarker> CREATOR = new Parcelable.Creator<SpotsMarker>() {
        @Override
        public SpotsMarker createFromParcel(Parcel in) {
            return new SpotsMarker(in);
        }

        @Override
        public SpotsMarker[] newArray(int size) {
            return new SpotsMarker[size];
        }
    };
}