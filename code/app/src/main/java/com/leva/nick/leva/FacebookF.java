package com.leva.nick.leva;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leva.nick.leva.common.logger.Log;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
        ImageButton settings = (ImageButton) view.findViewById(R.id.settingsB);

        settings.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                OnLogoutListener onLogoutListener = new OnLogoutListener() {
                    @Override
                    public void onLogout() {
                        Log.i("facebook", "You are logged out");
                        Intent myIntent = new Intent(getActivity(), TabLayoutA.class);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onThinking() {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }

                    @Override
                    public void onFail(String reason) {

                    }
                };
                mSimpleFacebook.logout(onLogoutListener);
            }
        });



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
                ProfileAdapter adapter = new ProfileAdapter(getActivity(), (ArrayList)mFriends);
                ListView list = (ListView) getView().findViewById((R.id.friendListF));
                list.setAdapter(adapter);
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

                AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>(){
                    protected Bitmap doInBackground(Void... p) {
                        Bitmap bm = null;
                        try {
                            URL aURL = new URL("https://graph.facebook.com/"+mMyProfil.getId()+"/picture?type=large");
                            URLConnection conn = aURL.openConnection();
                            conn.setUseCaches(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            BufferedInputStream bis = new BufferedInputStream(is);
                            bm = BitmapFactory.decodeStream(bis);
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bm;
                    }

                    protected void onPostExecute(Bitmap bm){

                        Drawable drawable = new BitmapDrawable(getResources(), bm);

                        mProfileImg.setImageDrawable(drawable);

                    }
                };
                t.execute();

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

    static public class ProfileAdapter extends ArrayAdapter<Profile> {

        private ArrayList<Profile> mProfileList;
        private Activity mActivity;
        private ViewHolder mViewHolder;

        public ProfileAdapter(Activity inActivity, ArrayList<Profile> inEntryList){
            super(inActivity, R.layout.cell_facebook    , inEntryList);
            this.mActivity = inActivity;
            mProfileList = inEntryList;

        }

        public void addEntry(Profile outItem){
            mProfileList.add(outItem);
        }

        @Override
        public int getCount() {
            return mProfileList.size();
        }

        @Override
        public Profile getItem(int arg0) {
            return mProfileList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        static class ViewHolder {
            public ImageView image;
            public TextView name;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {

            View childView = arg1;
            if(childView == null || childView.getTag() == null){

                childView = mActivity.getLayoutInflater().inflate(R.layout.cell_facebook, null);

                ViewHolder viewHolder = new ViewHolder();

                viewHolder.image = (ImageView) childView.findViewById(R.id.imageFcell);
                viewHolder.name = (TextView) childView.findViewById(R.id.nameFcell);

                childView.setTag(viewHolder);
            }
            final ImageView image = (ImageView) childView.findViewById(R.id.imageFcell);

            AsyncTask<String, Void, Bitmap> t = new AsyncTask<String, Void, Bitmap>() {
                protected Bitmap doInBackground(String... p) {
                    Bitmap bm = null;
                    try {
                        URL aURL = new URL(p[0]);
                        URLConnection conn = aURL.openConnection();
                        conn.setUseCaches(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        bm = BitmapFactory.decodeStream(bis);
                        bis.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bm;
                }

                protected void onPostExecute(Bitmap bm){

                    image.setImageBitmap(bm);

                }
            };
            t.execute(mProfileList.get(arg0).getPicture());

            ViewHolder holder = (ViewHolder) childView.getTag();
            holder.name.setText(mProfileList.get(arg0).getFirstName() + " " + mProfileList.get(arg0).getLastName());

            return childView;
        }

    }

}
