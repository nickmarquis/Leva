package com.leva.nick.leva.promotions;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davstpierre on 15-03-14.
 */
public class ItemPromotion implements Parcelable {

    private String day;
    private String promotionName;
    private String promotionDescription;
    private String timeStart;
    private String timeEnd;
    private String image;

    public ItemPromotion(String day, String promotionName, String promotionDescription, String timeStart,
                         String timeEnd, String image) {
        super();
        this.day = day;
        this.promotionName = promotionName;
        this.promotionDescription = promotionDescription;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.image = image;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDay() {
        return this.day;
    }

    public String getPromotionName() {
        return this.promotionName;
    }

    public String getPromotionDescription() {
        return this.promotionDescription;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }

    public String getImage() {
        return this.image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    // Sert à écrire un objet de type Item dans un Parcel.
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(promotionName);
        dest.writeString(promotionDescription);
        dest.writeString(timeStart);
        dest.writeString(timeEnd);
        dest.writeString(image);
    }

    public static final Creator<ItemPromotion> CREATOR = new Creator<ItemPromotion>() {

        @Override
        public ItemPromotion createFromParcel(Parcel source) {
            return new ItemPromotion(source);
        }

        @Override
        public ItemPromotion[] newArray(int size) {
            return new ItemPromotion[size];
        }
    };

    // Construction d'une instance de Item à partir d'un Parcel.
    public ItemPromotion(Parcel in) {
        this.day = in.readString();
        this.promotionName = in.readString();
        this.promotionDescription = in.readString();
        this.timeStart = in.readString();
        this.timeEnd = in.readString();
        this.image = in.readString();
    }

    public static Creator<ItemPromotion> getCreator() {
        return CREATOR;
    }
}