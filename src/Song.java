import javafx.scene.paint.Color;
import java.util.List;

public class Song {
    private String song;
    private String artist;
    private String lyrics;
    private Color color;
    private List<String> theSwears;
    private boolean found;
    private String hoverText;
    Song(String songName, String artistName){
        this.song = songName;
        this.artist = artistName;
        this.color = Color.BLACK;
        this.found = false;
        this.lyrics = "";
    }
    public String getSong(){
        return song;
    }
    public String getArtist(){
        return artist;
    }
    public void setLyrics(String lyrics){
        this.lyrics = lyrics;
    }
    public String getLyrics(){
        return lyrics;
    }
    public void deleteLyrics(){
        lyrics = null;
    }
    public void setSongColor(Color color){
        this.color = color;
    }
    public Color getSongColor(){
        return this.color;
    }
    public void printSong(){
        System.out.println("\n===========================");
        System.out.println(this.getSong() + " by " + this.getArtist());
        System.out.println("\n===========================");
        System.out.println(this.getLyrics());
    }
    public void setSongSwears(List<String> theSwears){
        this.theSwears = theSwears;
    }
    public List<String> getSongSwears(){
        return theSwears;
    }
    public boolean found(){
        return found;
    }
    public void setFound(boolean found){
        this.found = found;
    }
    public void setHoverText(String hoverText){
        this.hoverText = hoverText;
    }
    public String getHoverText(){
        return hoverText;
    }
}
