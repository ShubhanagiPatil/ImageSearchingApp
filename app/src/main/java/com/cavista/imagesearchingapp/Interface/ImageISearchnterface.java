package com.cavista.imagesearchingapp.Interface;



import com.cavista.imagesearchingapp.Model.SearchImages;
import com.cavista.imagesearchingapp.Utils.Constants;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ImageISearchnterface {

    @GET(Constants.IMAGE_SEARCH_API)
    Single<SearchImages> searchImage(@Header("Authorization") String token, @Query("q") String charSeq);

}
