/*CS 213 Fall 2018 - Assignment 1, Song Library
		Student1 Name Alex Silva  netid: ars366
		Student2 Name: Hongping Lin netid: Lin hl793
		Grader Name: Govind*/

package songlib.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Scanner;


import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import songlib.application.Song;
import songlib.application.SongMethod;

public class songlibController{

	@FXML
	private TextField songName;
	@FXML
	private TextField artist;
	@FXML
	private TextField album;
	@FXML
	private TextField year;

	@FXML
	public Text songNameDisplay;
	@FXML
	public Text artistDisplay;
	@FXML
	public Text albumDisplay;
	@FXML
	public Text yearDisplay;

	@FXML
	Button addSong;
	@FXML
	Button editSong;
	@FXML
	Button deleteSong;
	@FXML
	Button saveEdit;
	@FXML
	Button cancelOperation;

	@FXML
	ListView<Song> listView;

	private ObservableList<Song> obsList = FXCollections.observableArrayList();
	public static ArrayList<Song> songList = new ArrayList<>();

	public void start(Stage mainStage) {
		getfromFile();
		//Song s = new Song("stuff", "1", "2", "3");
		//obsList.addAll(s);

		// keep buttons deactivated if list is empty
		if (obsList.isEmpty())
			toggleButtons(1, 1);
		else
			toggleButtons(0, 1);

		// write data from file to arrayList
		obsList.addAll(songList);
		listView.getItems().addAll(obsList);

		// pre-select first song in listView if any
		if (!obsList.isEmpty())
			listView.getSelectionModel().select(0);

		//int selectedIndex = listView.getSelectionModel().getSelectedIndex();

		// gets every mouse selection and display the song details
		if (!(listView.getSelectionModel().isEmpty())) {
			listView.getSelectionModel().selectedItemProperty()
					.addListener(new ChangeListener<Song>() {
						@Override
						public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
							//System.out.println(newValue.getSongName());
							// sets the text displays to the values of the song selected
							setDisplay(newValue);
						}
					});
		}


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

					if (index == -1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.initOwner(mainStage);  // need to know the parent of screen and pop up on the center to that screen
						alert.setTitle("Error");
						alert.setHeaderText("Song is already in the List");
						String content = "Song: " + newSong.getSongName() + " " +
								"Artist: " + newSong.getArtist();

						alert.setContentText(content);
						alert.showAndWait();
					} else {
						//System.out.println("sorted index is: " + index);
						songList.add(index, newSong);

						// add songs from ArrayList to ObservableList
						updateObslList(index, newSong, obsList);

						clearTextField();
					}
				}
				toggleButtons(0, 1);
			}
		});

		// Still not functional
		editSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public final void handle(ActionEvent event) {

				//showItemInputDialog(mainStage);
				toggleButtons(0, 0);

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
				if (!songList.isEmpty()) {
					listView.getSelectionModel().select(index);
				}

				if (obsList.isEmpty())
					toggleButtons(1, 1);
				else
					toggleButtons(0, 1);
			}
		});

		
		/*listView
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
					(obs, oldVal, newVal) ->
						showItemInputDialog(mainStage));*/
	}

	private void toggleButtons(int flag, int saveFlag) {

		if (flag == 1) {
			editSong.setDisable(true);
			deleteSong.setDisable(true);
			cancelOperation.setDisable(true);

		} else if (flag == 0) {
			editSong.setDisable(false);
			deleteSong.setDisable(false);
			cancelOperation.setDisable(false);
		}
		if (saveFlag == 1) {
			saveEdit.setDisable(true);
		} else if (saveFlag == 0) {
			saveEdit.setDisable(false);

		}
	}

	private void displaySelection(Song song) {
		songNameDisplay.setText(song.getSongName());
		//setDisplay(song);
	}

	// Populate observableList with new Song
	private void updateObslList(int index, Song newSong, ObservableList<Song> obsList) {
		obsList.clear();
//		obsList.removeAll(songList);
		obsList.addAll(songList);
		//listView.getItems().removeAll(obsList);
		//listView.getItems().addAll(obsList);
		listView.getItems().setAll(obsList);

		// pre-select added song
		listView.getSelectionModel().select(index);
	}

	private void saveAction(int index, int songListIndex, Song song) {

		// update song fields
		song.setSongFields(songName.getText(), artist.getText(), album.getText(), year.getText());

		// update songList with the edited song fields
		//songList.set(songListIndex, song);
		songList.remove(songListIndex);
		int newIndex = new SongMethod().insertSortedIndex(songList, song);
		songList.add(newIndex, song);
		//System.out.println(newIndex);
		updateObslList(newIndex, song, obsList);
		//obsList.clear();
		//obsList.addAll(songList);

		clearTextField();
		toggleButtons(0, 1);
		//listView.getItems().setAll(obsList);
	}

	// clear all TextFields
	private void clearTextField() {
		songName.clear();
		artist.clear();
		album.clear();
		year.clear();
	}

	private void setDisplay(Song song) {
		// set display fields to mouse selected Song
		songNameDisplay.setText(song.getSongName());
		artistDisplay.setText(song.getArtist());
		albumDisplay.setText(song.getAlbum());
		yearDisplay.setText(song.getYear());

		if (obsList.isEmpty()) {
			songNameDisplay.setText(null);
			artistDisplay.setText(null);
			albumDisplay.setText(null);
			yearDisplay.setText(null);
		}
	}

	public void getfromFile() {
		//System.out.println("does file exist?");
		File file = new File("SongLibrary.txt");

		if(file.exists() && file.isFile())
		{
			//System.out.println("file exists");
			try {
				Scanner sc = new Scanner(file);
				int line =0;
				while(sc.hasNextLine())
				{
					line++;
					sc.nextLine();
				}
				sc.close();
//
//					sc = new Scanner(file);
//					line -= 2;
//					sc.nextLine();
//					sc.nextLine();

				if(line % 4 == 0)
				{
					for(int input=0; input<line; input+=4)
					{
						songList.add(new Song(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine()));
					}
				}
			}catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}

			listView.setItems(obsList);
			if(!songList.isEmpty())
			{
				listView.getSelectionModel().select(0);
			}

			showSongDestribtion();

			listView.getSelectionModel().selectedItemProperty().addListener(
					(obs, oldValue, newValue) -> showSongDestribtion());



		}else {
			System.out.println("file does not exist");
			try {
				//File file = new File("SongLibrary.txt");
				PrintWriter write = new PrintWriter(file);
				file.createNewFile();
				for(int i =0; i<songList.size(); i++) {
					write.println(songList.get(i).getSongName());
					write.println(songList.get(i).getArtist());
					write.println(songList.get(i).getAlbum());
					write.print(songList.get(i).getYear());
					if(i !=songList.size()-1) {
						write.println("");
					}write.close();

				}

			}catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	private void showSongDestribtion()
	{
		if(listView.getSelectionModel().getSelectedIndex() < 0)
		{
			return;
		}

		Song song = listView.getSelectionModel().getSelectedItem();
		songName.setText(song.getSongName());
		artist.setText(song.getArtist());
		album.setText(song.getAlbum());
		year.setText(song.getYear());
	}
}
