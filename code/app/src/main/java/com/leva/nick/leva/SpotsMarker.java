package com.leva.nick.leva;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nick on 19/03/15.
 */

public class SpotsMarker implements Parcelable {

    private String mLabel;
    private String mIcon;
    private LatLng mLatLng;
    private String mPicture;
    private String mDescription;
    private String mImage;

    public SpotsMarker(String label, String icon, LatLng latiLong, String picture, String description)
    {
        this.mLabel = label;
        this.mIcon = icon;
        this.mLatLng = latiLong;
        this.mPicture = picture;
        this.mDescription = description;
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

    public String getmDescription()
    {
        return mDescription;
    }

    public void setmDescription(String description)
    {
        this.mDescription = description;
    }

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
        mDescription = in.readString();
        mImage = in.readString();
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
        dest.writeString(mDescription);
        dest.writeString(mImage);
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