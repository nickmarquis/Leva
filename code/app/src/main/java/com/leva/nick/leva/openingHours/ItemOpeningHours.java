package ca.ulaval.ima.miniproject.openingHours;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davstpierre on 15-04-06.
 */
public class ItemOpeningHours implements Parcelable {

    private String day;
    private String timeOpen;
    private String timeClose;

    public ItemOpeningHours(String day, String timeOpen, String timeClose) {
        super();
        this.day = day;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTimeOpen(String timeOpen) {
        this.timeOpen = timeOpen;
    }

    public void setTimeClose(String timeClose) {
        this.timeClose = timeClose;
    }

    public String getDay() {
        return this.day;
    }

    public String getTimeOpen() {
        return this.timeOpen;
    }

    public String getTimeClose() {
        return this.timeClose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    // Sert à écrire un objet de type Item dans un Parcel.
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(timeOpen);
        dest.writeString(timeClose);
    }

    public static final Parcelable.Creator<ItemOpeningHours> CREATOR = new Parcelable.Creator<ItemOpeningHours>() {

        @Override
        public ItemOpeningHours createFromParcel(Parcel source) {
            return new ItemOpeningHours(source);
        }

        @Override
        public ItemOpeningHours[] newArray(int size) {
            return new ItemOpeningHours[size];
        }
    };

    // Construction d'une instance de Item à partir d'un Parcel.
    public ItemOpeningHours(Parcel in) {
        this.day = in.readString();
        this.timeOpen = in.readString();
        this.timeClose = in.readString();
    }

    public static Parcelable.Creator<ItemOpeningHours> getCreator() {
        return CREATOR;
    }
}
