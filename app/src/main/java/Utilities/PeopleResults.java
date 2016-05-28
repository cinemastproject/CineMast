package Utilities;

import java.util.ArrayList;
import java.util.List;

public class PeopleResults {
    private int page;
    private int totalResults;
    private int totalPages;
    private List<PeopleBean> peopleList;

    public PeopleResults(){
        this.peopleList = new ArrayList<>();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<PeopleBean> getMoviesList() {
        return peopleList;
    }

    public void setMoviesList(List<PeopleBean> moviesList) {
        this.peopleList = moviesList;
    }
}
