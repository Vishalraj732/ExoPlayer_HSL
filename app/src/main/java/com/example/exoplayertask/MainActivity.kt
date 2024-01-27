package com.example.exoplayertask

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity(),VideoAdapter.OnVideoItemClicked {

    private lateinit var mFullScreenDialog: Dialog
    private lateinit var mFullScreenButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var mainViewModel: MainViewModel

    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var isFullscreen = false
    private var isPlayerPlaying = true
    private lateinit var mediaItem : MediaItem

    private lateinit var dataList: ArrayList<VideosModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)
        recyclerView = findViewById(R.id.recyclerView)
        mFullScreenButton = findViewById(R.id.mFullScreenButton)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        exoPlayer = ExoPlayer.Builder(this).build()

        if(savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(STATE_RESUME_POSITION)
            isFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN)
            isPlayerPlaying = savedInstanceState.getBoolean(STATE_PLAYER_PLAYING)
        }

        mFullScreenButton.setOnClickListener(View.OnClickListener {
            if (!isFullscreen) {
                this.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                openFullscreenDialog()
            } else {
                this.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                closeFullscreenDialog()
            }
        })

        mFullScreenDialog = object : Dialog(this) {
            override fun onBackPressed() {
                if (isFullscreen) {   //mExoPlayerFullscreen is a boolean that we need to maintain to know whether screen is fullscreen or not.
                    this@MainActivity.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                    closeFullscreenDialog()
                }
                super.onBackPressed()
            }
        }

        loadData()
        startPlayer(dataList[mainViewModel.currentIndex].videoUrl)
    }

    private fun startPlayer(url: String) {

        mediaItem = MediaItem.Builder()
            .setUri(url)
            .setMimeType(MimeTypes.APPLICATION_M3U8)
            .build()
        exoPlayer.apply {
            playWhenReady = isPlayerPlaying
            seekTo(currentWindow, playbackPosition)
            setMediaItem(mediaItem, false)
            prepare()
            addListener(object : Player.Listener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    when (playbackState) {
                        ExoPlayer.STATE_BUFFERING -> {}
                        ExoPlayer.STATE_ENDED -> {}
                        ExoPlayer.STATE_IDLE -> {}
                        ExoPlayer.STATE_READY -> {}
                        else -> {}
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(this@MainActivity, "There is some network issue, Please wait we will play your video soon...", Toast.LENGTH_SHORT).show()
                }
            })
        }
        playerView.player = exoPlayer
        playerView.showController()
    }

    private fun releasePlayer() {
        isPlayerPlaying = exoPlayer.playWhenReady
        playbackPosition = exoPlayer.currentPosition
        currentWindow = exoPlayer.currentMediaItemIndex
        exoPlayer.release()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(STATE_RESUME_POSITION, exoPlayer.currentPosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, isFullscreen)
        outState.putBoolean(STATE_PLAYER_PLAYING, isPlayerPlaying)
        super.onSaveInstanceState(outState)
    }


    override fun onPause() {
        super.onPause()
            playerView.onPause()
            releasePlayer()
    }

    override fun onStop() {
        super.onStop()
            playerView.onPause()
            releasePlayer()
    }

    private fun loadData() {
        val jsonFileString = Utils.getJsonFromAssets(applicationContext, "videos.json")
        val listUserType = object : TypeToken<List<VideosModel?>?>() {}.type
        dataList = Gson().fromJson(jsonFileString, listUserType)
        adapter = VideoAdapter(this, dataList,this)
        recyclerView.adapter = adapter
    }

    private fun openFullscreenDialog() {
        (playerView.parent as ViewGroup).removeView(playerView)
        mFullScreenDialog.addContentView(
            playerView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        isFullscreen = true
        mFullScreenDialog.show()
    }

    private fun closeFullscreenDialog() {
        (playerView.parent as ViewGroup).removeView(playerView)
        isFullscreen = false
        mFullScreenDialog.dismiss()
    }

    override fun onVideoClicked(position: Int) {
        startPlayer(dataList[position].videoUrl)
        mainViewModel.setVideoIndex(position)
    }
    companion object {
        const val STATE_RESUME_POSITION = "resumePosition"
        const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
        const val STATE_PLAYER_PLAYING = "playerOnPlay"
    }
}