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

        String todaysURL = "https://api.themoviedb.org/3/tv/airing_today?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String onAirURL = "https://api.themoviedb.org/3/tv/on_the_air?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String topURL = "https://api.themoviedb.org/3/tv/top_rated?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String popularURL = "https://api.themoviedb.org/3/tv/popular?api_key=0d9b1f55e11c548f66e11f78a7f38357";

        Fragment todaysShowFragment = new GenericFragment();
        Bundle todaysData = new Bundle();
        todaysData.putString("URL", todaysURL);
        todaysData.putString("HEADING", "Today's Show");
        todaysData.putString("TYPE", "SERIAL");
        todaysShowFragment.setArguments(todaysData);
        getChildFragmentManager().beginTransaction().replace(R.id.today_shows, todaysShowFragment).commit();

        Fragment onAirShowFragment = new GenericFragment();
        Bundle onAirData = new Bundle();
        onAirData.putString("URL", onAirURL);
        onAirData.putString("HEADING", "On Air Shows");
        onAirData.putString("TYPE", "SERIAL");
        onAirShowFragment.setArguments(onAirData);
        getChildFragmentManager().beginTransaction().replace(R.id.on_air, onAirShowFragment).commit();

        Fragment topShowFragment = new GenericFragment();
        Bundle topShowData = new Bundle();
        topShowData.putString("URL", topURL);
        topShowData.putString("HEADING", "Top Shows");
        topShowData.putString("TYPE", "SERIAL");
        topShowFragment.setArguments(topShowData);
        getChildFragmentManager().beginTransaction().replace(R.id.top_shows, topShowFragment).commit();

        Fragment popularShowsFragment = new GenericFragment();
        Bundle popularData = new Bundle();
        popularData.putString("URL", popularURL);
        popularData.putString("HEADING", "Popular TV Shows");
        popularData.putString("TYPE", "SERIAL");
        popularShowsFragment.setArguments(popularData);
        getChildFragmentManager().beginTransaction().replace(R.id.popular_shows, popularShowsFragment).commit();
        return view;
    }
}
