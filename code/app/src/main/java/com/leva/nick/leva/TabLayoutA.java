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

import com.facebook.SessionDefaultAudience;
import com.leva.nick.leva.openingHours.ItemOpeningHours;
import com.leva.nick.leva.promotions.ItemPromotion;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.listeners.OnLoginListener;


public class TabLayoutA extends SampleActivityBase {

    public static final String TAG = "TabLayout";
    private static final String APP_ID = "371005393088854";
    private static final String APP_NAMESPACE = "leva_name";

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

        ArrayList<ItemOpeningHours> itemOpeningHoursArrayList = new ArrayList<>();

        itemOpeningHoursArrayList.add(0, new ItemOpeningHours("Lundi", "16", "23"));
        itemOpeningHoursArrayList.add(1, new ItemOpeningHours("Mardi", "16", "23"));
        itemOpeningHoursArrayList.add(2, new ItemOpeningHours("Mercredi", "16", "23"));
        itemOpeningHoursArrayList.add(3, new ItemOpeningHours("Jeudi", "16", "23"));
        itemOpeningHoursArrayList.add(4, new ItemOpeningHours("Vendredi", "16", "23"));
        itemOpeningHoursArrayList.add(5, new ItemOpeningHours("Samedi", "16", "23"));
        itemOpeningHoursArrayList.add(6, new ItemOpeningHours("Dimanche", "16", "23"));

        ArrayList<ItemPromotion> itemPromotionsArrayList = new ArrayList<>();

        itemPromotionsArrayList.add(0, new ItemPromotion("Lundi", "Lundis Woks", "5$ de rabais sur tous les woks. Consommation sur place. Taxes en sus.", "17", "23", null));
        itemPromotionsArrayList.add(1, new ItemPromotion("Mardi", "Mardis Burgers", "5$ de rabais sur tous les woks. Consommation sur place. Taxes en sus.", "17", "23", null));
        itemPromotionsArrayList.add(2, new ItemPromotion("Mercredi", "Spéciaux 5 à 8", "5$ de rabais sur tous les woks. Consommation sur place. Taxes en sus.", "17", "20", null));
        itemPromotionsArrayList.add(3, new ItemPromotion("Jeudi", "Spéciaux 5 à 8", "Rabais sur Alexander Keith's (blanche, blonde, rousse)", "17", "20", null));
        itemPromotionsArrayList.add(4, new ItemPromotion("Vendredi", "Spéciaux 5 à 8", "Rabais sur Alexander Keith's (blanche, blonde, rousse)", "17", "20", null));
        itemPromotionsArrayList.add(5, new ItemPromotion("Samedi", "Spéciaux 5 à 8", "Rabais sur Alexander Keith's (blanche, blonde, rousse)", "17", "20", null));
        itemPromotionsArrayList.add(6, new ItemPromotion("Dimanche", "Spéciaux 5 à 8", "Rabais sur Alexander Keith's (blanche, blonde, rousse)", "17", "20", null));

        mMyMarkersArray.add(new SpotsMarker("Blaxton", "ic_house", new LatLng(46.80283, -71.22465), "ic_blaxton", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("4 foyers", "ic_house", new LatLng(47.027088, -71.383312), "ic_4foyers", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("Maurice", "ic_house", new LatLng(46.805820, -71.216991), "ic_maurice", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("Archibald", "ic_house", new LatLng(46.943319, -71.292171), "ic_archibald", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("Valcartier", "ic_house", new LatLng(47.018067, -71.473618), "ic_valcartier", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("Le Relais", "ic_house", new LatLng(46.940381, -71.299596), "ic_relais", itemOpeningHoursArrayList, itemPromotionsArrayList));
        mMyMarkersArray.add(new SpotsMarker("Université Laval", "ic_house", new LatLng(46.780672, -71.274227), "ic_laval", itemOpeningHoursArrayList, itemPromotionsArrayList));

    }

    private void setLogin() {
        // Login listener
        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onLogin() {
                Log.i("facebook", "You are logged in");
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
