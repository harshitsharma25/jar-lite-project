package com.jar.app.repository

import com.jar.app.data.remote.network.ApiClient
import com.jar.app.model.GoldPriceResponse
import com.jar.app.model.YoutubeVideoResponse.YoutubeVideoResponse

class JarRepository {

    suspend fun getYoutubeVideos(
        apiKey: String,
        channelId: String,
        part: String,
        order: String,
        maxResults: Int,
        pageToken: String,
        type: String
    ) : YoutubeVideoResponse {
        val response = ApiClient.apiService.getYoutubeVideos(
            apiKey = apiKey,
            type = type,
            part = part,
            order = order,
            channelId = channelId,
            pageToken = pageToken,
            maxResults = maxResults
        )
        return response
    }

    suspend fun getGoldPrice() : GoldPriceResponse {
        val response = ApiClient.goldService.getGoldPriceLive()
        return response
    }
}