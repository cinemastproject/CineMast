package network;

import com.dev.infinity.yellow.modals.CombinedCastDetail;
import com.dev.infinity.yellow.modals.MovieDetailsBean;
import com.dev.infinity.yellow.modals.MovieVideosBean;
import com.dev.infinity.yellow.modals.MoviesContract;
import com.dev.infinity.yellow.modals.PeopleResults;
import com.dev.infinity.yellow.modals.ProfileDetailBean;
import com.dev.infinity.yellow.modals.ResultsContract;
import com.dev.infinity.yellow.modals.TVShowDetailBean;
import com.dev.infinity.yellow.utils.Constants;
import com.dev.infinity.yellow.modals.ImagesBean;
import com.dev.infinity.yellow.modals.PersonImagesBean;
import com.dev.infinity.yellow.modals.SearchResultsBean;
import com.dev.infinity.yellow.modals.SeasonDetailsBean;
import com.dev.infinity.yellow.modals.TVContract;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getMovies(@Path("path") String path, @Path("type") String type, @Query("page") int page);

    @GET("{id}?api_key=" + Constants.API_KEY)
    Call<MovieDetailsBean> getMovieDetails(@Path("id") String id);

    @GET("{path}/similar?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getSimilarMovies(@Path("path") String path);

    @GET("{path}/similar?api_key=" + Constants.API_KEY)
    Call<ResultsContract> getSimilarShows(@Path("path") String path);

    @GET("{path}/recommendations?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getRecommendations(@Path("path") String path);

    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<TVContract> getTVShows(@Path("path") String path, @Path("type") String type, @Query("page") int page);

    @GET("{id}?api_key=" + Constants.API_KEY)
    Call<TVShowDetailBean> getShowDetails(@Path("id") int id);

    @GET("{tv_id}/season/{season_number}?api_key=" + Constants.API_KEY)
    Call<SeasonDetailsBean> getSeason(@Path("tv_id") String id, @Path("season_number") String number);

    @GET("{path}/combined_credits?api_key=" + Constants.API_KEY)
    Call<CombinedCastDetail> getCombinedCredits(@Path("path") String path);

    @GET("{path}/credits?api_key=" + Constants.API_KEY)
    Call<CombinedCastDetail> getCasting(@Path("path") String path);

    @GET("{path}/images?api_key=" + Constants.API_KEY)
    Call<PersonImagesBean> getPersonImages(@Path("path") String path);

    @GET("multi?api_key=" + Constants.API_KEY)
    Call<SearchResultsBean> getMultiSearchResult(@Query("query") String query);

    @GET("{path}/images?api_key=" + Constants.API_KEY)
    Call<ImagesBean> getImages(@Path("path") String path);

    @GET("{path}?api_key=" + Constants.API_KEY)
    Call<PeopleResults> getPopularPeople(@Path("path") String path, @Query("page") int page);

    @GET("{id}?api_key=" + Constants.API_KEY)
    Call<ProfileDetailBean> getProfile(@Path("id") int id);

    @GET("{path}/{id}/videos?api_key=" + Constants.API_KEY)
    Call<MovieVideosBean> getVideos(@Path("path") String path, @Path("id") String id);
}