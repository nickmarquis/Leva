

/**
 * Created by nick on 06/03/15.
 */

package com.leva.nick.leva;

import com.leva.nick.leva.common.view.SlidingTabLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TabFrameF extends Fragment {

    private final String[] TITLES = {"Caméra", "Carte", "Amis"};


    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    Fragment mMapFragment;
    Fragment mCamFragment;
    Fragment mFriendFragment;

    public static TabFrameF newInstance(ArrayList<SpotsMarker> markersArray) {

        TabFrameF fragment = new TabFrameF();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("myMarker", markersArray);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<SpotsMarker> markersArray = getArguments().getParcelableArrayList("myMarker");

        mCamFragment = CameraF.newInstance(TITLES[0]);
        mMapFragment = GoogleMapF.newInstance(TITLES[1], markersArray);
        mFriendFragment = GoogleMapF.newInstance(TITLES[2], markersArray); //temp

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_tab_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                int col;
                switch (position) {
                    case 0:
                        col =  Color.CYAN;
                        break;
                    case 1:
                        col = Color.BLUE;
                        break;
                    case 2:
                        col = Color.CYAN;
                        break;
                    default:
                        col = Color.BLUE;
                        break;
                }
                return col;
            }

            @Override
            public int getDividerColor(int position) {
                int col;
                switch (position) {
                    case 0:
                        col =  Color.GRAY;
                        break;
                    case 1:
                        col = Color.GRAY;
                        break;
                    case 2:
                        col = Color.GRAY;
                        break;
                    default:
                        col = Color.GRAY;
                        break;

                }
                return col;
            }
        });
        mViewPager.setCurrentItem(1);
    }

    class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

        FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = mCamFragment;
                    break;
                case 1:
                    fragment = mMapFragment;
                    break;
                case 2:
                    fragment = mFriendFragment;
                    break;
                default:
                    fragment = mMapFragment;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) { return TITLES[position];  }
    }

}