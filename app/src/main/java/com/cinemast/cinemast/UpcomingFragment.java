package com.cinemast.cinemast;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class UpcomingFragment extends Fragment implements FetchFromServerUser{
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcoming_movies_fragment_layout, container, false);
        new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/movie/upcoming?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        PopularMovieParser parser = new PopularMovieParser(string);
        List<PopularMovieBean> popularMoviesList = parser.getMoviesList();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.popularMovies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MoviesAdapter adapter = new MoviesAdapter(getActivity(), popularMoviesList);
        recyclerView.setAdapter(adapter);
    }
}
