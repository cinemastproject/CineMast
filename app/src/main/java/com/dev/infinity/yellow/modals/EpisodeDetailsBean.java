package com.dev.infinity.yellow.modals;

import java.io.Serializable;
import java.util.List;

/**
 * Created by deepjyoti on 14/5/16.
 */
public class EpisodeDetailsBean implements Serializable {
    private String air_date;
    private List<CrewDetails> crewDetailsList;
    private int episode_number;
    private List<GuestStarsDetails> guestStarsDetailsList;
    private String name;
    private String overview;
    private String id;
    private String production_code;
    private int season_number;
    private String still_path;
    private double vote_average;
    private int vote_count;

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public List<CrewDetails> getCrewDetailsList() {
        return crewDetailsList;
    }

    public void setCrewDetailsList(List<CrewDetails> crewDetailsList) {
        this.crewDetailsList = crewDetailsList;
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

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
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

    public List<GuestStarsDetails> getGuestStarsDetailsList() {
        return guestStarsDetailsList;
    }

    public void setGuestStarsDetailsList(List<GuestStarsDetails> guestStarsDetailsList) {
        this.guestStarsDetailsList = guestStarsDetailsList;
    }
}
