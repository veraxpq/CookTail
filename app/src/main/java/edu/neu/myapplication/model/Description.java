package edu.neu.myapplication.model;

import android.net.Uri;

class Description {
    private Uri picture;
    private String description;

    public Description(Uri picture, String description) {
        this.picture = picture;
        this.description = description;
    }

    public Description() {
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
