package songlib.view;

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


public class songlibController {
	
	@FXML private TextField songName;
	@FXML private TextField artist;
	@FXML private TextField details;
	@FXML ListView<String> listView;
	@FXML Button addSong;
	@FXML Button editSong;
	@FXML Button deleteSong;
	
	
	
	private ObservableList<String> obsList;
	//private ObservableList<String> list = FXCollections.observableArrayList();
	
	// Declare arrayList
	
	public void start(Stage mainStage) {
		
		// write data from file to arrayList
		obsList = FXCollections.observableArrayList(
				"item1",
				"item2"
				// ArrayList  
				);
		
		listView.setItems(obsList);
		
		// ADD button listener to add fields to ObservableList
		addSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!songName.getText().isEmpty() && !artist.getText().isEmpty() && !details.getText().isEmpty()) {
					// arrayList.add(1, songName.getText());
					obsList.add(1, songName.getText());
				}
			}
		});
		
		editSong.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showItemInputDialog(mainStage);
			}
		});
		
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
	
	private void showSongList(Stage mainStage) {
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
	}
	
	private void showItemInputDialog(Stage mainStage) {
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();
		
		TextInputDialog dialog = new TextInputDialog(item);
		dialog.initOwner(mainStage); dialog.setTitle("Add Song");
		dialog.setHeaderText("Selected Item (Index: " + index + ")");
		dialog.setContentText("Enter Song name: ");		    
	    
		Optional<String> result = dialog.showAndWait();
	    //Optional<Pair<String, String>> result = dialog.showAndWait();
		if (result.isPresent()) { obsList.set(index, result.get());}
		
		
	}
	
	
}
