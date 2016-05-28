package Utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeasonDetailsBean implements Serializable {
    private String air_date;
    private String name;
    private String overview;
    private String id;
    private String poster_path;
    private String season_number;
    private List<Episode> episodes;


    public SeasonDetailsBean(){
        episodes = new ArrayList<>();

    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public String getSeason_number() {
        return season_number;
    }

    public void setSeason_number(String season_number) {
        this.season_number = season_number;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    class Episode implements Serializable{
        private String air_date;
        private int episode_number;
        private String name;
        private String overview;
        private String id;
        private String production_code;
        private String season_number;
        private String still_path;
        private double vote_average;
        private int vote_count;
        private List<CrewDetails> crewDetails;
        private List<GuestStarsDetails> guestStarsDetails;

        public Episode(){
            crewDetails = new ArrayList<>();
            guestStarsDetails = new ArrayList<>();
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getEpisode_number() {
            return episode_number;
        }

        public void setEpisode_number(int episode_number) {
            this.episode_number = episode_number;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduction_code() {
            return production_code;
        }

        public void setProduction_code(String production_code) {
            this.production_code = production_code;
        }

        public String getStill_path() {
            return still_path;
        }

        public void setStill_path(String still_path) {
            this.still_path = still_path;
        }

        public String getSeason_number() {
            return season_number;
        }

        public void setSeason_number(String season_number) {
            this.season_number = season_number;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public List<CrewDetails> getCrewDetails() {
            return crewDetails;
        }

        public void setCrewDetails(List<CrewDetails> crewDetails) {
            this.crewDetails = crewDetails;
        }

        public List<GuestStarsDetails> getGuestStarsDetails() {
            return guestStarsDetails;
        }

        public void setGuestStarsDetails(List<GuestStarsDetails> guestStarsDetails) {
            this.guestStarsDetails = guestStarsDetails;
        }
    }


}
