package com.jar.app.model.YoutubeVideoResponse

data class YoutubeVideoResponse(
    val kind : String,
    val etag : String,
    val nextPageToken: String?,
    val prevPageToken: String?,
    val regionCode : String,
    val pageInfo : PageInfo,
    val items : List<YoutubeVideoItems>
)

data class PageInfo(
    val totalResults : Int,
    val resultsPerPage : Int
)

data class YoutubeVideoItems(
    val kind : String,
    val etag : String,
    val id : YoutubeId,
    val snippet: YoutubeSnippet
)

data class YoutubeId(
    val kind : String,
    val videoId: String?
)

data class YoutubeSnippet(
    val publishedAt : String,
    val channelId : String,
    val title : String,
    val description : String,
    val thumbnails : VideoThumbnails,
    val channelTitle : String,
    val liveBroadcastContent : String,
    val publishTime : String
)

data class VideoThumbnails(
    val default: Thumbnail?,
    val medium: Thumbnail?,
    val high: Thumbnail?
)

data class Thumbnail(
    val url: String,
    val width: Int?,
    val height: Int?
)
