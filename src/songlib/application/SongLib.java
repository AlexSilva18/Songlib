/*CS 213 Fall 2018 - Assignment 1, Song Library
		Student1 Name Alex Silva  netid: ars366
		Student2 Name: Hongping Lin netid: hl793
		Grader Name: Govind*/

package songlib.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import songlib.view.songlibController;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{

		// Loads an object hierarchy from XML document
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
				getClass().getResource("/songlib/view/songlib.fxml"));
		//GridPane root = (GridPane)loader.load();
		AnchorPane root = (AnchorPane)loader.load();
		
		//Create controller object reference to songlibController class start() method
		songlibController controller = loader.getController();
		controller.start(primaryStage);
		
		Scene scene = new Scene(root, 600, 550);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
		songlibController controller = new songlibController();
		ArrayList<Song> outputFile = new ArrayList<>();
		outputFile.addAll(controller.songList);
		controller.writetoFile();
	}
}
