package com.example.android.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArtistDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Button buttonPause to buttonPause variable

        // Assign variables to TextViews in layout
        TextView artistName = (TextView) findViewById(R.id.artistNameTextView);
        TextView artistDescription = (TextView) findViewById(R.id.artistDecription);

        // Receive intent from otherActivity
        final Bundle receiveArtist = getIntent().getExtras();

        // Assign received intent Extra Information to relevant variables
        artistName.setText(receiveArtist.getString("ArtistName"));
        artistDescription.setText(receiveArtist.getInt("ArtistDescription"));

        // Find the {@link Button} object in the view hierarchy of the {@link Activity}.
        // and set it to the relevant variables
        Button musicPlayerButton = (Button) findViewById(R.id.musicPlayerButton);
        Button songLibraryButton = (Button) findViewById(R.id.songLibraryButton);

        // Set a click listener to start Song Library Activity when the button is clicked on
        songLibraryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open Song Library  Activity and send Intent
                Intent openSongDetails = new Intent(getApplicationContext(), SongLibraryActivity.class);
                openSongDetails.putExtra("ArtistName", receiveArtist.getString("ArtistName"));
                openSongDetails.putExtra("SongName", receiveArtist.getString("SongName"));
                openSongDetails.putExtra("AlbumName", receiveArtist.getString("AlbumName"));
                openSongDetails.putExtra("ArtistDescription", receiveArtist.getInt("ArtistDescription"));
                startActivity(openSongDetails);
            }

        });

        // Set a click listener to start Song Library Activity when the button is clicked on
        musicPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open Song Library  Activity and send Intent
                Intent openSongDetails = new Intent(getApplicationContext(), SongLibraryActivity.class);
                openSongDetails.putExtra("ArtistName", receiveArtist.getString("ArtistName"));
                openSongDetails.putExtra("SongName", receiveArtist.getString("SongName"));
                openSongDetails.putExtra("AlbumName", receiveArtist.getString("AlbumName"));
                openSongDetails.putExtra("ArtistDescription", receiveArtist.getInt("ArtistDescription"));
                startActivity(openSongDetails);
            }

        });
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
