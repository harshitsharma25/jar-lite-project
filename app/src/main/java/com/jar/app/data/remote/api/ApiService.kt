package com.jar.app.data.remote.api

import com.jar.app.model.GoldPriceResponse
import com.jar.app.model.YoutubeVideoResponse.YoutubeVideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getYoutubeVideos(
        @Query("key") apiKey : String,
        @Query("channelId") channelId : String,
        @Query("part") part : String = "snippet",
        @Query("order") order : String = "date",
        @Query("maxResults") maxResults : Int = 10,
        @Query("pageToken") pageToken: String? = null,
        @Query("type") type : String = "video"
    ) : YoutubeVideoResponse

    @GET("XAU/INR")
    suspend fun getGoldPriceLive() : GoldPriceResponse

}