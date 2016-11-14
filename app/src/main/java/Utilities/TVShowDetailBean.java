package Utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TVShowDetailBean implements Serializable {
    private String backdrop_path;
    private List<CreatedByDetail> createdBy;
    private int[] episode_run_time;
    private String first_air_date;
    private List<GenreDetail> genreDetails;
    private String homepage;
    private String id;
    private boolean in_production;
    private String[] languages;
    private String last_air_date;
    private String name;
    private List<NetworkDetail> networkDetails;
    private int number_of_episodes;
    private int number_of_seasons;
    private String[] origin_country;
    private String original_language;
    private String original_name;
    private String overview;
    private float popularity;
    private String poster_path;
    private List<ProductionCompanyDetail> companyDetails;
    private ArrayList<Season> seasons;
    private String status;
    private String type;
    private float vote_average;
    private int vote_count;

    public TVShowDetailBean(){
        createdBy = new ArrayList<>();
        genreDetails = new ArrayList<>();
        networkDetails = new ArrayList<>();
        companyDetails = new ArrayList<>();
        seasons = new ArrayList<>();
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<CreatedByDetail> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<CreatedByDetail> createdBy) {
        this.createdBy = createdBy;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public void setEpisode_run_time(int[] episode_run_time) {
        this.episode_run_time = episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<GenreDetail> getGenreDetails() {
        return genreDetails;
    }

    public void setGenreDetails(List<GenreDetail> genreDetails) {
        this.genreDetails = genreDetails;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NetworkDetail> getNetworkDetails() {
        return networkDetails;
    }

    public void setNetworkDetails(List<NetworkDetail> networkDetails) {
        this.networkDetails = networkDetails;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<ProductionCompanyDetail> getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(List<ProductionCompanyDetail> companyDetails) {
        this.companyDetails = companyDetails;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String[] origin_country) {
        this.origin_country = origin_country;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public class Season implements Serializable {
        private String air_date;
        private int episode_count;
        private String id;
        private String poster_path;
        private int season_number;

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }
    }

}

