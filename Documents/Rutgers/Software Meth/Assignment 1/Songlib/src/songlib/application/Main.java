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


public class Main extends Application {
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
		
		Scene scene = new Scene(root, 500, 450);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Song Library");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		/*Group root = new Group();
		Text txt = new Text("Sup?");
		txt.setFont(new Font("Papyrus", 80));
		Label label = new Label("Name: ");
		TextField nameFld = new TextField();
		GridPane grid = new GridPane();
		grid.add(label, 0, 0);
		grid.add(nameFld, 1, 0);
		grid.setHgap(20);
		Button btn = new Button();
		grid.add(btn, 1, 1);
		//grid.setGridLinesVisible(true);
		btn.setText("Say Sup!");
		btn.setOnAction(evt -> System.out.printf("Sup %s!%n",
				nameFld.getText()));
		//root.getChildren().add(txt);
		//root.getChildren().add(btn);
		txt.setY(50);
		VBox box = new VBox();
		box.getChildren().addAll(txt, nameFld, grid);
		root.getChildren().add(box);*/
		/*primaryStage.setScene(scene);
		primaryStage.setTitle("Sup");
		//primaryStage.setScene(new Scene(root, 300, 275));
		primaryStage.show();*/
		
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
