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

public class SlideshowController {
	

	@FXML private ImageView photo;
	@FXML private TextArea caption_text;
	@FXML private Text photo_preview_text;
	@FXML private TextArea photos_tags_text;
	@FXML private Button back;
	
	CustomImage selected_photo = PhotosController.selected_photo;
	User this_user;
	String albumName;
	StoreableImage temp_store = new StoreableImage(null, null);
	public void start(Stage mainStage, User user, String albumname) {
		
		this_user = user;
		albumName = albumname;
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
	//int prev_index = -1;
	public void previous(ActionEvent e) {
		int index = -1;
		//if(prev_index == -1) {
			index = PhotosController.imgList.indexOf(selected_photo);
		//}
		//if(prev_index > -1) {
			//index = prev_index;
		//}
		if(index > 0) {
			selected_photo = PhotosController.imgList.get(index-1);
			for(int i = 0; i < LoginController.user_list.size();i++) {
				if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
					for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
						if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
							for(int k =0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size();k++) {
								if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(selected_photo.getPath())) {
									temp_store = LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k);
								}
							}
						}
					}
				}
			}
			
			
			
			System.out.println(temp_store.getTagList().size()+ "tag size "+ temp_store.getPath());
			
			photo_preview_text.setText("Photo Taken: "+temp_store.getStringDate());
			caption_text.setText(temp_store.getCaption());
			photo.setImage(new Image(temp_store.getPath()));
			photos_tags_text.setText("");
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
		   // prev_index = index-1;
		}
	}
	//int next_index = -1;
	public void next(ActionEvent e) {
		//int index = -1;
		//if(next_index == -1) {
			int index = PhotosController.imgList.indexOf(selected_photo);
	/*	}
		if(next_index > -1) {
			index = next_index;
		}*/
		if(index < PhotosController.imgList.size()-1) {
			selected_photo = PhotosController.imgList.get(index+1);
			
			for(int i = 0; i < LoginController.user_list.size();i++) {
				if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
					for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
						if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
							for(int k =0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size();k++) {
								if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(selected_photo.getPath())) {
									temp_store = LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k);
								}
							}
						}
					}
				}
			}
			
			photo_preview_text.setText("Photo Taken: "+temp_store.getStringDate());
			caption_text.setText(temp_store.getCaption());
			photo.setImage(new Image(temp_store.getPath()));
			photos_tags_text.setText("");
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
		//    next_index = index+1;
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
