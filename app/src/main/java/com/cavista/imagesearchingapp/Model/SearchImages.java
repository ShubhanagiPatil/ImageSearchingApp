package com.cavista.imagesearchingapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchImages {
    @SerializedName("data")
    List<ImageData> ImageData;
    @SerializedName("status")
    String status;
    @SerializedName("success")
    Boolean success;

    public List<ImageData> getImageData() {
        return ImageData;
    }

    public void setImageData(List<ImageData> imageData) {
        ImageData = imageData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

