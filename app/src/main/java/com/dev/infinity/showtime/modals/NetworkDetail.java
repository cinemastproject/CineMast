package com.dev.infinity.showtime.modals;

import java.io.Serializable;

/**
 * Created by deepjyoti on 12/5/16.
 */
public class NetworkDetail implements Serializable {
    private String id;
    private String name;

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
}
