package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
		System.out.println("HELLO THERE: "+ user);
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
			try {
				new_Image = new Image((file.toURI()).toURL().toString());
				ImageView new_ImageView = new ImageView(new_Image);
			    new_ImageView.setFitHeight(50);
			    new_ImageView.setFitWidth(50);
			    new_CustomImage = new CustomImage(new_ImageView, null, (file.toURI()).toURL().toString());
			    photo.setImage(new_Image);
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
			} catch (Exception e1) {
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
			new_CustomImage.setCaption(caption_text.getText());
		    PhotosController.imgList.add(new_CustomImage);

			StoreableImage temp_store = new StoreableImage(new_CustomImage.getCaption(), new_CustomImage.getPath());
		    temp_store.setTagList(new_CustomImage.getTagList());
		    if(temp_store.getTagList()!= null) {
		    	System.out.println("yee");
		    }
			temp_store.setDate(new_CustomImage.getDate());
			
		    // add to list
		    for(int i = 0; i < LoginController.user_list.size(); i++) {
		    	System.out.println("HERE IS THE NAME"+LoginController.user_list.get(i).getUsername());
		    	if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
		    		for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size(); j++) {
		    			if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
		    				if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList() == null) {
		    					ArrayList<StoreableImage> new_image_list_temp = new ArrayList<StoreableImage>();
		    					new_image_list_temp.add(temp_store);
		    					LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new_image_list_temp);
		    				}else {
		    					LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(temp_store);
		    					
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    
		    //write to file
		    ArrayList<User> users = LoginController.user_list;
	    	for(User i: users) {
	    		try {
	    			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\users\\"+i.getUsername()+".txt"));
	    			oos.writeObject(i);
	    			oos.close();
	    		}catch(Exception ero) {
					System.out.println(ero);
				}
	    	}
		    
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
			photosController.start(primaryStage, this_user, albumName);
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
					photos_tags_text.setText(photos_tags_text.getText()+", "+new_tag);
				}
				
			    for(int i = 0; i < LoginController.user_list.size(); i++) {
			    	System.out.println("HERE IS THE NAME"+LoginController.user_list.get(i).getUsername());
			    	if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
			    		System.out.println("it was the current one!");
			    		for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size(); j++) {
			    			if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
			    				LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
								for(int k = 0; k < PhotosController.imgList.size(); k++) {
		    						System.out.println(k);
		    						CustomImage cimage = PhotosController.imgList.get(k);
		    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 
		    						simage.setTagList(cimage.getTagList());
		    						simage.setDate(cimage.getDate());
		    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
		    					}
			    			}
			    		}
			    	}
			    }
			    
			    
			    //write to file
			    ArrayList<User> users = LoginController.user_list;
		    	for(User i: users) {
		    		try {
		    			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\users\\"+i.getUsername()+".txt"));
		    			oos.writeObject(i);
		    			oos.close();
		    		}catch(Exception ero) {
						System.out.println(ero);
					}
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

				    for(int j = 0; j < LoginController.user_list.size(); j++) {
				    	System.out.println("HERE IS THE NAME"+LoginController.user_list.get(j).getUsername());
				    	if(LoginController.user_list.get(j).getUsername().equals(this_user.getUsername())) {
				    		System.out.println("it was the current one!");
				    		for(int k = 0; k < LoginController.user_list.get(j).getAlbumData().size(); k++) {
				    			if(LoginController.user_list.get(j).getAlbumData().get(k).getName().equals(albumName)) {
				    				for(int l = 0; l < LoginController.user_list.get(j).getAlbumData().get(k).getImageList().size();l++) {
				    					if(LoginController.user_list.get(j).getAlbumData().get(k).getImageList().get(l).getPath().equals(new_CustomImage.getPath())) {
				    						LoginController.user_list.get(j).getAlbumData().get(k).getImageList().get(l).setTagList(new_CustomImage.getTagList());
				    					}
				    				}
				    			}
				    		}
				    	}
				    }
					
					
					
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
