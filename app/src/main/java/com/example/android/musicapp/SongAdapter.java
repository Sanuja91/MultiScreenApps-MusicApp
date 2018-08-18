package com.example.android.musicapp;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Song Adapter Class to Populate List with relevant ArrayList
public class SongAdapter extends ArrayAdapter<com.example.android.musicapp.Song> {

    /**
     * Create a new {@link SongAdapter} object
     *
     * @param context is the current context (i.e. Activity) that the adapater is being cread in respect of
     * @param songs   is the list of (@link Song)s to be displayed.
     */
    public SongAdapter(Activity context, ArrayList<com.example.android.musicapp.Song> songs) {
        //  Here, we initialize the Array Adapter's internal storage for the context and the list.
        //  the second argument is used when the ArrayAdapter is populating a single TextView
        //  Because this is a custom adapter for three TextViews (Incomplete)
        // going t to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, songs);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate
     *                    (search online for android view recycling for more info)
     * @param parent      The parent ViewGroup that is used for inflation
     * @return The View for the position in the AdapterView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the {@link Song} object located at this position in the list
        com.example.android.musicapp.Song currentSong = getItem(position);

        //  Find the TextView in the list_item.xml layout with the ID songTextView
        TextView songTextView = (TextView) listItemView.findViewById(R.id.songTextView);

        //  Get the Song Name from the current Song object and set this text on the new ListView
        songTextView.setText(currentSong.getSong());

        //  Find the TextView in the list_item.xml layout with the ID artistTextView
        TextView artistTextView = (TextView) listItemView.findViewById(R.id.artistTextView);

        //  Get the Artist Name from the current Song object and set this text on the new ListView
        artistTextView.setText(currentSong.getArtist());

        //  Find the TextView in the list_item.xml layout with the ID albumTextView
        TextView albumTextView = (TextView) listItemView.findViewById(R.id.albumTextView);

        //  Get the Artist Name from the current Song object and set this text on the new ListView
        albumTextView.setText(currentSong.getAlbum());

        // Set the theme colors for the list item
        View textContainer = listItemView.findViewById(R.id.songContainer);

        // Attaches the designated color of the View ID
        int color = ContextCompat.getColor(getContext(), R.color.viewColor);

        //Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 3 TextViews) so that it can be displayed
        return listItemView;
    }
}


