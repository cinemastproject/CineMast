package com.cinemast.cinemast;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import Utilities.CombinedCastDetail;
import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.ImagesParser;
import Utilities.MovieDetailsBean;
import Utilities.MovieDetailsParser;
import Utilities.MoviesContract;
import Utilities.RecyclerItemClickListener;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetail extends FragmentActivity implements FetchFromServerUser, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    TextView genre;
    TextView movieName;
    ExpandableTextView overview;
    ImageView poster;
    ImageView q_start;
    ImageView q_end;
    private SliderLayout cover;
    TextView duration;
    TextView releaseDate;
    TextView tagline;
    RatingBar ratingBar;
    String movieId;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/movie/")
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        genre = (TextView) findViewById(R.id.genre);
        movieName = (TextView) findViewById(R.id.movie_name);
        overview = (ExpandableTextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.poster);
        duration = (TextView) findViewById(R.id.duration);
        releaseDate = (TextView) findViewById(R.id.release);
        tagline = (TextView) findViewById(R.id.tagline);
        cover = (SliderLayout) findViewById(R.id.cover);
        q_start = (ImageView) findViewById(R.id.q_start);
        q_end = (ImageView) findViewById(R.id.q_end);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        Intent intent = getIntent();
        movieId = intent.getStringExtra("ID");

        if(movieId != null) {
            Log.d("MovieDetail", "http://api.themoviedb.org/3/movie/" + movieId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
            new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/movie/" + movieId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
            new FetchFromServerTask(this, 1).execute("http://api.themoviedb.org/3/movie/" + movieId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        }

        api.getCasting(movieId).enqueue(new Callback<CombinedCastDetail>() {
            @Override
            public void onResponse(Call<CombinedCastDetail> call, Response<CombinedCastDetail> response) {
                final List<CombinedCastDetail.CastDetail> castList = response.body().getCast();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.casts);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetail.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MovieDetail.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(MovieDetail.this, Profile.class);
                        detailActivity.putExtra("ID", castList.get(position).getId());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                CastingAdapter adapter = new CastingAdapter(MovieDetail.this, castList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CombinedCastDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });

        api.getSimilarMovies(movieId).enqueue(new Callback<MoviesContract>() {
            @Override
            public void onResponse(Call<MoviesContract> call, Response<MoviesContract> response) {
                final List<MovieDetailsBean> moviesList = response.body().getResults();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.similar_movies);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetail.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MovieDetail.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(MovieDetail.this, MovieDetail.class);
                        detailActivity.putExtra("ID", moviesList.get(position).getId());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                MoviesAdapter adapter = new MoviesAdapter(MovieDetail.this, moviesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesContract> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {

        if(id == 0) {
            ImagesParser parser = new ImagesParser(string);
            List<String> images = parser.getImages();
            for(int i = 0; i < images.size(); i++){
                TextSliderView textSliderView = new TextSliderView(this);
                textSliderView
                    .image("https://image.tmdb.org/t/p/w500/" + images.get(i))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra","" + i);

                cover.addSlider(textSliderView);
            }
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
            overview.setText(detailsBean.getOverview());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(poster);
            duration.setText(detailsBean.getRuntime() / 60 + " hrs " + detailsBean.getRuntime() % 60 + " mins");
            releaseDate.setText(detailsBean.getRelease_date());
            ratingBar.setRating((float)detailsBean.getPopularity() / 2);
            if(detailsBean.getTagline() == null || detailsBean.getTagline().trim().length() == 0) {
                tagline.setVisibility(View.INVISIBLE);
                q_start.setVisibility(View.INVISIBLE);
                q_end.setVisibility(View.INVISIBLE);
            }else
                tagline.setText(detailsBean.getTagline());

            Fragment movieVideoFragment = new MovieVideoFragment();
            Bundle data = new Bundle();
            data.putString("TYPE", "movie");
            data.putString("ID", movieId);
            movieVideoFragment.setArguments(data);
            getFragmentManager().beginTransaction().replace(R.id.video_frames, movieVideoFragment).commit();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
