package com.leva.nick.leva;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leva.nick.leva.common.logger.Log;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookF extends Fragment {

    private static final String KEY_TITLE = "facebook";

    private SimpleFacebook mSimpleFacebook;
    private List<Profile> mFriends;
    private Profile mMyProfil;

    ImageView mProfileImg;
    TextView mProfileNom;


    public static FacebookF newInstance(CharSequence title) {

        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);

        FacebookF fragment = new FacebookF();
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSimpleFacebook = SimpleFacebook.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);

        getFriendsList();
        getProfile();

        mProfileImg = (ImageView) view.findViewById(R.id.imageF);
        mProfileNom = (TextView) view.findViewById(R.id.nomF);

        //profileNom.setText(mMyProfil.getFirstName() + mMyProfil.getName());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(getActivity(), requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance();
    }

    private void getFriendsList() {
        OnFriendsListener onFriendsListener = new OnFriendsListener() {
            @Override
            public void onComplete(List<Profile> friends) {
                Log.i("Facebook", "Number of friends = " + friends.size());
                mFriends = friends;
            }
        };

        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(150);
        pictureAttributes.setWidth(150);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.FIRST_NAME)
                .add(Profile.Properties.LAST_NAME)
                .add(Profile.Properties.INSTALLED)
                .add(Profile.Properties.PICTURE, pictureAttributes)
                .build();

        mSimpleFacebook.getFriends(properties, onFriendsListener);
    }

    private void getProfile() {

        OnProfileListener onProfileListener = new OnProfileListener() {
            @Override
            public void onException(Throwable throwable) {

                Log.i("Facebook", "Exception");
            }

            @Override
            public void onFail(String reason) {
                Log.i("Facebook", reason);
            }

            @Override
            public void onComplete(Profile profile) {
                Log.i("Facebook", "My profile id = " + profile.getId());
                mMyProfil = profile;
                mProfileNom.setText(mMyProfil.getFirstName() + " " + mMyProfil.getLastName());
            }

        };

        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
        pictureAttributes.setHeight(150);
        pictureAttributes.setWidth(150);
        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);

        Profile.Properties properties = new Profile.Properties.Builder()
                .add(Profile.Properties.ID)
                .add(Profile.Properties.FIRST_NAME)
                .add(Profile.Properties.LAST_NAME)
                .add(Profile.Properties.PICTURE, pictureAttributes)
                .add(Profile.Properties.COVER)
                .build();

        mSimpleFacebook.getProfile(properties, onProfileListener);
    }


}
