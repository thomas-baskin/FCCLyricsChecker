import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    List<Song> songs = new ArrayList<>();
    HBox hBox = new HBox();
    VBox vBox = new VBox();
    Font defaultFont = new Font("Helvetica",14);
    String currLyrics;
    ChoiceBox guidelineChoiceBox = new ChoiceBox();
    public void start(Stage primaryStage){
        // Drag and Drop VBox Setup
        Color color1 = Color.color(0.678f, 0.847f, 0.902f,.3);
        VBox dragAndDrop = new VBox();
        dragAndDrop.setFocusTraversable(true);
//        dragAndDrop.setStyle("-fx-border-color: black");
        dragAndDrop.requestFocus();
        dragAndDrop.setMinHeight(100);
        dragAndDrop.setMinWidth(200);
        dragAndDrop.setPrefWidth(300);
        BackgroundFill backgroundFill = new BackgroundFill(color1, new CornerRadii(10), new Insets(10));
        Background background = new Background(backgroundFill);
        dragAndDrop.setBackground(background);

        // Functional Buttons
        Button checkLyricsButton = new Button("Check Lyrics");
        Button seeLyricsButton = new Button("See Lyrics");
        Text currentGuidelines = new Text("Selected Guidelines");
        guidelineChoiceBox.setItems(FXCollections.observableArrayList("FCC2019Standard",
                "ThomasBaskinPreset","Test2020Standard"));

        // ScrollPane for dragAndDrop Pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefViewportHeight(200);
        scrollPane.setPrefViewportWidth(300);
        scrollPane.setContent(dragAndDrop);
        scrollPane.setPannable(true);
//        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //i worked here
//        dragAndDrop.setPrefHeight(scrollPane.getHeight());
        hBox.getChildren().add(scrollPane);
        hBox.getChildren().add(vBox);

        // buttonHBox holds seeLyricsButton
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().add(seeLyricsButton);
        vBox.getChildren().add(buttonHBox);

        // add other buttons below buttonHBox in vBox
        vBox.getChildren().add(currentGuidelines);
        vBox.getChildren().add(guidelineChoiceBox);
        vBox.getChildren().add(checkLyricsButton);
        Scene sc1 = new Scene(hBox);

        // Make lyrics scene
        Pane lyricsPane = new Pane();
        Scene lyricsScene = new Scene(lyricsPane);

        // Animation Setup
        Box box1 = new Box(100,75,50);
        Material boxMaterial1 = new PhongMaterial(Color.LIGHTBLUE);
        Material boxMaterial2 = new PhongMaterial(Color.LIGHTGREEN);
        box1.setMaterial(boxMaterial1);
        RotateTransition rotateTransition1 = new RotateTransition(Duration.millis(4000), new Rectangle(10,10));
        RotateTransition rotateTransition2 = new RotateTransition(Duration.millis(5000), new Rectangle(15,10));
        RotateTransition rotateTransition3 = new RotateTransition(Duration.millis(3000), new Rectangle(10,15));
        rotateTransition1.setCycleCount(Timeline.INDEFINITE);
        rotateTransition2.setCycleCount(Timeline.INDEFINITE);
        rotateTransition3.setCycleCount(Timeline.INDEFINITE);
        rotateTransition1.setByAngle(360);
        rotateTransition2.setByAngle(360);
        rotateTransition3.setByAngle(360);
        rotateTransition1.setAxis(Rotate.X_AXIS);
        rotateTransition2.setAxis(Rotate.Y_AXIS);
        rotateTransition3.setAxis(Rotate.Z_AXIS);
        rotateTransition1.setNode(box1);

        // Nested animations to make motion in 3 dimensions
        FlowPane flowPane1 = new FlowPane();
        rotateTransition2.setNode(flowPane1);
        FlowPane flowPane = new FlowPane();
        rotateTransition3.setNode(flowPane);
        flowPane1.getChildren().add(box1);
        flowPane1.setPrefWidth(150);
        flowPane.getChildren().add(flowPane1);
        flowPane.setPrefWidth(150);
        flowPane.setPadding(new Insets(75));
        hBox.getChildren().add(flowPane);
        rotateTransition1.play();
        rotateTransition2.play();
        rotateTransition3.play();

        sc1.heightProperty().addListener(ov ->{
            scrollPane.setFitToHeight(true);
        });

        ToggleGroup radioButtons = new ToggleGroup();
        radioButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(radioButtons.getSelectedToggle() != null){
                    // Get lyrics, clean them up, add button
                    currLyrics = radioButtons.getSelectedToggle().getUserData().toString();
                    lyricsPane.getChildren().clear();
                    currLyrics = currLyrics.replace("<br>","\n");
                    currLyrics = currLyrics.replace("&quot;","\"");
                    currLyrics = currLyrics.replace("<i>[x7]</i>","");
                    currLyrics += "\n\n";
                    Button goBackButton = new Button("Go Back");
                    goBackButton.setAlignment(Pos.BOTTOM_CENTER);

                    // Scroll Pane and Lyrics Setup
                    VBox lyricsOverGoBack = new VBox();
                    ScrollPane scrollPaneForLyrics = new ScrollPane();
                    lyricsOverGoBack.getChildren().add(new Label (currLyrics));
                    lyricsOverGoBack.getChildren().add(goBackButton);
                    lyricsOverGoBack.setFillWidth(true);
                    scrollPaneForLyrics.setContent(lyricsOverGoBack);
                    scrollPaneForLyrics.setFitToWidth(true);
                    scrollPaneForLyrics.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    scrollPaneForLyrics.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    scrollPaneForLyrics.setPannable(true);
                    lyricsPane.setMinHeight(200);
                    lyricsPane.setMinWidth(200);
                    lyricsPane.setMaxHeight(300);
                    lyricsPane.setMaxWidth(500);
                    lyricsPane.getChildren().add(scrollPaneForLyrics);
                    scrollPaneForLyrics.setPrefViewportHeight(lyricsScene.getHeight());

                    lyricsScene.heightProperty().addListener(ov ->{
                        lyricsPane.setMinHeight(lyricsScene.getHeight());
                        scrollPaneForLyrics.setPrefViewportHeight(lyricsScene.getHeight());
                    });

                    goBackButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            primaryStage.setScene(sc1);
                        }
                    });

                    primaryStage.setScene(lyricsScene);
                }
            }
        });

        seeLyricsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(seeLyricsButton.getText().equals("Home")){
                    seeLyricsButton.setText("See Lyrics");
                    seeLyricsButton.setTextFill(Color.BLACK);
                    dragAndDrop.getChildren().clear();
                    for(Song song : songs){
                        Text text = new Text(song.getSong() + " - " + song.getArtist());
                        Tooltip.install(text,new Tooltip(song.getHoverText()));
                        text.setFont(defaultFont);
                        text.setFill(song.getSongColor());
                        dragAndDrop.getChildren().add(text);
                    }
                    box1.setMaterial(boxMaterial1);
                }
                else if(seeLyricsButton.getText().equals("See Lyrics")){
                    dragAndDrop.getChildren().clear();
                    for(Song song : songs){
                        RadioButton radioButton = new RadioButton(song.getSong() + " - " + song.getArtist());
                        radioButton.setUserData(song.getLyrics());
                        radioButton.setFont(defaultFont);
                        if(song.getSongColor() == Color.BLACK){
                            radioButton.setBackground(new Background(new BackgroundFill(color1.brighter(),new CornerRadii(3),new Insets(0))));
                        }else{
                            radioButton.setBackground(new Background(new BackgroundFill(song.getSongColor(),new CornerRadii(3),new Insets(0))));
                            radioButton.setTextFill(Color.WHITE);
                        }
                        radioButton.setToggleGroup(radioButtons);
                        dragAndDrop.getChildren().add(radioButton);
                    }
                    seeLyricsButton.setText("Home");
                    seeLyricsButton.setTextFill(Color.GREEN);
                    box1.setMaterial(boxMaterial2);
                }
            }
        });

        box1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                seeLyricsButton.fire();
            }
        });

        dragAndDrop.setOnDragEntered(new EventHandler<DragEvent>(){
            @Override
            public void handle(DragEvent event){
                SpotifyHTMLReader(event.getDragboard().getContent(DataFormat.PLAIN_TEXT).toString());
                dragAndDrop.getChildren().clear();
                LookupAllSongs();
                for(Song song : songs){
                    Text text = new Text(song.getSong() + " - " + song.getArtist());
                    Tooltip.install(text,new Tooltip(song.getHoverText()));
                    text.setFont(defaultFont);
                    text.setFill(song.getSongColor());
                    dragAndDrop.getChildren().add(text);
                }
                dragAndDrop.setPrefHeight(scrollPane.getHeight());
            }
        });

        checkLyricsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dragAndDrop.getChildren().clear();
                for (Song song : songs){
                    if(!song.found()){
                        song.setSongColor(Color.DARKGOLDENROD);
                        song.setHoverText("File Not Found");
                    }else if(checkLyrics(song.getLyrics()) && song.found()){
                        song.setSongColor(Color.FIREBRICK);
                        song.setHoverText("Lyrics Contain Unapproved Language");
                    }else{
                        song.setSongColor(Color.DARKGREEN);
                        song.setHoverText("Lyrics Approved");
                    }
                }
                for(Song song : songs){
                    Text text = new Text(song.getSong() + " - " + song.getArtist());
                    Tooltip.install(text,new Tooltip(song.getHoverText()));
                    text.setFont(defaultFont);
                    text.setFill(song.getSongColor());
                    dragAndDrop.getChildren().add(text);
                }
            }
        });

        primaryStage.setScene(sc1);
        primaryStage.setTitle("FCC Lyrics Checker");
        primaryStage.show();
        }

    // Takes one songs's lyrics and returns a true or false on whether they swear based on the selected guidelines
    public boolean checkLyrics(String songLyrics){
        boolean doesItSwear = false;
        String currentDirectory = System.getProperty("user.dir");
        List<String> theSwears = new ArrayList<String>();
        try{
            BufferedReader input = new BufferedReader(new FileReader(currentDirectory
                    + "\\src\\" + guidelineChoiceBox.getSelectionModel().getSelectedItem() + ".txt"));
            String newLine;
            //Make list of swear words based off of selected file
            while((newLine = input.readLine()) != null){
                theSwears.add(newLine);
            }
            for(String swear : theSwears){
                if(songLyrics.toLowerCase().contains(swear)){
                    doesItSwear = true;
//                    System.out.println(swear);
                }
            }
        }catch(IOException io){
            System.out.println(io);
        }
        return doesItSwear;
    }

    // For all songs, it sets the lyrics with an AToZLyricsLookup call
    public void LookupAllSongs(){
        for(Song song : songs){
            if(song.getLyrics() == ""){
                song.setLyrics(AToZLyricsLookup(song));
            }
//            System.out.println(song.getLyrics());
        }
    }

    //Gets Names of Songs with their Artists from Spotify Website
    //Adds songs to the songs list based on those values
    public void SpotifyHTMLReader(String URL){
        try {
            URL webPage = new URL(URL);
            BufferedReader input = new BufferedReader(new InputStreamReader(webPage.openStream()));
            String newLine;
            while((newLine = input.readLine()) != null){
//                System.out.println(newLine);
                // Individual songs
                if(newLine.contains("<span class=\"track-name\" dir=\"auto\">")){
                    List<Character> song = new ArrayList<>();
                    List<Character> artist = new ArrayList<>();

                    int startIndexForSong = newLine.indexOf("<span class=\"track-name\" dir=\"auto\">") +
                            "<span class=\"track-name\" dir=\"auto\">".length();
                    int i = -1;
                    while(newLine.charAt((startIndexForSong + i)) != '<'){
                        i += 1;
                        song.add(newLine.charAt(startIndexForSong + i));
                    }

                    int startIndexForArtist = newLine.indexOf("<span dir=\"auto\">") + "<span dir=\"auto\">".length();
                    int j = -1;
                    while(newLine.charAt((startIndexForArtist + j)) != '<'){
                        j += 1;
                        artist.add(newLine.charAt(startIndexForArtist + j));
                    }

                    StringBuilder strBuilderSong = new StringBuilder();
                    for(char character : song){
                        strBuilderSong.append(character);
                    }
                    StringBuilder strBuilderArtist = new StringBuilder();
                    for(char character : artist){
                        strBuilderArtist.append(character);
                    }

                    String strSong = strBuilderSong.toString();
                    String strArtist = strBuilderArtist.toString();

                    strSong = strReformat(strSong);
                    strArtist = strReformat(strArtist);

                    //Only add song to songs if not already added
                    boolean alreadyAddedToSongs = false;
                    for(Song iter : songs){
                        if(iter.getSong().equals(strSong) && iter.getArtist().equals(strArtist)){
                            alreadyAddedToSongs = true;
                        }
                    }
                    if(!alreadyAddedToSongs){
                        songs.add(new Song(strSong,strArtist));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String[] replacingItems = {",","<","(",")","&quot;","&#039;","&amp;#039;","&amp;amp;","&amp;",".","•"};
    //Replace Function
    public String strReformat(String strWord){
        for(String replaceItem : replacingItems){
            strWord = strWord.replace(replaceItem,"");
        }
        return strWord;
    }

    //Looks up the lyrics than return a string with all the lyrics in one line
    public String AToZLyricsLookup(Song song){
        //artist formatting for website
        String artistNameReformat = song.getArtist().toLowerCase();
        artistNameReformat = artistNameReformat.replace(" ","");
        if(artistNameReformat.startsWith("the")){
            artistNameReformat = artistNameReformat.replace("the", "");
        }

        //song formatting for website
        String songNameReformat = song.getSong().toLowerCase();
        songNameReformat = songNameReformat.replace(" ", "");

        // deals with - Remastered 2009
        if(songNameReformat.contains("-")){
            int cutoff = songNameReformat.indexOf("-");
            int end = songNameReformat.length();
            String removedString = songNameReformat.substring(cutoff,end);
            songNameReformat = songNameReformat.replace(removedString,"");
        }

        String atozURL = "https://www.azlyrics.com/lyrics/" + artistNameReformat + "/"
                + songNameReformat + ".html";
        StringBuilder allLyrics = new StringBuilder();
        //attempts to pull lyrics off of the website
        try {
            URL webPage = new URL(atozURL);
            BufferedReader input = new BufferedReader(new InputStreamReader(webPage.openStream()));
            String newLine = "";
            String lyricLine = "";
            song.setFound(true);
            while((newLine = input.readLine()) != null){
                if(newLine.contains("<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our" +
                        " licensing agreement. Sorry about that. -->")){
                    while(!(lyricLine = input.readLine()).contains("</div>")){
                        allLyrics.append(lyricLine);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
            song.setFound(false);
        }
        return allLyrics.toString();
    }
}
