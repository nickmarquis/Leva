package com.leva.nick.leva;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.leva.nick.leva.common.activities.SampleActivityBase;
import com.leva.nick.leva.common.logger.Log;
import com.leva.nick.leva.common.logger.LogFragment;
import com.leva.nick.leva.common.logger.LogWrapper;
import com.leva.nick.leva.common.logger.MessageOnlyLogFilter;

import java.util.ArrayList;
import java.util.List;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;


public class TabLayoutA extends SampleActivityBase {

    public static final String TAG = "TabLayout";
    private static final String APP_ID = "371005393088854";
    private static final String APP_NAMESPACE = "leva";

    private SimpleFacebook mSimpleFacebook;

    private boolean mLogShown;
    private ArrayList<SpotsMarker> mMyMarkersArray = new ArrayList<SpotsMarker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tab_layout);

        Permission[] permissions = new Permission[] {
                Permission.PUBLIC_PROFILE,
                Permission.USER_FRIENDS
        };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace(APP_NAMESPACE)
                .setPermissions(permissions)
                .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                .setAskForAllPermissionsAtOnce(false)
                .build();

        SimpleFacebook.setConfiguration(configuration);

        mSimpleFacebook = SimpleFacebook.getInstance(this);

        setLogin();

        initMarkers();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TabFrameF fragment = TabFrameF.newInstance(mMyMarkersArray);
            transaction.replace(R.id.content_fragment, fragment, TAG);
            transaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_layout, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }


    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

    }

    public void initMarkers() {

//        FileOutputStream fout = new FileOutputStream(fileName);
//        ObjectOutputStream out = new ObjectOutputStream(fout);

        mMyMarkersArray.add(new SpotsMarker("Blaxton", "ic_house", new LatLng(46.80283, -71.22465), "ic_blaxton", "Ceci est un resto/bar. Le Blaxon marche très bien le vendredi soir. Il y a deux étages et servent du popcorn ...................................................................................................................." ));
        mMyMarkersArray.add(new SpotsMarker("4 foyers", "ic_house", new LatLng(47.027088, -71.383312), "ic_4foyers", "Ceci est un resto/bar de ski. Le monde vont dans cette établissement le après un journée de ski ou simplement pour diner. Le vendredi soir le bar est plein et il ya a un band différent ....................................." ));
        mMyMarkersArray.add(new SpotsMarker("Maurice", "ic_house", new LatLng(46.805820, -71.216991), "ic_maurice", "Ceci est un club" ));
        mMyMarkersArray.add(new SpotsMarker("Archibald", "ic_house", new LatLng(46.943319, -71.292171), "ic_archibald", "Ceci est un resto/bar" ));
        mMyMarkersArray.add(new SpotsMarker("Valcartier", "ic_house", new LatLng(47.018067, -71.473618), "ic_valcartier", "Ceci est un parc d'amusement" ));
        mMyMarkersArray.add(new SpotsMarker("Le Relais", "ic_house", new LatLng(46.940381, -71.299596), "ic_relais", "Ceci est centre de ski" ));

//        for (int i = 0; i < 6; i++) {
//
//            out.writeObject(p1);
//        }
//
//        out.writeObject(p1);
//
//        out.close();

    }

    public void addMarkers(String fileName, SpotsMarker marker) {

        mMyMarkersArray.add(marker);

//        FileOutputStream fout = new FileOutputStream(fileName);
//        ObjectOutputStream out = new ObjectOutputStream(fout);
//
//        out.writeObject(marker);
//
//        out.close();

    }

    private void setLogin() {
        // Login listener
        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onLogin() {
                //getFriendsList();
                Toast.makeText(getApplication(), "Logged in", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotAcceptingPermissions(Permission.Type type) {
                Toast.makeText(getApplication(), String.format("You didn't accept %s permissions", type.name()), Toast.LENGTH_SHORT).show();
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
        mSimpleFacebook.login(onLoginListener);
    }
}
