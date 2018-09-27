package songlib.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;
import songlib.application.Song;


public class songlibController {
	
	@FXML private TextField songName;
	@FXML private TextField artist;
	@FXML private TextField details;
	@FXML ListView<Song> listView;
	@FXML Button addSong;
	@FXML Button editSong;
	@FXML Button deleteSong;


	private ObservableList<Song> obsList = FXCollections.observableArrayList();


	ArrayList<Song> songList = new ArrayList<>();

	public void start(Stage mainStage) {

		// testing song added to Observable list
		Song s = new Song("Ride with Me", "Nelly", "Album");
		songList.add(s);
		// write data from file to arrayList
		obsList.addAll(songList);
		listView.getItems().addAll(obsList);
		// add ArrayList<Song> songList to observableList so we can populate it with all the values
		//updateObslList(songList, obsList);


		
		// ADD button listener to add fields to ObservableList
		addSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//check if input fields are empty
				if (!songName.getText().isEmpty() && !artist.getText().isEmpty() && !details.getText().isEmpty()) {

					// Create new song instance to add to ArrayList
					Song newSong = new Song(songName.getText(), artist.getText(), details.getText());
					songList.add(0, newSong);

					// add songs from ArrayList to ObservableList
					updateObslList(newSong, obsList);
					clearTextField();
					//obsList.add(0, newSong);
				}
			}
		});

		// Still not functional
		editSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//showItemInputDialog(mainStage);
				editSong.setText("Save");
				Song song = listView.getSelectionModel().getSelectedItem();
				int index = listView.getSelectionModel().getSelectedIndex();

				songName.setText(song.getSongName());
				artist.setText(song.getArtist());
				details.setText(song.getDescription());

				editSong.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						song.setSongFields(songName.getText(), artist.getText(), details.getText());
						songList.set(index, song);
						obsList.removeAll(songList);
						obsList.setAll(songList);
						editSong.setText("Edit");
						clearTextField();
					}
				});
			}
		});

		// Still not functional
		deleteSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int index = listView.getSelectionModel().getSelectedIndex();
				obsList.remove(index);
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

		/*obsList = FXCollections.observableArrayList(
				// add Song
		);*/
		//listView.setItems(obsList);
		obsList.clear();
		obsList.addAll(newSong);
		listView.getItems().addAll(obsList);

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

	private void clearTextField(){
		songName.clear();
		artist.clear();
		details.clear();
	}
	
}
