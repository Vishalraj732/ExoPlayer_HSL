package com.example.exoplayertask

import java.io.Serializable


data class VideosModel(
    var id: String,
    var title: String,
    var videoUrl: String
) : Serializable