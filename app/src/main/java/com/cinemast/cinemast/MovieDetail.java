package com.cinemast.cinemast;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import Utilities.CirclePageIndicator;
import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.ImageSlideAdapter;
import Utilities.ImagesParser;
import Utilities.MovieDetailsBean;
import Utilities.MovieDetailsParser;
import Utilities.PageIndicator;

public class MovieDetail extends FragmentActivity implements FetchFromServerUser {

    View view;
    TextView genre;
    TextView movieName;
    TextView year;
    ExpandableTextView overview;
    ImageView poster;
    TextView censor;
    TextView duration;
    TextView releaseDate;
    TextView tagline;
    String movieId;
    ViewPager mViewPager;
    PageIndicator mIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        genre = (TextView) findViewById(R.id.genre);
        movieName = (TextView) findViewById(R.id.movie_name);
        year = (TextView) findViewById(R.id.movie_year);
        overview = (ExpandableTextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.poster);
        censor = (TextView) findViewById(R.id.censor);
        duration = (TextView) findViewById(R.id.duration);
        releaseDate = (TextView) findViewById(R.id.release);
        tagline = (TextView) findViewById(R.id.tagline);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        mIndicator.setOnPageChangeListener(new PageChangeListener());
        mViewPager.setOnPageChangeListener(new PageChangeListener());

        Intent intent = getIntent();
        movieId = intent.getStringExtra("ID");

        if(movieId != null) {
            Log.d("MovieDetail", "http://api.themoviedb.org/3/movie/" + movieId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
            new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/movie/" + movieId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
            new FetchFromServerTask(this, 1).execute("http://api.themoviedb.org/3/movie/" + movieId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        }

        GenericFragment similarMovies = new GenericFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TYPE", "Similar Movies");
        bundle.putString("URL", "https://api.themoviedb.org/3/movie/" + movieId + "/similar?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        similarMovies.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.similar, similarMovies).commit();
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {

        if(id == 0) {
            ImagesParser parser = new ImagesParser(string);
            List<String> images = parser.getImages();
            mViewPager.setAdapter(new ImageSlideAdapter(this, images));
            mIndicator.setViewPager(mViewPager);
        }else if(id == 1) {
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
            TextView txt = (TextView) overview.findViewById(R.id.expandable_text);
            txt.setTextColor(Color.parseColor("#EEEEEE"));
            overview.setText(detailsBean.getOverview());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(poster);
            censor.setText(detailsBean.isAdult() ? "A" : "UA");
            duration.setText(detailsBean.getRuntime() / 60 + " hrs " + detailsBean.getRuntime() % 60 + " mins");
            releaseDate.setText(detailsBean.getRelease_date());
            tagline.setText(detailsBean.getTagline());

            Fragment movieVideoFragment = new MovieVideoFragment();
            Bundle data = new Bundle();
            data.putString("ID", movieId);
            movieVideoFragment.setArguments(data);
            getFragmentManager().beginTransaction().replace(R.id.video_frames, movieVideoFragment).commit();

            Fragment castingFragment = new CastingFragment();
            castingFragment.setArguments(data);
            getFragmentManager().beginTransaction().replace(R.id.casting, castingFragment).commit();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }
}
