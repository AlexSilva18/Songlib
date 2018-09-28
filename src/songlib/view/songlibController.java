package songlib.view;

import java.util.ArrayList;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import songlib.application.Song;
import songlib.application.SongMethod;

public class songlibController {
	
	@FXML private TextField songName;
	@FXML private TextField artist;
	@FXML private TextField album;
	@FXML private TextField year;

	@FXML private Text songNameDisplay;
	@FXML private Text artistDisplay;
	@FXML private Text albumDisplay;
	@FXML private Text yearDisplay;

	@FXML Button addSong;
	@FXML Button editSong;
	@FXML Button deleteSong;
	@FXML Button saveEdit;
	@FXML Button cancelOperation;

	@FXML ListView<Song> listView;




	private ObservableList<Song> obsList = FXCollections.observableArrayList();


	ArrayList<Song> songList = new ArrayList<>();

	public void start(Stage mainStage) {

		//Song s = new Song("stuff", "1", "2", "3");
		//obsList.addAll(s);
		// keep save button deactivated
		saveEdit.setDisable(true);

		// write data from file to arrayList
		obsList.addAll(songList);
		listView.getItems().addAll(obsList);

		// pre-select first song in listView if any
		listView.getSelectionModel().select(0);

		//int selectedIndex = listView.getSelectionModel().getSelectedIndex();

		// gets every mouse selection and display the song details
		listView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Song>() {
								 @Override
								 public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
									 //System.out.println(newValue.getAlbum());
									 
									 // sets the text displays to the values of the song selected
									 setDisplay(newValue);
								 }
							 });

//		listView.getSelectionModel()
//				.getSelectedItem((song.) -> albumDisplay.setText());
		//albumDisplay.setText(song.getAlbum());
		//yearDisplay.setText(song.getYear());

		//System.out.println(listView.getSelectionModel().getSelectedItems());

		// add ArrayList<Song> songList to observableList so we can populate it with all the values
		//updateObslList(songList, obsList);

		cancelOperation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				clearTextField();
				saveEdit.setDisable(true);
			}
		});

		
		// ADD button listener to add fields to ObservableList
		addSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				//check if input fields are empty
				if (!songName.getText().isEmpty() && !artist.getText().isEmpty()) {

					// Create new song instance to add to ArrayList
					Song newSong = new Song(songName.getText().toLowerCase(), artist.getText().toLowerCase(),
							album.getText().toLowerCase(), year.getText().toLowerCase());

					// retrieve sorted index to insert new song, returns -1 if song is duplicate
					int index = new SongMethod().insertSortedIndex(songList, newSong);

					if (index == -1){
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(mainStage);  // need to know the parent of screen and pop up on the center to that screen
						alert.setTitle("Error");
						alert.setHeaderText("Song is already in the List");
						String content  = "Song: " + newSong.getSongName() + " " +
								"Artist: " + newSong.getArtist();

						alert.setContentText(content);
						alert.showAndWait();
					}
					else{
						//System.out.println("sorted index is: " + index);
						songList.add(index, newSong);

						// add songs from ArrayList to ObservableList
						updateObslList(newSong, obsList);

						// pre-select added song
						listView.getSelectionModel().select(index);

						clearTextField();
					}
				}
				saveEdit.setDisable(true);
			}
		});

		// Still not functional
		editSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public final void handle(ActionEvent event) {

				//showItemInputDialog(mainStage);
				saveEdit.setDisable(false);

				// get mouse selected song and index
				Song song = listView.getSelectionModel().getSelectedItem();
				int index = listView.getSelectionModel().getSelectedIndex();

				// Fill textFields with item selected to edit
				songName.setText(song.getSongName().toLowerCase());
				artist.setText(song.getArtist().toLowerCase());
				album.setText(song.getAlbum().toLowerCase());
				year.setText(song.getYear().toLowerCase());

				// fetch songList for the index holding the selected song
				int songListIndex = songList.indexOf(song);

				// saves edited song to the songList and updates the observableList
				saveEdit.setOnAction(e -> saveAction(index, songListIndex, song));

			}
		});

		// Still gives error when list is empty
		deleteSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// get mouse selected song and index
				Song song = listView.getSelectionModel().getSelectedItem();
				int index = listView.getSelectionModel().getSelectedIndex();

				// get index of the song in the ArrayList
				int songListIndex = songList.indexOf(song);
				//System.out.println(songList.get(songListIndex).getSongName());

				songList.remove(songListIndex);
				obsList.remove(song);
				listView.getItems().remove(index);
				//listView.getItems().setAll(obsList);

				// pre-select next/previous song when deleted
				listView.getSelectionModel().select(index);
				saveEdit.setDisable(true);
			}
		});

		
		/*listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
						showItemInputDialog(mainStage));*/
	}

	// Populate observableList with new Song
	private void updateObslList(Song newSong, ObservableList<Song> obsList){
		obsList.clear();
		obsList.addAll(songList);
//		listView.getItems().removeAll(obsList);
		//listView.getItems().addAll(obsList);
		listView.getItems().setAll(obsList);

	}

	private void saveAction(int index, int songListIndex, Song song){

		// update song fields
		song.setSongFields(songName.getText(), artist.getText(), album.getText(), year.getText());

		// update songList with the edited song fields
		songList.set(songListIndex, song);

		obsList.setAll(song);
		obsList.clear();

		clearTextField();
		saveEdit.setDisable(true);
	}

	// clear all TextFields
	private void clearTextField(){
		songName.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	private void setDisplay(Song song){

		// set display fields to mouse selected Song
		songNameDisplay.setText(song.getSongName());
		artistDisplay.setText(song.getArtist());
		albumDisplay.setText(song.getAlbum());
		yearDisplay.setText(song.getYear());

	}

	/*private void showSongList(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);
	      alert.initOwner(mainStage);  // need to know the parent of screen and pop up on the center to that screen
	      alert.setTitle("List Item");
	      alert.setHeaderText("Selected list item properties");
	      String content  = "Index: " +
	    		  listView.getSelectionModel().getSelectedIndex() +
	    		  "\nValue: " +
	    		  listView.getSelectionModel().getSelectedItem();
	      
	      alert.setContentText(content);
	      alert.showAndWait();
	}*/
	
	/*private void showItemInputDialog(Stage mainStage) {
		Song item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		
		TextInputDialog dialog = new TextInputDialog(item.getSongName());
		dialog.initOwner(mainStage); dialog.setTitle("Add Song");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter Song name: ");		    
	    
		Optional<String> result = dialog.showAndWait();
	    //Optional<Pair<String, String>> result = dialog.showAndWait();
		//if (result.isPresent()) { obsList.set(index, result.get());}
		
		
	}*/


	
}
