package com.cinemast.cinemast;

public interface FetchFromServerUser {
    void onPreFetch();
    void onFetchCompletion(String string, int id);
}