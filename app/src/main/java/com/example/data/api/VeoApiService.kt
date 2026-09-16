package com.example.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class GenerateVideosRequest(
    @Json(name = "prompt") val prompt: String,
    @Json(name = "config") val config: VeoConfig? = null
)

@JsonClass(generateAdapter = true)
data class VeoConfig(
    @Json(name = "numberOfVideos") val numberOfVideos: Int = 1,
    @Json(name = "resolution") val resolution: String = "1080p",
    @Json(name = "aspectRatio") val aspectRatio: String = "16:9"
)

@JsonClass(generateAdapter = true)
data class OperationResponse(
    @Json(name = "name") val name: String? = null,
    @Json(name = "done") val done: Boolean? = false,
    @Json(name = "response") val response: OperationResultPayload? = null,
    @Json(name = "error") val error: OperationError? = null
)

@JsonClass(generateAdapter = true)
data class OperationError(
    @Json(name = "code") val code: Int? = null,
    @Json(name = "message") val message: String? = null
)

@JsonClass(generateAdapter = true)
data class OperationResultPayload(
    @Json(name = "generateVideoResponse") val generateVideoResponse: GenerateVideoResponseData? = null
)

@JsonClass(generateAdapter = true)
data class GenerateVideoResponseData(
    @Json(name = "generatedSamples") val generatedSamples: List<GeneratedSample>? = null
)

@JsonClass(generateAdapter = true)
data class GeneratedSample(
    @Json(name = "video") val video: GeneratedVideoData? = null
)

@JsonClass(generateAdapter = true)
data class GeneratedVideoData(
    @Json(name = "uri") val uri: String? = null
)

interface VeoApiService {
    @POST("v1beta/models/{model}:generateVideos")
    suspend fun generateVideos(
        @Path("model") model: String,
        @Query("key") apiKey: String,
        @Body request: GenerateVideosRequest
    ): OperationResponse

    @GET("v1beta/{operationName}")
    suspend fun getOperation(
        @Path("operationName", encoded = true) operationName: String,
        @Query("key") apiKey: String
    ): OperationResponse
}

object VeoApiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    val service: VeoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(VeoApiService::class.java)
    }
}
