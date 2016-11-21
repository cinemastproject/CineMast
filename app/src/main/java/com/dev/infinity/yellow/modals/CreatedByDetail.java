package com.dev.infinity.yellow.modals;

import java.io.Serializable;

/**
 * Created by deepjyoti on 12/5/16.
 */
public class CreatedByDetail implements Serializable {
    private String id;
    private String name;
    private String profile_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}

