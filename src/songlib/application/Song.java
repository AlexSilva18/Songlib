package songlib.application;

public class Song {
    String songName;
    String artist;
    String description;

    public Song(String songName, String artist, String description){
        this.songName = songName;
        this.artist = artist;
        this.description = description;
    }

    public String getSongName(){
        return songName;
    }

    public String getArtist(){
        return artist;
    }

    public String getDescription(){
        return description;
    }

    // to store data in a file
    public String displayString(){
        return songName + artist + description;
    }
}
