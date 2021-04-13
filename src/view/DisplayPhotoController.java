package view;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import app.CustomImage;
import app.Tag;
import view.PhotosController;
import java.util.Calendar;

public class DisplayPhotoController {
	

	@FXML private ImageView photo;
	@FXML private TextArea caption_text;
	@FXML private Text photo_preview_text;
	@FXML private TextArea photos_tags_text;
	@FXML private Button back;
	public void start(Stage mainStage) {
		photo_preview_text.setText(photo_preview_text.getText()+PhotosController.selected_photo.getStringDate());
		caption_text.setText(PhotosController.selected_photo.getCaption().getText());
		photo.setImage(PhotosController.selected_photo.getImage().getImage());
		for(int i = 0; i < PhotosController.selected_photo.getTagList().size(); i++) {
			if(photos_tags_text.getText() == "") {
				photos_tags_text.setText(PhotosController.selected_photo.getTagList().get(i).toString());
			}else {
				photos_tags_text.setText(photos_tags_text.getText()+", "+PhotosController.selected_photo.getTagList().get(i));
			}
		}
		double ratio = photo.getImage().getHeight()/photo.getFitHeight();
	    double actual_width = photo.getImage().getWidth() / ratio;
	    if(actual_width >750) {
	    	photo.setFitWidth(750);
	    	photo.setX(10);
	    }else {
	    	photo.setX(380-(actual_width/2));
	    }
	}
	public void back(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/photos.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			PhotosController photosController = 
					loader.getController();
			photosController.start(primaryStage, null, null);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
	}
}
