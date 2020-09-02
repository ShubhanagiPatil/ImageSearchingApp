package com.cavista.imagesearchingapp.Model;

import com.google.gson.annotations.SerializedName;

public class ImageJsonObject {
    @SerializedName("link")
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
