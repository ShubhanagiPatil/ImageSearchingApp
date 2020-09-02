package com.cavista.imagesearchingapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageData {
    @SerializedName("id")
    String Id;
    @SerializedName("title")
    String Tital;
    @SerializedName("images")
    List<ImageJsonObject> ImageJsonObject;

    String Comment;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTital() {
        return Tital;
    }

    public void setTital(String tital) {
        Tital = tital;
    }

    public List<ImageJsonObject> getImageJsonObject() {
        return ImageJsonObject;
    }

    public void setImageJsonObject(List<ImageJsonObject> imageJsonObject) {
        ImageJsonObject = imageJsonObject;
    }
}
