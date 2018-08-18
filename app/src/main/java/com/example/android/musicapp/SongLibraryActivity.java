package com.example.android.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SongLibraryActivity extends AppCompatActivity {

    // String variable to store Artist Name once selected for specific intents
    private static String selectedArtist;

    // String variable to store Song Name once selected for specific intents
    private static String selectedSong;

    // String variable to store Album Name once selected for specific intents
    private static String selectedAlbum;

    // int variable to store Music Resource ID once selected for specific intents
    private static int selectedMusicResource;

    // int variable to store Artist Description once selected for specific intents
    private static int selectedAristDescription;

    // Boolean to see if list item was clicked at any point
    private static Boolean itemClicked = false;

    // OnCreate method is overridden to load layout file song_list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<com.example.android.musicapp.Song>();

        // Songs are added to the list
        songs.add(new com.example.android.musicapp.Song("On a Good Day (Metropolis)", "Above & Beyond", "Ministry of Sound Annual", R.raw.on_a_good_day,R.string.aboveNBeyond));
        songs.add(new com.example.android.musicapp.Song("Blame it on You (Sidechains Dub)", "Gathania", "Ministry of Sound Annual", R.raw.blame_it_on_you,R.string.gathania));
        songs.add(new com.example.android.musicapp.Song("Burn (Ali Payami Mix)", "Jessica Mauboy", "Ministry of Sound Annual", R.raw.burn,R.string.jessicaMauboy));
        songs.add(new com.example.android.musicapp.Song("Move for Me", "Kaskade", "Strobelight Seduction", R.raw.move_for_me,R.string.kaskade));
        songs.add(new com.example.android.musicapp.Song("Angel on my Shoulder", "Kaskade", "Strobelight Seduction", R.raw.angel_on_my_shoulder,R.string.kaskade));
        songs.add(new com.example.android.musicapp.Song("King without a Crown", "Matisyahu", "Shake off the Dust", R.raw.king_without_a_crown,R.string.matisyahu));
        songs.add(new com.example.android.musicapp.Song("Dynamik", "Michael Woods", "Ministry of Sound Annual", R.raw.dynamik,R.string.michaelWoods));
        songs.add(new com.example.android.musicapp.Song("I Gotta Feeling (Felix Grey Remix)", "Orangez", "Ministry of Sound Annual", R.raw.i_gotta_feeling,R.string.orangez));
        songs.add(new com.example.android.musicapp.Song("I'm Alive 2010", "Stretch & Vern", "Ministry of Sound Annual", R.raw.im_alive_2010,R.string.strechNVern));
        songs.add(new com.example.android.musicapp.Song("Under the Gun", "The Killers", "Saw Dust", R.raw.under_the_gun,R.string.theKillers));
        songs.add(new com.example.android.musicapp.Song("VCR", "The XX", "XX", R.raw.vcr,R.string.theXX));
        songs.add(new com.example.android.musicapp.Song("Islands", "The XX", "XX", R.raw.islands,R.string.theXX));
        songs.add(new com.example.android.musicapp.Song("Touch Me (Electro Mix)", "Tina More", "Ministry of Sound Annual", R.raw.touch_me,R.string.tinaMore));

        // Create an {@link SongAdapter}, whose data source is a list of {@link Song}s.
        // The adapter knows how to create list items for each item in the list.
        SongAdapter adapter = new SongAdapter(this, songs);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // song_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link SongAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Song} in the list.
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Set itemClicked to true
                itemClicked = true;

                // Get the {@link Song} object at the given position the user clicked on
                com.example.android.musicapp.Song song = songs.get(position);

                // Set Artist Name & Description in Global variables for other activities
                selectedArtist = song.getArtist();
                selectedAristDescription = song.getArtistDescription();
                selectedAlbum = song.getAlbum();
                selectedSong = song.getSong();
                selectedMusicResource = song.getMusicResource();

                // Sends the Details to the MusicPlayerActivity using an explicit Intent with multiple extras
                Intent sendMusic = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                sendMusic.putExtra("SongName", selectedSong);
                sendMusic.putExtra("ArtistName", selectedArtist);
                sendMusic.putExtra("AlbumName", selectedAlbum);
                sendMusic.putExtra("MusicResource", selectedMusicResource);
                sendMusic.putExtra("ArtistDescription", selectedAristDescription);
                sendMusic.putExtra("PlaySong",true);
                startActivity(sendMusic);
            }
        });

        // Find the {@link Button} object in the view hierarchy of the {@link Activity}.
        // and set it to the relevant variables
        Button artistDetailsButton = (Button) findViewById(R.id.artistDetailsButton);
        Button musicPlayerButton = (Button) findViewById(R.id.musicPlayerButton);

        // Set a click listener to start Music Player Activity when the button is clicked on
        musicPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check whether a list item has been clicked
                if (itemClicked == true){
                    // If yes open Music Player Activity
                    Intent openMusicPlayer = new Intent(getApplicationContext(), MusicPlayerActivity.class);
                    openMusicPlayer.putExtra("SongName", selectedSong);
                    openMusicPlayer.putExtra("ArtistName", selectedArtist);
                    openMusicPlayer.putExtra("AlbumName", selectedAlbum);
                    openMusicPlayer.putExtra("MusicResource", selectedMusicResource);
                    openMusicPlayer.putExtra("ArtistDescription", selectedAristDescription);
                    openMusicPlayer.putExtra("PlaySong",false);
                    startActivity(openMusicPlayer);
                } else{
                    // Display error message
                    Toast.makeText(getApplicationContext(),"Please Select a Song First",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set a click listener to start Artist Details Activity when the button is clicked on
        artistDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check whether a list item has been clicked
                if (itemClicked == true){
                    // If yes open Artist Details Activity
                    Intent sendArtistDetails = new Intent(getApplicationContext(), ArtistDetails.class);
                    sendArtistDetails.putExtra("ArtistName", selectedArtist);
                    sendArtistDetails.putExtra("ArtistDescription", selectedAristDescription);
                    startActivity(sendArtistDetails);
                } else{
                    // Display error message
                    Toast.makeText(getApplicationContext(),"Please Select a Song First",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

