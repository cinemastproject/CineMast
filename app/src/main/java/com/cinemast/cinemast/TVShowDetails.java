package com.cinemast.cinemast;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Utilities.CombinedCastDetail;
import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.GenreDetail;
import Utilities.ImagesParser;
import Utilities.MovieDetailsBean;
import Utilities.MovieDetailsParser;
import Utilities.MoviesContract;
import Utilities.RecyclerItemClickListener;
import Utilities.SeasonDetailsBean;
import Utilities.TVShowDetailBean;
import Utilities.TVShowDetailParser;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TVShowDetails extends Activity implements FetchFromServerUser, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, FullScreenImageGalleryAdapter.FullScreenImageLoader {

    TextView genre;
    TextView showName;
    ExpandableTextView overview;
    ImageView poster;
    ImageView q_start;
    ImageView q_end;
    private SliderLayout cover;
    TextView duration;
    TextView releaseDate;
    TextView tagline;
    RatingBar ratingBar;
    int tvId;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/tv/")
            .build();

    API api = retrofit.create(API.class);

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
        tagline = (TextView) findViewById(R.id.tagline);
        cover = (SliderLayout) findViewById(R.id.cover);
        q_start = (ImageView) findViewById(R.id.q_start);
        q_end = (ImageView) findViewById(R.id.q_end);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        Intent intent = getIntent();
        tvId = intent.getIntExtra("ID", 0);

        paletteColorType = PaletteColorType.VIBRANT;

        Log.d("MovieDetail", "http://api.themoviedb.org/3/movie/" + tvId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/tv/" + tvId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        new FetchFromServerTask(this, 1).execute("http://api.themoviedb.org/3/tv/" + tvId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");

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

        api.getSimilarMovies(String.valueOf(tvId)).enqueue(new Callback<MoviesContract>() {
            @Override
            public void onResponse(Call<MoviesContract> call, Response<MoviesContract> response) {
                final List<MovieDetailsBean> moviesList = response.body().getResults();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.similar_movies);
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
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        if(id == 0) {
            ImagesParser parser = new ImagesParser(string);
            List<String> images = parser.getImages();
            List<String> allImages = parser.getAllImages();

            final ArrayList<String> imagesURL = new ArrayList<>();
            for(int i = 0; i < allImages.size(); i++) {
                imagesURL.add(i, "https://image.tmdb.org/t/p/w500/" + allImages.get(i));
            }

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

        }else if(id == 1) {
            try {
                TVShowDetailParser parser = new TVShowDetailParser(string);
                TVShowDetailBean detailsBean = parser.getTvShowDetail();
                showName.setText(detailsBean.getName());
                StringBuilder genreStr = new StringBuilder();
                List<GenreDetail> genreDetailList = detailsBean.getGenreDetails();
                for (int i = 0; i < genreDetailList.size(); i++) {
                    genreStr.append(genreDetailList.get(i).getName());
                    if (i < genreDetailList.size() - 1)
                        genreStr.append(" | ");
                }
                genre.setText(genreStr.toString());
                overview.setText(detailsBean.getOverview());
                Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(poster);
                duration.setText(detailsBean.getEpisode_run_time()[0] / 60 + " hrs " + detailsBean.getEpisode_run_time()[0] % 60 + " mins");
                releaseDate.setText(detailsBean.getFirst_air_date());
                ratingBar.setRating((float) detailsBean.getPopularity() / 2);

                final List<TVShowDetailBean.Season> moviesList = detailsBean.getSeasons();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasons);
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
                GenericAdapter adapter = new GenericAdapter(TVShowDetails.this, moviesList);
                recyclerView.setAdapter(adapter);

                Fragment movieVideoFragment = new MovieVideoFragment();
                Bundle data = new Bundle();
                data.putString("TYPE", "tv");
                data.putString("ID", String.valueOf(tvId));
                movieVideoFragment.setArguments(data);
                getFragmentManager().beginTransaction().replace(R.id.video_frames, movieVideoFragment).commit();
            }catch (Exception ex) {

            }
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
                                    applyPalette(palette, bgLinearLayout);
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

    private void applyPalette(Palette palette, LinearLayout bgLinearLayout){
        int bgColor = getBackgroundColor(palette);
        if (bgColor != -1)
            bgLinearLayout.setBackgroundColor(bgColor);
    }

    private int getBackgroundColor(Palette palette) {
        int bgColor = -1;

        int vibrantColor = palette.getVibrantColor(0x000000);
        int lightVibrantColor = palette.getLightVibrantColor(0x000000);
        int darkVibrantColor = palette.getDarkVibrantColor(0x000000);

        int mutedColor = palette.getMutedColor(0x000000);
        int lightMutedColor = palette.getLightMutedColor(0x000000);
        int darkMutedColor = palette.getDarkMutedColor(0x000000);

        if (paletteColorType != null) {
            switch (paletteColorType) {
                case VIBRANT:
                    if (vibrantColor != 0) { // primary option
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) { // fallback options
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case LIGHT_VIBRANT:
                    if (lightVibrantColor != 0) { // primary option
                        bgColor = lightVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case DARK_VIBRANT:
                    if (darkVibrantColor != 0) { // primary option
                        bgColor = darkVibrantColor;
                    } else if (vibrantColor != 0) { // fallback options
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (mutedColor != 0) {
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    }
                    break;
                case MUTED:
                    if (mutedColor != 0) { // primary option
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) { // fallback options
                        bgColor = lightMutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case LIGHT_MUTED:
                    if (lightMutedColor != 0) { // primary option
                        bgColor = lightMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (darkMutedColor != 0) {
                        bgColor = darkMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                case DARK_MUTED:
                    if (darkMutedColor != 0) { // primary option
                        bgColor = darkMutedColor;
                    } else if (mutedColor != 0) { // fallback options
                        bgColor = mutedColor;
                    } else if (lightMutedColor != 0) {
                        bgColor = lightMutedColor;
                    } else if (vibrantColor != 0) {
                        bgColor = vibrantColor;
                    } else if (lightVibrantColor != 0) {
                        bgColor = lightVibrantColor;
                    } else if (darkVibrantColor != 0) {
                        bgColor = darkVibrantColor;
                    }
                    break;
                default:
                    break;
            }
        }

        return bgColor;
    }
}