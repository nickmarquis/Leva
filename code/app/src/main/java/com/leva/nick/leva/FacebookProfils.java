package com.leva.nick.leva;

import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by nick on 14/04/15.
 */
public class FacebookProfils implements Parcelable {


    private String mFirstName;
    private String mName;
    private String mProfilPic;
    private String mCoverPic;
    private String mId;

    public FacebookProfils(String mFirstName, String mName, String mProfilPic, String mCoverPic, String mId) {
        this.mFirstName = mFirstName;
        this.mName = mName;
        this.mProfilPic = mProfilPic;
        this.mCoverPic = mCoverPic;
        this.mId = mId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmProfilPic() {
        return mProfilPic;
    }

    public void setmProfilPic(String mProfilPic) {
        this.mProfilPic = mProfilPic;
    }

    public String getmCoverPic() {
        return mCoverPic;
    }

    public void setmCoverPic(String mCoverPic) {
        this.mCoverPic = mCoverPic;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


    protected FacebookProfils(Parcel in) {
        mFirstName = in.readString();
        mName = in.readString();
        mProfilPic = in.readString();
        mCoverPic = in.readString();
        mId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeString(mName);
        dest.writeString(mProfilPic);
        dest.writeString(mCoverPic);
        dest.writeString(mId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FacebookProfils> CREATOR = new Parcelable.Creator<FacebookProfils>() {
        @Override
        public FacebookProfils createFromParcel(Parcel in) {
            return new FacebookProfils(in);
        }

        @Override
        public FacebookProfils[] newArray(int size) {
            return new FacebookProfils[size];
        }
    };
}