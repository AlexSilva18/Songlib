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

    public String getArtist(){ return artist; }

    public String getDescription(){
        return description;
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
