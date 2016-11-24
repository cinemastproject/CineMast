package com.dev.infinity.yellow.tv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dev.infinity.yellow.R;
import com.dev.infinity.yellow.common.MovieVideoAdapter;
import com.dev.infinity.yellow.modals.MovieVideosBean;
import com.dev.infinity.yellow.modals.MoviesContract;
import com.dev.infinity.yellow.modals.ResultsContract;
import com.dev.infinity.yellow.person.CastingAdapter;
import com.dev.infinity.yellow.common.GenericAdapter;
import com.dev.infinity.yellow.movies.MovieDetail;
import com.dev.infinity.yellow.movies.MoviesAdapter;
import com.dev.infinity.yellow.person.Profile;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dev.infinity.yellow.modals.CombinedCastDetail;
import com.dev.infinity.yellow.modals.GenreDetail;
import com.dev.infinity.yellow.modals.ImagesBean;
import com.dev.infinity.yellow.modals.MovieDetailsBean;
import com.dev.infinity.yellow.utils.RecyclerItemClickListener;
import com.dev.infinity.yellow.modals.TVShowDetailBean;
import com.dev.infinity.yellow.utils.Utils;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVShowDetails extends Activity implements FullScreenImageGalleryAdapter.FullScreenImageLoader {

    private TextView genre;
    private TextView showName;
    private ExpandableTextView overview;
    private ImageView poster;
    private SliderLayout cover;
    private TextView duration;
    private TextView releaseDate;
    private RatingBar ratingBar;
    private int tvId;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/tv/")
            .build();

    API api = retrofit.create(API.class);

    private Retrofit retrofitVideo = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/")
            .build();

    private API apiVideo = retrofitVideo.create(API.class);

    private PaletteColorType paletteColorType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_show_layout);
        genre = (TextView) findViewById(R.id.genre);
        showName = (TextView) findViewById(R.id.movie_name);
        overview = (ExpandableTextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.poster);
        duration = (TextView) findViewById(R.id.duration);
        releaseDate = (TextView) findViewById(R.id.release);
        cover = (SliderLayout) findViewById(R.id.cover);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        ImageView back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVShowDetails.this.finish();
            }
        });

        Intent intent = getIntent();
        tvId = intent.getIntExtra("ID", 0);

        paletteColorType = PaletteColorType.VIBRANT;

        api.getShowDetails(tvId).enqueue(new Callback<TVShowDetailBean>() {
            @Override
            public void onResponse(Call<TVShowDetailBean> call, Response<TVShowDetailBean> response) {
                TVShowDetailBean bean = response.body();
                showName.setText(bean.getName());
                StringBuilder genreStr = new StringBuilder();
                List<GenreDetail> genreDetailList = bean.getgenres();
                for (int i = 0; i < genreDetailList.size(); i++) {
                    genreStr.append(genreDetailList.get(i).getName());
                    if (i < genreDetailList.size() - 1)
                        genreStr.append(" | ");
                }
                genre.setText(genreStr.toString());
                overview.setText(bean.getOverview());
                Picasso.with(TVShowDetails.this).load("https://image.tmdb.org/t/p/w320/" + bean.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(poster);
                duration.setText(bean.getEpisode_run_time()[0] / 60 + " hrs " + bean.getEpisode_run_time()[0] % 60 + " mins");
                releaseDate.setText(bean.getFirst_air_date());
                ratingBar.setRating(bean.getPopularity() / 2);

                final List<TVShowDetailBean.Season> moviesList = bean.getSeasons();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasons);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TVShowDetails.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(TVShowDetails.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(TVShowDetails.this, SeasonActivity.class);
                        detailActivity.putExtra("ID", String.valueOf(tvId));
                        detailActivity.putExtra("SEASON_NUMBER", moviesList.get(position).getSeason_number());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                GenericAdapter adapter = new GenericAdapter(TVShowDetails.this, moviesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TVShowDetailBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

        apiVideo.getVideos("tv", String.valueOf(tvId)).enqueue(new Callback<MovieVideosBean>() {
            @Override
            public void onResponse(Call<MovieVideosBean> call, Response<MovieVideosBean> response) {
                final List<MovieVideosBean.Result> movieVideosList = response.body().getResults();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.video_frames);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TVShowDetails.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                MovieVideoAdapter adapter = new MovieVideoAdapter(TVShowDetails.this, movieVideosList);
                recyclerView.setAdapter(adapter);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(TVShowDetails.this, new RecyclerItemClickListener.OnItemClickListener() {
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

        api.getImages(String.valueOf(tvId)).enqueue(new Callback<ImagesBean>() {
            @Override
            public void onResponse(Call<ImagesBean> call, Response<ImagesBean> response) {
                List<ImagesBean.Profile> posters = response.body().getPosters();
                List<ImagesBean.Profile> backdrops = response.body().getBackdrops();
                List<String> images = new ArrayList<>();

                final ArrayList<String> imagesURL = new ArrayList<>();
                for(int i = 0; i < posters.size(); i++) {
                    imagesURL.add(i, "https://image.tmdb.org/t/p/w500/" + posters.get(i).getFile_path());
                }
                for(int i = 0; i < backdrops.size(); i++) {
                    imagesURL.add(i, "https://image.tmdb.org/t/p/w500/" + backdrops.get(i).getFile_path());
                }

                Collections.shuffle(imagesURL);

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
                    TextSliderView textSliderView = new TextSliderView(TVShowDetails.this);
                    textSliderView
                            .image("https://image.tmdb.org/t/p/w500/" + images.get(i))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra","" + i);

                    cover.addSlider(textSliderView);
                }

                ImageView gallery = (ImageView) findViewById(R.id.slideshow);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TVShowDetails.this, FullScreenImageGalleryActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, imagesURL);
                        bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, 0);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        FullScreenImageGalleryActivity.setFullScreenImageLoader(TVShowDetails.this);
                    }
                });
            }

            @Override
            public void onFailure(Call<ImagesBean> call, Throwable t) {

            }
        });

        api.getCasting(String.valueOf(tvId)).enqueue(new Callback<CombinedCastDetail>() {
            @Override
            public void onResponse(Call<CombinedCastDetail> call, Response<CombinedCastDetail> response) {
                final List<CombinedCastDetail.CastDetail> castList = response.body().getCast();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.casts);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TVShowDetails.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(TVShowDetails.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(TVShowDetails.this, Profile.class);
                        detailActivity.putExtra("ID", castList.get(position).getId());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                CastingAdapter adapter = new CastingAdapter(TVShowDetails.this, castList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CombinedCastDetail> call, Throwable t) {
                t.printStackTrace();
            }
        });

        api.getSimilarShows(String.valueOf(tvId)).enqueue(new Callback<ResultsContract>() {
            @Override
            public void onResponse(Call<ResultsContract> call, Response<ResultsContract> response) {
                final List<TVShowDetailBean> showsList = response.body().getResults();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.similar_movies);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TVShowDetails.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(TVShowDetails.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(TVShowDetails.this, TVShowDetails.class);
                        detailActivity.putExtra("ID", showsList.get(position).getId());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                GenericAdapter adapter = new GenericAdapter(TVShowDetails.this, showsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResultsContract> call, Throwable t) {
                t.printStackTrace();
            }
        });

        api.getRecommendations(String.valueOf(tvId)).enqueue(new Callback<MoviesContract>() {
            @Override
            public void onResponse(Call<MoviesContract> call, Response<MoviesContract> response) {
                final List<MovieDetailsBean> moviesList = response.body().getResults();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recommendations);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TVShowDetails.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(TVShowDetails.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent detailActivity = new Intent(TVShowDetails.this, MovieDetail.class);
                        detailActivity.putExtra("ID", moviesList.get(position).getId());
                        startActivity(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                MoviesAdapter adapter = new MoviesAdapter(TVShowDetails.this, moviesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesContract> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bgLinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    Utils.applyPalette(palette, paletteColorType, bgLinearLayout);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
    }
}