package com.shadowings.apodkmp.android.fragments

import android.os.Bundle
import co.touchlab.kampstarter.android.BuildConfig
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.shadowings.apodkmp.model.Apod

class ApodPlayerFragment(private val apod: Apod) : YouTubePlayerSupportFragment() {

    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)

        if (apod.media_type == "video") {
            this.initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                    p1?.loadVideo(apod.youtubeId, apod.startTimeMs)
                }
                override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                }
            })
        }
    }
}
