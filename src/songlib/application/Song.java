package songlib.application;

import java.util.ArrayList;

public class Song {
    String songName;
    String artist;
    String description;

    public Song(){
        this.songName = null;
        this.artist = null;
        this.description = null;
    }

    public Song(String songName, String artist, String description){
        this.songName = songName;
        this.artist = artist;
        this.description = description;
    }

    public String getSongName(){
        return songName;
    }

    public String getArtist(){ return artist; }

    public String getDescription(){
        return description;
    }

    public int fetchSong(ArrayList<Song> songList, Song song){
        for (Song s : songList){
            if (s.getSongName() != null &&  s.getSongName().equals(song.getSongName()) &&
                    s.getArtist().equals(song.getArtist())){
               return songList.indexOf(s);
            }
        }
        return -1;
    }

    public void setSongFields(String songName, String artist, String description) {
        this.songName = songName;
        this.artist = artist;
        this.description = description;
    }

    // to store data in a file
    public String displayString(){
        return songName + artist + description;
    }

    @Override
    public String toString() { return (songName + "  \t\t" + artist + "\t\t\t" + description); }
}
