package com.cinemast.cinemast.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinemast.cinemast.R;

import java.util.List;

import com.cinemast.cinemast.utils.FetchFromServerTask;
import com.cinemast.cinemast.utils.FetchFromServerUser;
import com.cinemast.cinemast.utils.MovieVideoParser;
import com.cinemast.cinemast.modals.MovieVideosBean;
import com.cinemast.cinemast.utils.RecyclerItemClickListener;


public class MovieVideoFragment extends Fragment implements FetchFromServerUser {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movie_video, container, false);
        Bundle data = getArguments();
        String id = data.getString("ID");
        String type = data.getString("TYPE");
        if(type != null && type.equals("movie"))
            new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/movie/"+ id +"/videos?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        else if(type != null && type.equals("tv"))
            new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/tv/"+ id +"/videos?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        MovieVideoParser parser = new MovieVideoParser(string);
        try {
            final List<MovieVideosBean> movieVideosList = parser.getMovieVideoList();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.video_frames);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            MovieVideoAdapter adapter = new MovieVideoAdapter(getActivity(), movieVideosList);
            recyclerView.setAdapter(adapter);

            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideosList.get(position).getKey())));
                }
            }));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
