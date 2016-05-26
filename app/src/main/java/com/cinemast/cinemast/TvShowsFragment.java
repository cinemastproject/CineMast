package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TvShowsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_shows_layout, container, false);

        Fragment todaysShowFragment = new TodaysShowFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.today_shows, todaysShowFragment).commit();

        Fragment onAirShowFragment = new OnAirShowFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.on_air, onAirShowFragment).commit();

        Fragment topShowFragment = new TopShowsFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.top_shows, topShowFragment).commit();

        Fragment popularShowsFragment = new PopularShowsFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.popular_shows, popularShowsFragment).commit();
        return view;
    }
}
