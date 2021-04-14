package view;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
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
import app.Album;
import app.CustomImage;
import app.StoreableImage;
import app.Tag;
import app.User;
import view.PhotosController;
import java.util.Calendar;

public class DisplayPhotoController {
	
	private User user;
	private String albumname;

	@FXML private ImageView photo;
	@FXML private TextArea caption_text;
	@FXML private Text photo_preview_text;
	@FXML private TextArea photos_tags_text;
	@FXML private Button back;

	User this_user;
	String albumName;
	public void start(Stage mainStage, User user, String albumname) {
		this_user = user;
		albumName = albumname;
		StoreableImage temp_store = new StoreableImage(null, null);
		for(int i = 0; i < LoginController.user_list.size();i++) {
			if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
				for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
					if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
						for(int k =0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size();k++) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(PhotosController.selected_photo.getPath())) {
								temp_store = LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k);
							}
						}
					}
				}
			}
		}
		if(temp_store == null) {
			System.out.println("ERROR");
		}else {
		photo_preview_text.setText(photo_preview_text.getText()+temp_store.getStringDate());
		caption_text.setText(temp_store.getCaption());
		photo.setImage(new Image(temp_store.getPath()));
		for(int i = 0; i < temp_store.getTagList().size(); i++) {
			if(photos_tags_text.getText() == "") {
				photos_tags_text.setText(temp_store.getTagList().get(i).toString());
			}else {
				photos_tags_text.setText(photos_tags_text.getText()+", "+temp_store.getTagList().get(i));
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

			photosController.start(primaryStage, this_user, albumName);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
	}
}
