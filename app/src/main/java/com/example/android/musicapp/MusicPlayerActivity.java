package com.example.android.musicapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicPlayerActivity extends AppCompatActivity {

    //Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();

            // Go back to the SongLibraryActivity once the song is finished playing
            Intent backToLibrary = new Intent(getApplicationContext(), SongLibraryActivity.class);
            startActivity(backToLibrary);
        }
    };

    // Clean up the media player by releasing its resources.
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Button buttonPause to buttonPause variable
        Button buttonPause = (Button) findViewById(R.id.buttonPause);
        // Set buttonPause text to Play for initial state
        buttonPause.setText("Pause");

        // Set OnClickListener on buttonPause
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set buttonPause to be respective button named buttonPause
                Button buttonPause = (Button) findViewById(R.id.buttonPause);

                // If the Media Player is playing, pause Media Player
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    buttonPause.setText("Play");

                    // If the Media Player is not playing, start Media Player
                } else if (!mMediaPlayer.isPlaying()) {
                    buttonPause.setText("Pause");
                    mMediaPlayer.start();
                }
            }

        });

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) MusicPlayerActivity.this.getSystemService(Context.AUDIO_SERVICE);

        // Assign variables to TextViews in layout
        TextView songName = (TextView) findViewById(R.id.songNameTextView);
        TextView albumName = (TextView) findViewById(R.id.albumNameTextView);
        TextView artistName = (TextView) findViewById(R.id.artistNameTextView);

        // Assign variable for Music Resource
        int musicResource = 0;

        // Assign boolean to play song if correct intent
        boolean playSong;

        // Receive intent from SongLibraryActivity
        final Bundle receiveMusic = getIntent().getExtras();

        // Assign boolean variable to filter through multiple intents
        playSong = receiveMusic.getBoolean("PlaySong");

        // Assign received intent Extra Information to relevant variables
        songName.setText(receiveMusic.getString("SongName"));
        albumName.setText(receiveMusic.getString("AlbumName"));
        artistName.setText(receiveMusic.getString("ArtistName"));


        if (playSong == true) {

            // Assign Music Resource if the Song Library is sending the appropriate intent to play a song
            musicResource = receiveMusic.getInt("MusicResource");


            // Release the media player if it currently exists because we are about to
            // play a different sound file
            releaseMediaPlayer();

            // Request audio focus so in order to play the audio file with AUDIOFOCUS_GAIN
            int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                    AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                // We have audio focus now.
                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // Music Resource sent from the SongLibraryActivity
                mMediaPlayer = MediaPlayer.create(MusicPlayerActivity.this, musicResource);

                // Start the audio file
                mMediaPlayer.start();

                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        }

        // Find the {@link Button} object in the view hierarchy of the {@link Activity}.
        // and set it to the relevant variables
        Button artistDetailsButton = (Button) findViewById(R.id.artistDetailsButton);
        Button songLibraryButton = (Button) findViewById(R.id.songLibraryButton);

        // Set a click listener to start Artist Details Activity when the button is clicked on
        artistDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open Artist Details Activity and send Intent
                Intent sendArtistDetails = new Intent(getApplicationContext(), ArtistDetails.class);
                sendArtistDetails.putExtra("ArtistName", receiveMusic.getString("ArtistName"));
                sendArtistDetails.putExtra("SongName", receiveMusic.getString("SongName"));
                sendArtistDetails.putExtra("AlbumName", receiveMusic.getString("AlbumName"));
                sendArtistDetails.putExtra("ArtistDescription", receiveMusic.getInt("ArtistDescription"));
                startActivity(sendArtistDetails);
            }

        });

        // Set a click listener to start Song Library Activity when the button is clicked on
        songLibraryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open Song Library  Activity and send Intent
                Intent openSongDetails = new Intent(getApplicationContext(), SongLibraryActivity.class);
                openSongDetails.putExtra("ArtistName", receiveMusic.getString("ArtistName"));
                openSongDetails.putExtra("SongName", receiveMusic.getString("SongName"));
                openSongDetails.putExtra("AlbumName", receiveMusic.getString("AlbumName"));
                openSongDetails.putExtra("ArtistDescription", receiveMusic.getInt("ArtistDescription"));
                startActivity(openSongDetails);
            }

        });
    }

    // Override onResume so the Media player resumes from where it was if the user returns to the Media Player page
    @Override
    protected void onResume() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying())
            mMediaPlayer.start();
        super.onResume();
    }

    // Setup back button to return to previous activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}



