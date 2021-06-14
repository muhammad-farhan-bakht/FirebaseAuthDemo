package com.farhan.demo.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.farhan.demo.R
import com.farhan.demo.databinding.ActivityHomeBinding
import com.farhan.demo.ui.auth.login.LoginActivity
import com.farhan.demo.util.navigateToWithClearBackStack
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAssetInPlayer(binding.videoViewOne,"asset:///video/alan_walker_darkside.mp4")
        setAssetInPlayer(binding.videoViewTwo,"asset:///video/alan_walker_faded.mp4")
        setAssetInPlayer(binding.videoViewThree,"asset:///video/hatsune_miku_world_is_mine.mp4")
        setAssetInPlayer(binding.audioViewFour,"asset:///audio/akon_right_now_na_na_na.mp3")
        setAssetInPlayer(binding.audioViewFive,"asset:///audio/taylor_swift_love_story.mp3")


    }

    private fun setAssetInPlayer(playerView: PlayerView, url: String) {
        val player = SimpleExoPlayer.Builder(this@HomeActivity).build()
        playerView.player = player
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        Firebase.auth.signOut()
        this@HomeActivity.navigateToWithClearBackStack(LoginActivity::class.java)
    }
}