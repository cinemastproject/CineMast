package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.MovieDetailsParser;

public class MovieDetails extends AppCompatActivity implements FetchFromServerUser {
    View view;
    TextView genre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        genre = (TextView) findViewById(R.id.genre)
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/movie/now_playing?api_key=0d9b1f55e11c548f66e11f78a7f38357");

    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        MovieDetailsParser = 
    }
}