package com.cinemast.cinemast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.MovieDetailsBean;
import Utilities.MovieDetailsParser;

public class MovieDetails extends AppCompatActivity implements FetchFromServerUser {
    View view;
    TextView genre;
    TextView movieName;
    TextView year;
    TextView overview;
    ImageView poster;
    TextView censor;
    TextView duration;
    TextView releaseDate;
    TextView tagline;
    ImageView movieImage;
    int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        genre = (TextView) findViewById(R.id.genre);
        movieName = (TextView) findViewById(R.id.movie_name);
        year = (TextView) findViewById(R.id.movie_year);
        overview = (TextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.poster);
        censor = (TextView) findViewById(R.id.censor);
        duration = (TextView) findViewById(R.id.duration);
        releaseDate = (TextView) findViewById(R.id.release);
        tagline = (TextView) findViewById(R.id.tagline);
        movieImage = (ImageView) findViewById(R.id.movie_images);

        Intent intent = getIntent();
        movieId = intent.getIntExtra("ID", -1);

        if(movieId != -1) {

            new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/movie/" + movieId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        }

    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {

            MovieDetailsParser parser = new MovieDetailsParser(string);
            MovieDetailsBean detailsBean = parser.getMovieDetails();
            movieName.setText(detailsBean.getTitle());
            StringBuilder genreStr = new StringBuilder();
            String[] genreArray = detailsBean.getGenres();
            for (int i = 0; i < genreArray.length; i++) {
                genreStr.append(genreArray[i]);
                if (i < genreArray.length - 1)
                    genreStr.append(" | ");
            }
            genre.setText(genreStr.toString());
            year.setText("(" + String.valueOf(detailsBean.getRelease_date().split("-")[0]) + ")");
            overview.setText(detailsBean.getOverview());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)

                    .into(poster);
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(movieImage);
            censor.setText(detailsBean.isAdult() ? "A" : "UA");
            duration.setText(detailsBean.getRuntime() / 60 + " hrs " + detailsBean.getRuntime() % 60 + " mins");
            releaseDate.setText(detailsBean.getRelease_date());
            tagline.setText(detailsBean.getTagline());

        Fragment movieVideoFragment = new MovieVideoFragment();
        Bundle data = new Bundle();
        data.putInt("ID", movieId);
        movieVideoFragment.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.video_frames, movieVideoFragment).commit();

        }



}