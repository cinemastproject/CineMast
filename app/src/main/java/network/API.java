package network;

import com.dev.infinity.showtime.modals.CombinedCastDetail;
import com.dev.infinity.showtime.modals.EpisodeDetailsBean;
import com.dev.infinity.showtime.modals.MovieDetailsBean;
import com.dev.infinity.showtime.modals.MovieVideosBean;
import com.dev.infinity.showtime.modals.MoviesContract;
import com.dev.infinity.showtime.modals.PeopleResults;
import com.dev.infinity.showtime.modals.ProfileDetailBean;
import com.dev.infinity.showtime.modals.ResultsContract;
import com.dev.infinity.showtime.modals.TVShowDetailBean;
import com.dev.infinity.showtime.utils.Constants;
import com.dev.infinity.showtime.modals.ImagesBean;
import com.dev.infinity.showtime.modals.PersonImagesBean;
import com.dev.infinity.showtime.modals.SearchResultsBean;
import com.dev.infinity.showtime.modals.SeasonDetailsBean;
import com.dev.infinity.showtime.modals.TVContract;
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

    @GET("{path}/recommendations?api_key=" + Constants.API_KEY)
    Call<ResultsContract> getRecommendedShows(@Path("path") String path);

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

    @GET("{tv_id}/season/{season_number}/videos?api_key=" + Constants.API_KEY)
    Call<MovieVideosBean> getSeasonVideos(@Path("tv_id") String tvId, @Path("season_number") String seasonId);

    @GET("{tv_id}/season/{season_number}/episode/{episode_number}/videos?api_key=" + Constants.API_KEY)
    Call<MovieVideosBean> getSeasonEpisodeVideos(@Path("tv_id") String tvId, @Path("season_number") String seasonId, @Path("episode_number") String episodeNumber);

    @GET("{tv_id}/season/{season_number}/episode/{episode_number}?api_key=" + Constants.API_KEY)
    Call<EpisodeDetailsBean> getEpisode(@Path("tv_id") String tvId, @Path("season_number") String seasonId, @Path("episode_number") String episodeNumber);
}