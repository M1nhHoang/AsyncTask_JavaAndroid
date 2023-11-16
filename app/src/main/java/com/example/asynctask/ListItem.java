package com.example.asynctask;

import android.graphics.Bitmap;

public class ListItem {

    private Bitmap imageResource;
    private String title;
    private String description;

    public ListItem(Bitmap imageResource, String title, String description) {
        this.imageResource = imageResource;
        this.title = title;
        this.description = description;
    }

    public Bitmap getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
