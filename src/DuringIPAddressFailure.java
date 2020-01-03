// keep in case of blacklisting
//                    BufferedReader offlineFile = new BufferedReader(new FileReader(currentDirectory + "\\src\\" + "GreenHamLyricFailureExample.txt"));
//                    String newLyricLine;
//                    while((newLyricLine = offlineFile.readLine()) != null){
//                        for(String swear : theSwears){
//                            if(newLyricLine.contains(swear)){
//                                System.out.println(swear);
//                            }
//                        }
//                    }


// test songs because I got blocked
//        Song song1 = new Song("ThisSong","ThisArtist");
//        song1.setLyrics("Here are my damn lyrics");
//        Song song2 = new Song("ThatSong","ThatArtist");
//        song2.setLyrics("I am going to write something simple here.");
//        Song song3 = new Song("International Anthem","Global Patriots");
//        song3.setLyrics("Here we go, listen to these words\nnothing to see, nowhere to type\nthis song is poorly written");
//        songs.add(song1);
//        songs.add(song2);
//        songs.add(song3);

//        String currentDirectory = System.getProperty("user.dir");
//        String exclamationFileName = "file:" + currentDirectory + "\\src\\!.png";
//        Image exclamation = new Image(exclamationFileName);

//                    dragAndDrop.getChildren().add(new Text(song.getSong() + " - " + song.getArtist()));
//                    dragAndDrop.getChildren().add(new HBox(new ImageView(exclamation), new Text(song.getSong() + " - " + song.getArtist())));