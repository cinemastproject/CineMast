package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.MovieVideoParser;
import Utilities.MovieVideosBean;


public class MovieVideoFragment extends Fragment implements FetchFromServerUser {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_video, container, false);
        Bundle data = getArguments();
        int id = data.getInt("ID");
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/movie/"+ id +"/videos?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        Log.d("Video URL :","https://api.themoviedb.org/3/movie/"+ id +"/videos?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        MovieVideoParser parser = new MovieVideoParser(string);
        try {
            List<MovieVideosBean> movieVideosList = parser.getMovieVideoList();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.video_frames);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            MovieVideoAdapter adapter = new MovieVideoAdapter(getActivity(), movieVideosList);
            recyclerView.setAdapter(adapter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
