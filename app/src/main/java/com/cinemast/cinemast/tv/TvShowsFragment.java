package com.cinemast.cinemast.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinemast.cinemast.common.GenericFragment;
import com.cinemast.cinemast.R;

public class TvShowsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_shows_layout, container, false);

        Fragment todaysShowFragment = new GenericFragment();
        Bundle todaysData = new Bundle();
        todaysData.putString("HEADING", "Today's Show");
        todaysData.putString("TYPE", "SERIAL");
        todaysData.putString("CLASS", "airing_today");
        todaysShowFragment.setArguments(todaysData);
        getChildFragmentManager().beginTransaction().replace(R.id.today_shows, todaysShowFragment).commit();

        Fragment onAirShowFragment = new GenericFragment();
        Bundle onAirData = new Bundle();
        onAirData.putString("HEADING", "On Air Shows");
        onAirData.putString("TYPE", "SERIAL");
        onAirData.putString("CLASS", "on_the_air");
        onAirShowFragment.setArguments(onAirData);
        getChildFragmentManager().beginTransaction().replace(R.id.on_air, onAirShowFragment).commit();

        Fragment topShowFragment = new GenericFragment();
        Bundle topShowData = new Bundle();
        topShowData.putString("HEADING", "Top Shows");
        topShowData.putString("TYPE", "SERIAL");
        topShowData.putString("CLASS", "top_rated");
        topShowFragment.setArguments(topShowData);
        getChildFragmentManager().beginTransaction().replace(R.id.top_shows, topShowFragment).commit();

        Fragment popularShowsFragment = new GenericFragment();
        Bundle popularData = new Bundle();
        popularData.putString("HEADING", "Popular TV Shows");
        popularData.putString("TYPE", "SERIAL");
        popularData.putString("CLASS", "popular");
        popularShowsFragment.setArguments(popularData);
        getChildFragmentManager().beginTransaction().replace(R.id.popular_shows, popularShowsFragment).commit();
        return view;
    }
}
