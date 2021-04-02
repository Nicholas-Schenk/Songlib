package app;

/*import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
*/
import javafx.application.Application;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
import view.PhotosController;
//import view.SonglibController.Song;

public class PhotosApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/photos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		PhotosController photosController = 
				loader.getController();
		photosController.start(primaryStage);
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		//ComboBox combo = new ComboBox;
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		//System.out.println("when does this print?");
		

	}

}