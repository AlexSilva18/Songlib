/*CS 213 Fall 2018 - Assignment 1, Song Library
		Student1 Name Alex Silva  netid: ars366
		Student2 Name: Hongping Lin netid: Lin hl793
		Grader Name: Govind*/

package songlib.application;

import java.util.ArrayList;

public class Song {
    String songName;
    String artist;
    String album;
    String year;

    public Song(){
        this.songName = null;
        this.artist = null;
        this.album = null;
        this.year = null;
    }

    public Song(String songName, String artist, String album, String year){
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public String getSongName(){
        return songName;
    }

    public String getArtist(){ return artist; }

    public String getAlbum(){
        return album;
    }

    public String getYear(){
        return year;
    }

    // writes song to file with this format
    public String songtoWrite() {
        return (songName + "\t" + artist + "\t" + album + "\t" + year);
    }

    // update song fields when song is edited
    public void setSongFields(String songName, String artist, String album, String year) {
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    // allows comparison between two songs with compareTo
    public int compareTo(Song song){
        return this.getSongName().compareTo(song.getSongName());
    }

    // to store data in a file
    public String displayString(){
        return songName + artist + album;
    }

    // display song name and artist in listview
    @Override
    public String toString() { return (songName + "  \t\t" + artist); }
}
