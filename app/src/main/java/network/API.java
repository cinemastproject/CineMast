package network;

import com.cinemast.cinemast.modals.CombinedCastDetail;
import com.cinemast.cinemast.utils.Constants;
import com.cinemast.cinemast.modals.ImagesBean;
import com.cinemast.cinemast.modals.MoviesContract;
import com.cinemast.cinemast.modals.PersonImagesBean;
import com.cinemast.cinemast.modals.SearchResultsBean;
import com.cinemast.cinemast.modals.SeasonDetailsBean;
import com.cinemast.cinemast.modals.TVContract;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getMovies(@Path("path") String path, @Path("type") String type, @Query("page") int page);

    @GET("{path}/similar?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getSimilarMovies(@Path("path") String path);

    @GET("{path}/recommendations?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getRecommendations(@Path("path") String path);

    @GET("{path}/videos?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getVideos(@Path("path") String path);

    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<TVContract> getTVShows(@Path("path") String path, @Path("type") String type, @Query("page") int page);

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
}
