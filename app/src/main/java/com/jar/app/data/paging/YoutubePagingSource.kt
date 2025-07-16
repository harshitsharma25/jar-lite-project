package com.jar.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jar.app.model.YoutubeVideoResponse.YoutubeVideoItems
import com.jar.app.repository.JarRepository

class YouTubePagingSource(
    private val repository: JarRepository,
    private val apiKey: String,
    private val channelId: String
) : PagingSource<String, YoutubeVideoItems>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, YoutubeVideoItems> {
        return try {
            val response = repository.getYoutubeVideos(
                apiKey = apiKey,
                channelId = channelId,
                part = "snippet",
                order = "date",
                maxResults = params.loadSize,
                pageToken = params.key ?: "",
                type = "video"
            )
            println("--> debug data is: $response")

            LoadResult.Page(
                data = response.items,
                prevKey = response.prevPageToken,
                nextKey = response.nextPageToken
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, YoutubeVideoItems>): String? = null
}
