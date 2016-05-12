package Utilities;

import java.io.Serializable;

/**
 * Created by deepjyoti on 12/5/16.
 */
public class ProductionCompanyDetail implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
