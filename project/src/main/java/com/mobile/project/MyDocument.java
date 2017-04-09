package com.mobile.project;

/**
 * Created by YMC on 08/04/2017.
 */

class MyDocument {

    private String name;
    private String dateCreation;
    private String path;

    public MyDocument(String name, String dateCreation,String path) {
        this.name = name;
        this.dateCreation = dateCreation;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
