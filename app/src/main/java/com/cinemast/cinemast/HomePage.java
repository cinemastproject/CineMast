package com.cinemast.cinemast;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class HomePage extends AppCompatActivity implements FetchFromServerUser{

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/movie/upcoming?api_key=YOUR_KEY");
    }

    @Override
    public void onPreFetch() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Details");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onFetchCompletion(String string, int id) {
        if(progressDialog != null)
            progressDialog.dismiss();
        PopularMovieParser parser = new PopularMovieParser(string);
        List<PopularMovieBean> popularMoviesList = parser.getMoviesList();
        GridView moviesGrid = (GridView)findViewById(R.id.movies);
        MoviesAdapter adapter = new MoviesAdapter(this, popularMoviesList);
        moviesGrid.setAdapter(adapter);
    }
}