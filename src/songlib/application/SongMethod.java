/*CS 213 Fall 2018 - Assignment 1, Song Library
		Student1 Name Alex Silva  netid: ars366
		Student2 Name: Hongping Lin netid: hl793
		Grader Name: Govind*/

package songlib.application;

import java.util.ArrayList;

public class SongMethod extends Song{

    // Iterate through ArrayList and compare each Songs String
    // If the Song name is the same, loop again through the ArrayList and compare Artists
    public int insertSortedIndex(ArrayList<Song> songList, Song song){
        //System.out.println("Song is: " + song.getSongName());
        int i = 0;
        for (Song s : songList){
            ++i;
            // check if song comes after the next Song alphabetically, if so go to the next item
            if (song.getSongName().trim().toUpperCase().compareTo(s.getSongName().trim().toUpperCase()) > 0) {
                continue;
            }
            // check if song is already in the list, if so check Artists
            else if (s.getSongName().trim().toUpperCase().compareTo(song.getSongName().trim().toUpperCase()) == 0){

                for (Song a : songList){
                    if (song.getArtist().trim().toUpperCase().compareTo(a.getArtist().trim().toUpperCase()) > 0)
                        continue;

                    else if (song.getArtist().trim().toUpperCase().compareTo(a.getArtist().trim().toUpperCase()) < 0)
                        return songList.indexOf(a);

                    else{
                        // Song Name and Album are the same
                        return -1;
                    }
                }
            }
            else
                return songList.indexOf(s);
        }
        // Song will be placed at the end of the ArrayList
        return i;
    }

}
