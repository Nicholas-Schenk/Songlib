package view;

import java.io.IOException;
import java.util.ArrayList;

import app.Album;
import app.CustomImage;
import app.User;
import app.Tag;
import app.StoreableImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhotoSearchController {
	private User user;
	public void setUser(User u) {
		this.user = u;
	}
	
	public User gerUser() {
		return this.user;
	}
	
	private ArrayList<Tag> userTags;
	public static ObservableList<CustomImage> imgList;
	public static ObservableList<CustomImage> searchResults;
	public static CustomImage selected_photo;
	
	
	@FXML Label username;
	
	@FXML private Button logout;
	@FXML private Button searchDate;
	@FXML private Button searchTag1;
	@FXML private Button searchTag1and2;
	@FXML private Button searchTag1or2;
	@FXML private Button createAlbum;
	
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	
	@FXML private ChoiceBox<String> tagName1;
	@FXML private ChoiceBox<String> tagValue1;
	@FXML private ChoiceBox<String> tagName2;
	@FXML private ChoiceBox<String> tagValue2;
	
	@FXML private TableView<CustomImage> tableView;

	
	public void start(Stage mainStage, User u) {
		setUser(u);
		username.setText(user.getUsername());
			//tableView.setEditable(true);
			imgList = null;
			if(imgList == null) {
				imgList = FXCollections.observableArrayList();
	        
	        	for(int i = 0; i < LoginController.user_list.size();i++) {
	        		if((LoginController.user_list.get(i).getUsername()).equals(user.getUsername())) {
	        			for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size(); j++) {
	        					for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size(); k++) {
	        						ImageView temp = new ImageView(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath());
	        						
	        						temp.setFitHeight(50);
	        						temp.setFitWidth(50);
	        						//LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getImage().setFitWidth(50);
	        						imgList.add(new CustomImage(temp, LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getCaption(), LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath()));
	        						for(Tag imageTag: LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getTagList()) {
	        							System.out.println(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getTagList());
	        							userTags.add(imageTag);
	        						}
	        					}
	        				}
	        			}
	        			
	        		}
	        }
			for(CustomImage photo: imgList) {
				System.out.println(photo.getCaption());
				System.out.println(photo.getStringDate());
			}
			if(userTags != null) {
				for(Tag tag: userTags) {
					System.out.println(tag.getTag());
				}	
			}else {
				System.out.println("NO TAGS");
			}
			
	}

	
	public void searchDate(ActionEvent e) {
		
	}
	
	public void searchTag1(ActionEvent e) {
			
	}

	public void searchTag1and2(ActionEvent e) {
		
	}
	public void searchTag1or2(ActionEvent e) {
		
	}
	
	public void createAlbum(ActionEvent e) {
		
	}

	public void logout(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		try {
		loader.setLocation(
				getClass().getResource("/view/LoginView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		LoginController controller = 
				loader.getController();
		controller.start(primaryStage);
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
		}
	}

}
