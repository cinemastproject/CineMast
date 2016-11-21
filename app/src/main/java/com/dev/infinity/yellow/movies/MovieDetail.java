package com.dev.infinity.yellow.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.infinity.yellow.R;
import com.dev.infinity.yellow.common.MovieVideoAdapter;
import com.dev.infinity.yellow.modals.GenreDetail;
import com.dev.infinity.yellow.modals.MovieVideosBean;
import com.dev.infinity.yellow.person.CastingAdapter;
import com.dev.infinity.yellow.person.Profile;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.dev.infinity.yellow.modals.CombinedCastDetail;
import com.dev.infinity.yellow.modals.ImagesBean;
import com.dev.infinity.yellow.modals.MovieDetailsBean;
import com.dev.infinity.yellow.modals.MoviesContract;
import com.dev.infinity.yellow.utils.RecyclerItemClickListener;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetail extends FragmentActivity {

    private TextView genre;
    private TextView movieName;
    private ExpandableTextView overview;
    private ImageView poster;
    private ImageView q_start;
    private ImageView q_end;
    private SliderLayout cover;
    private TextView duration;
    private TextView releaseDate;
    private TextView tagline;
    private RatingBar ratingBar;

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/movie/")
            .build();

    private API api = retrofit.create(API.class);

    private Retrofit retrofitVideo = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/")
            .build();

    private API apiVideo = retrofitVideo.create(API.class);

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
        String movieId = intent.getStringExtra("ID");

        if(movieId != null) {

            api.getMovieDetails(movieId).enqueue(new Callback<MovieDetailsBean>() {
                @Override
                public void onResponse(Call<MovieDetailsBean> call, Response<MovieDetailsBean> response) {
                    MovieDetailsBean bean = response.body();
                    movieName.setText(bean.getTitle());
                    StringBuilder genreStr = new StringBuilder();
                    List<GenreDetail> genreArray = bean.getGenres();
                    for (int i = 0; i < genreArray.size(); i++) {
                        genreStr.append(genreArray.get(i).getName());
                        if (i < genreArray.size() - 1)
                            genreStr.append(" | ");
                    }
                    genre.setText(genreStr.toString());
                    overview.setText(bean.getOverview());
                    Picasso.with(MovieDetail.this).load("https://image.tmdb.org/t/p/w320/" + bean.getPoster_path())
                            .error(R.drawable.notfound)
                            .placeholder(R.drawable.movie)
                            .into(poster);
                    duration.setText(bean.getRuntime() / 60 + " hrs " + bean.getRuntime() % 60 + " mins");
                    releaseDate.setText(bean.getRelease_date());
                    ratingBar.setRating((float)bean.getPopularity() / 2);
                    if(bean.getTagline() == null || bean.getTagline().trim().length() == 0) {
                        tagline.setVisibility(View.INVISIBLE);
                        q_start.setVisibility(View.INVISIBLE);
                        q_end.setVisibility(View.INVISIBLE);
                    }else
                        tagline.setText(bean.getTagline());
                }

                @Override
                public void onFailure(Call<MovieDetailsBean> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            apiVideo.getVideos("movie", movieId).enqueue(new Callback<MovieVideosBean>() {
                @Override
                public void onResponse(Call<MovieVideosBean> call, Response<MovieVideosBean> response) {
                    final List<MovieVideosBean.Result> movieVideosList = response.body().getResults();
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.video_frames);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MovieDetail.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    MovieVideoAdapter adapter = new MovieVideoAdapter(MovieDetail.this, movieVideosList);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MovieDetail.this, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideosList.get(position).getKey())));
                        }
                    }));
                }

                @Override
                public void onFailure(Call<MovieVideosBean> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            api.getImages(movieId).enqueue(new Callback<ImagesBean>() {
                @Override
                public void onResponse(Call<ImagesBean> call, Response<ImagesBean> response) {
                    List<ImagesBean.Profile> posters = response.body().getPosters();
                    List<ImagesBean.Profile> backdrops = response.body().getBackdrops();
                    List<String> images = new ArrayList<>();

                    for(int j = 0; j < posters.size(); j++) {
                        if(j >= 5)
                            break;
                        String path = posters.get(j).getFile_path();
                        images.add(path);
                    }
                    for(int i = 0; i < backdrops.size(); i++) {
                        if(i >= 5)
                            break;
                        String path = posters.get(i).getFile_path();
                        images.add(path);
                    }
                    for(int i = 0; i < images.size(); i++){
                        TextSliderView textSliderView = new TextSliderView(MovieDetail.this);
                        textSliderView
                                .image("https://image.tmdb.org/t/p/w500/" + images.get(i))
                                .setScaleType(BaseSliderView.ScaleType.Fit);

                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle().putString("extra","" + i);

                        cover.addSlider(textSliderView);
                    }
                }

                @Override
                public void onFailure(Call<ImagesBean> call, Throwable t) {

                }
            });

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
        }else {
            Toast.makeText(this, "Movie not found", Toast.LENGTH_LONG).show();
        }
    }
}
