package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import app.User;
import view.PhotosController;
import java.util.Calendar;
public class AddPhotoController {
	

	@FXML private ImageView photo;
	@FXML private TextArea caption_text;
	@FXML private TextField tag_add_field;
	@FXML private TextField tag_add_id_field;
	@FXML private TextArea photos_tags_text;
	@FXML private ComboBox tag_delete_dropdown;

	ObservableList<Tag> dropdown_list = FXCollections.observableArrayList();
	
	
	private String albumName;
	private User this_user;
	public void start(Stage mainStage, String albumname, User user) {
		albumName = albumname;
		this_user = user;
	}
	

    Image new_Image;
    ImageView new_ImageView;
    CustomImage new_CustomImage;
	public void select_photo(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		Node node = (Node) e.getSource();
	    File file = fileChooser.showOpenDialog((Stage) node.getScene().getWindow());
	    if (file != null) {
	    	System.out.println("HELLO");   
	        //Image new_image;
			try {
				new_Image = new Image((file.toURI()).toURL().toString());
				ImageView new_ImageView = new ImageView(new_Image);
			    new_ImageView.setFitHeight(50);
			    new_ImageView.setFitWidth(50);
			    new_CustomImage = new CustomImage(new_ImageView, null, (file.toURI()).toURL().toString());
			    //PhotosController.imgList.add(new_custom_image);
			    photo.setImage(new_Image);
			    //System.out.println(new_Image.getHeight());
			    //System.out.println(new_Image.getWidth());
			    double ratio = new_Image.getHeight()/photo.getFitHeight();
			    double actual_width = new_Image.getWidth() / ratio;
			    if(actual_width >350) {
			    	photo.setFitWidth(350);
			    	photo.setX(10);
			    }else {
			    	photo.setX(175-(actual_width/2));
			    }
			    long date = file.lastModified();
			    Calendar cal = Calendar.getInstance();
			    cal.setTimeInMillis(date);
			    cal.set(Calendar.MILLISECOND,0);
			    new_CustomImage.setDate(cal);
			    //System.out.println("HELLO");
			   //SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
			    //System.out.println(new_CustomImage.getStringDate());
			    
			} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
	    }
	}
	public void add_photo(ActionEvent e) {
		if(new_CustomImage == null) {
			return;
		}else {
			System.out.println(caption_text.getText());
			new_CustomImage.setCaption(new Text(caption_text.getText()));
		    PhotosController.imgList.add(new_CustomImage);
		    
		
		    
		}
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
	
	public void add_tag(ActionEvent e) {
		if(new_CustomImage == null) {
			return;
		}else {
			String tag_ID = tag_add_field.getText();
			String tag = tag_add_id_field.getText();
			Tag new_tag = new Tag(tag_ID, tag);
			if(tag_ID == null || tag == null) {
				return;
			}else {
				ArrayList<Tag> tag_list = new_CustomImage.getTagList();
				for(int i = 0; i < tag_list.size(); i++) {
					if(tag_list.get(i).getTag().equals(tag) && tag_list.get(i).getTagID().equals(tag_ID)) {
						return;
					}
				}

				tag_list.add(new_tag);
				dropdown_list.add(new_tag);
				tag_delete_dropdown.setItems(dropdown_list);
				new_CustomImage.setTagList(tag_list);
				System.out.println(new_tag);
				if(photos_tags_text.getText() == "") {
					photos_tags_text.setText(new_tag.toString());
				}else {
					//System.out.println("THE TEXT:"+photos_tags_text.getText());
					photos_tags_text.setText(photos_tags_text.getText()+", "+new_tag);
				}
			}
		}
	
	
	}
	public void delete_tag(ActionEvent e) {
		if(tag_delete_dropdown.getValue() == null || new_CustomImage.getTagList() == null) {
			return;
		}else {
			String delete = tag_delete_dropdown.getValue().toString();
			for(int i = 0;  i < new_CustomImage.getTagList().size(); i++) {
				if(new_CustomImage.getTagList().get(i).toString().equals(delete)) {
					System.out.println("here");
					dropdown_list.remove(i);
					new_CustomImage.getTagList().remove(i);
					tag_delete_dropdown.setItems(dropdown_list);
					photos_tags_text.setText("");
					for(int j = 0 ; j < new_CustomImage.getTagList().size(); j++ ) {
						System.out.print("we entered: "+  new_CustomImage.getTagList().size());
						if(photos_tags_text.getText() == "") {
							photos_tags_text.setText(new_CustomImage.getTagList().get(j).toString());
						}else {
							//System.out.println("THE TEXT:"+photos_tags_text.getText());
							photos_tags_text.setText(photos_tags_text.getText()+", "+new_CustomImage.getTagList().get(j).toString());
						}
					}
					break;
				}
			}
		}
	}
}
