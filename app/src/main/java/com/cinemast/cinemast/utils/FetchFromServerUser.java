package com.cinemast.cinemast.utils;

public interface FetchFromServerUser {
    void onPreFetch();
    void onFetchCompletion(String string, int id);
}