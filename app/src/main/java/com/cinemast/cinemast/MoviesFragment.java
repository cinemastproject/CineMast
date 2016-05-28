package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoviesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_layout, container, false);

        String inTheaterURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String topRatedURL = "https://api.themoviedb.org/3/movie/top_rated?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String upComingURL = "https://api.themoviedb.org/3/movie/upcoming?api_key=0d9b1f55e11c548f66e11f78a7f38357";
        String popularURL = "https://api.themoviedb.org/3/movie/popular?api_key=0d9b1f55e11c548f66e11f78a7f38357";

        Fragment inTheaterFragment = new GenericFragment();
        Bundle inTheaterData = new Bundle();
        inTheaterData.putString("URL", inTheaterURL);
        inTheaterData.putString("HEADING", "Now In Theater");
        inTheaterData.putString("TYPE", "MOVIES");
        inTheaterFragment.setArguments(inTheaterData);
        getChildFragmentManager().beginTransaction().replace(R.id.in_theater, inTheaterFragment).commit();

        Fragment topFragment = new GenericFragment();
        Bundle topRatedData = new Bundle();
        topRatedData.putString("URL", topRatedURL);
        topRatedData.putString("HEADING", "Top Rated Movies");
        topRatedData.putString("TYPE", "MOVIES");
        topFragment.setArguments(topRatedData);
        getChildFragmentManager().beginTransaction().replace(R.id.top_rated, topFragment).commit();

        Fragment upComingFragment = new GenericFragment();
        Bundle upComingData = new Bundle();
        upComingData.putString("URL", upComingURL);
        upComingData.putString("HEADING", "Upcoming Movies");
        upComingData.putString("TYPE", "MOVIES");
        upComingFragment.setArguments(upComingData);
        getChildFragmentManager().beginTransaction().replace(R.id.up_coming, upComingFragment).commit();

        Fragment popularMoviesFragment = new GenericFragment();
        Bundle popularData = new Bundle();
        popularData.putString("URL", popularURL);
        popularData.putString("HEADING", "Popular Movies");
        popularData.putString("TYPE", "MOVIES");
        popularMoviesFragment.setArguments(popularData);
        getChildFragmentManager().beginTransaction().replace(R.id.popular_movies, popularMoviesFragment).commit();

        return view;
    }
}