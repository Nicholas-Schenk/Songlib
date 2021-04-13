package view;

import java.util.ArrayList;
import app.CustomImage;
import app.Tag;
import app.User;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
//import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
//import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhotosController {


	// widget from fxml for tableView
	@FXML private TableView<CustomImage> tableView;
	@FXML private Button add_new_photo;
	@FXML private TextArea caption_text;
	

	@FXML private TextField photo_add_tag_id_text;
	@FXML private TextField photo_add_tag_text;

	@FXML private Text Album_name;
	
	@FXML private Button logout;
	

	//private ObservableList<String> obsList; 
	public static ObservableList<CustomImage> imgList;
	public static CustomImage selected_photo;

	@FXML private ComboBox tag_delete_dropdown;

	ObservableList<Tag> dropdown_list = FXCollections.observableArrayList();
	
	
		
	private String albumName;
	private User this_user;
	private ArrayList<User> user_list;
	public void start(Stage mainStage, User user, String albumname) { 
		albumName = albumname;
		this_user = user;
		System.out.println(user);
		System.out.println(albumname);
		Album_name.setText(albumname);
			tableView.setEditable(true);
			if(imgList == null) {
				imgList = FXCollections.observableArrayList();
	        	ImageView imageView = new ImageView(new Image("/app/llama.jpg"));
	        	imageView.setFitHeight(50);
	        	imageView.setFitWidth(50);
	        	ImageView imageView2 = new ImageView(new Image("/app/llama.jpg"));
	        	imageView2.setFitHeight(50);
	        	imageView2.setFitWidth(50);
	        	Text text = new Text("This is a picture of a llama");
	        	Text text2 = new Text("This the exact same picture of a llama");
	        	CustomImage item_1 = new CustomImage(imageView, text, "/app/llama.jpg");
	        	CustomImage item_2 = new CustomImage(imageView2, text2, "/app/llama.jpg");
	        	imgList.addAll(item_1, item_2);
			}
	        /* initialize and specify table column */
	        TableColumn<CustomImage, ImageView> firstColumn = new TableColumn<CustomImage, ImageView>("Images");
	        firstColumn.setCellValueFactory(new PropertyValueFactory<CustomImage, ImageView>("image"));
	       // firstColumn.setPrefWidth(60);
	        
	        
	        TableColumn<CustomImage, ImageView> secondColumn = new TableColumn<CustomImage, ImageView>("Caption");
	        secondColumn.setCellValueFactory(new PropertyValueFactory<CustomImage, ImageView>("caption"));
	        //firstColumn.setPrefWidth(60);

	        /* add column to the tableview and set its items */
	        tableView.getColumns().add(firstColumn);
	        tableView.getColumns().add(secondColumn);
	        tableView.setItems(imgList);
		//tableView.setItems(imgList); 
		
		// select the first item
		tableView.getSelectionModel().select(0);
		

		tableView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));

		
	}
	
	private void showItemInputDialog(Stage mainStage) {      
		System.out.println("HEY");
		CustomImage item = tableView.getSelectionModel().getSelectedItem();
		ArrayList<Tag> tag_list = item.getTagList();
		dropdown_list = FXCollections.observableArrayList();
		dropdown_list.addAll(tag_list);
		tag_delete_dropdown.setItems(null);
		tag_delete_dropdown.setItems(dropdown_list);
	
	}
		
	public void change_caption(ActionEvent e) {
	
		ObservableList<CustomImage> temp = FXCollections.observableArrayList(imgList);
		FXCollections.copy(temp, imgList);
		//temp = imgList;
		int index = tableView.getSelectionModel().getSelectedIndex();
		if(index < 0 || index > imgList.size()) {
			return;
		}
		CustomImage selected = imgList.get(index);
		
		Text text = new Text(caption_text.getText());
		//System.out.println(text);
		selected.setCaption(text);
		//imgList.add(selected, index);
		imgList.removeAll(imgList);
		for(int i = 0; i < temp.size(); i++) {
			//System.out.println("HELLO");
			if(i == index) {
				imgList.add(selected);
			}else {
				imgList.add(temp.get(i));
			}
		}
		
	}
	public void delete_photo(ActionEvent e) {
		ObservableList<CustomImage> temp = FXCollections.observableArrayList(imgList);
		FXCollections.copy(temp, imgList);
		int index = tableView.getSelectionModel().getSelectedIndex();
		if(index < 0 || index > imgList.size()) {
			return;
		}
		//CustomImage selected = imgList.get(index);
		temp.remove(index);
		imgList.removeAll(imgList);
		for(int i = 0; i < temp.size(); i++) {
				imgList.add(temp.get(i));
		}
	}
	public void add_test(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/addPhoto.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			AddPhotoController controller = 
					loader.getController();
			controller.start(primaryStage, albumName, this_user);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
		
		
		
	}
	public void add_tag(ActionEvent e) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		if(index <0) {
			return;
		}
		CustomImage new_CustomImage = imgList.get(index);
		if(new_CustomImage == null) {
			return;
		}else {
			String tag_ID = photo_add_tag_id_text.getText();
			String tag = photo_add_tag_text.getText();
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
				dropdown_list = FXCollections.observableArrayList();
				dropdown_list.addAll(tag_list);
				tag_delete_dropdown.setItems(null);
				tag_delete_dropdown.setItems(dropdown_list);
				new_CustomImage.setTagList(tag_list);
				System.out.println(new_tag);
			}
		}
	
	
	}
	public void delete_tag(ActionEvent e) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		if(index <0) {
			return;
		}
		CustomImage new_CustomImage = imgList.get(index);
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
					break;
				}
			}
		}
	}
	
	
	public void display_photo(ActionEvent event) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		if(index <0) {
			return;
		}
		selected_photo = imgList.get(index);
		Node node = (Node) event.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/displayPhoto.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			DisplayPhotoController controller = 
					loader.getController();
			controller.start(primaryStage);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
		
		
		
	}
	
	public void slideshow(ActionEvent event) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		if(index <0) {
			return;
		}
		selected_photo = imgList.get(index);
		Node node = (Node) event.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/slideshow.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			SlideshowController controller = 
					loader.getController();
			controller.start(primaryStage);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
		
		
		
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
		//ComboBox combo = new ComboBox;
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
		}
	}
	
	
}