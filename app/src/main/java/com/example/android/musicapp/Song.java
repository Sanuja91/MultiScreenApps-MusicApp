package com.example.android.musicapp;

// Object Class called Song to hold the details related to a song
public class Song {

    // String Value for Song Name
    private String mSongName;

    // String Value for Artist Name
    private String mArtist;

    // String Value for Album Name
    private String mAlbum;

    // Int Value for Song Resource ID
    private int mMusicResourceId;

    // Int Value for Artist Description Resource ID
    private int mArtistDescription;

    // Constructs a Song with the initial values Song Name, Artist, Album and Music Resource ID
    public Song(String songName, String artist, String album, int musicResourceId, int artistDescription) {
        mSongName = songName;
        mArtist = artist;
        mAlbum = album;
        mMusicResourceId = musicResourceId;
        mArtistDescription = artistDescription;
    }

    // Returns Song Name
    public String getSong() {
        return mSongName;
    }

    // Returns Artist Name
    public String getArtist() {
        return mArtist;
    }

    // Returns Album Name
    public String getAlbum() {
        return mAlbum;
    }

    // Returns Music Resource ID
    public int getMusicResource() {
        return mMusicResourceId;
    }

    // Returns Artist Description
    public int getArtistDescription() {
        return mArtistDescription;
    }
}
