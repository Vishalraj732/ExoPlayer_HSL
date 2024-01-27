package com.example.exoplayertask

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var currentIndex: Int = 0

    fun setVideoIndex(index:Int){
        currentIndex = index
    }
}