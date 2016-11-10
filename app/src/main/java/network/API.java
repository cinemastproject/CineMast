package network;

import Utilities.CombinedCastDetail;
import Utilities.Constants;
import Utilities.MoviesContract;
import Utilities.PersonImagesBean;
import Utilities.TVContract;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getMovies(@Path("path") String path, @Path("type") String type, @Query("page") int page);

    @GET("{path}/similar?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getSimilarMovies(@Path("path") String path);

    @GET("{path}/videos?api_key=" + Constants.API_KEY)
    Call<MoviesContract> getVideos(@Path("path") String path);

    @GET("{path}/{type}?api_key=" + Constants.API_KEY)
    Call<TVContract> getTVShows(@Path("path") String path, @Path("type") String type, @Query("page") int page);

    @GET("{path}/combined_credits?api_key=" + Constants.API_KEY)
    Call<CombinedCastDetail> getCombinedCredits(@Path("path") String path);

    @GET("{path}/credits?api_key=" + Constants.API_KEY)
    Call<CombinedCastDetail> getCasting(@Path("path") String path);

    @GET("{path}/images?api_key=" + Constants.API_KEY)
    Call<PersonImagesBean> getPersonImages(@Path("path") String path);
}
