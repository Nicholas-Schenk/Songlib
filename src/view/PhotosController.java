package view;

import java.util.ArrayList;

import app.Album;
import app.CustomImage;
import app.StoreableImage;
import app.Tag;
import app.User;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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

import view.LoginController;

public class PhotosController {
	

	// widget from fxml for tableView
	@FXML private TableView<CustomImage> tableView;
	@FXML private Button add_new_photo;
	@FXML private TextArea caption_text;
	

	@FXML private TextField photo_add_tag_id_text;
	@FXML private TextField photo_add_tag_text;

	@FXML private Text Album_name;
	
	@FXML private Button logout;
	@FXML private Button back;
	

	//private ObservableList<String> obsList; 
	public static ObservableList<CustomImage> imgList;
	public static CustomImage selected_photo;

	@FXML private ComboBox tag_delete_dropdown;

	@FXML private ComboBox album_box;
	ObservableList<Tag> dropdown_list = FXCollections.observableArrayList();
	ObservableList<String> album_list = FXCollections.observableArrayList();
	
		
	private String albumName;
	private User this_user;
	private ArrayList<User> user_list;
	public void start(Stage mainStage, User user, String albumname) { 
		albumName = albumname;
		this_user = user;
		if(user == null) {
			System.out.println("ahhhhhh");
		}
		System.out.println(user+ "---");
		System.out.println(albumname);
		Album_name.setText(albumname);
			tableView.setEditable(true);
			imgList = null;
			if(imgList == null) {
				imgList = FXCollections.observableArrayList();
	        	/*ImageView imageView = new ImageView(new Image("/app/llama.jpg"));
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
	        	ArrayList<CustomImage> temp_arr_list = new ArrayList<CustomImage>();
	        	temp_arr_list.add(item_1);
	        	temp_arr_list.add(item_2);*/
	        	for(int i = 0; i < LoginController.user_list.size();i++) {
	        		System.out.println("hello + "+LoginController.user_list.get(i).getUsername()+ LoginController.user_list.size());
	        		if((LoginController.user_list.get(i).getUsername()).equals(this_user.getUsername())) {

		        		System.out.println("hello-");
	        			if(LoginController.user_list.get(i).getAlbumData() == null) {
	    	        		System.out.println("hello--");
	    	        		LoginController.user_list.get(i).setAlbumData(new ArrayList<Album>());
	    	        		LoginController.user_list.get(i).getAlbumData().add(new Album(albumname));
	    	        
	        			}
	        			for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size(); j++) {
	        				
	    	        		System.out.println("hello------");
	        				if( LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumname)) {
	        					System.out.println("success");
	        					//LoginController.user_list.get(i).getAlbumData().get(j).setImageList(temp_arr_list);
	        					for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size(); k++) {
	        						System.out.println(k);
	        						ImageView temp = new ImageView(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath());
	        						
	        						temp.setFitHeight(50);
	        						temp.setFitWidth(50);
	        						//LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getImage().setFitWidth(50);
	        						CustomImage temp_cust = new CustomImage(temp, LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getCaption(), LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath());
	        						temp_cust.setTagList(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getTagList());
	        						temp_cust.setDate(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getDate());
	        						imgList.add(temp_cust);
	        					}
	        				}
	        			}
	        			
	        		}
	        	}
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
		
		ArrayList<String> the_album_list = new ArrayList<String>();
		for(int i = 0; i < LoginController.user_list.size();i++) {
			if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
				for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
					the_album_list.add(LoginController.user_list.get(i).getAlbumData().get(j).getName());
				}
			}
		}

		album_list = FXCollections.observableArrayList();
		album_list.addAll(the_album_list);
	

		album_box.setItems(null);
		album_box.setItems(album_list);
		

		CustomImage item = tableView.getSelectionModel().getSelectedItem();
		if(item != null) {
			ArrayList<Tag> tag_list = item.getTagList();
			dropdown_list = FXCollections.observableArrayList();
			dropdown_list.addAll(tag_list);
			
			tag_delete_dropdown.setItems(null);
			tag_delete_dropdown.setItems(dropdown_list);
			

		}

		tableView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));
		
		
		System.out.println(this_user + "-----");

		
	}
	
	private void showItemInputDialog(Stage mainStage) {      
		System.out.println("HEY");
		CustomImage item = tableView.getSelectionModel().getSelectedItem();
		if(item == null) {
			return;
		}
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
		selected.setCaption(text.getText());
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
		
		
		for(int i = 0; i < LoginController.user_list.size();i++) {
			if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
				for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
					if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
						LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
						for(int k = 0; k < imgList.size(); k++) {
    						System.out.println(k);
    						CustomImage cimage = imgList.get(k);
    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 

    						simage.setTagList(cimage.getTagList());
    						simage.setDate(cimage.getDate());
    						
    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
    						//imgList.add(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k));
    					}
					}
				}
			}
			
		}
		
		//write to file
	  	//ArrayList<User> users = LoginController.user_list;
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
	public void delete_photo(ActionEvent e) {
		ObservableList<CustomImage> temp = FXCollections.observableArrayList(imgList);
		FXCollections.copy(temp, imgList);
		int index = tableView.getSelectionModel().getSelectedIndex();
		if(index < 0 || index > imgList.size()) {
			System.out.println("hello");
			return;
		}
		//CustomImage selected = imgList.get(index);
		System.out.println("hello-"+temp.size());
		temp.remove(index);
		imgList.removeAll(imgList);
		System.out.println("----"+imgList.size());
		for(int i = 0; i < temp.size(); i++) {
				imgList.add(temp.get(i));
		}
		System.out.println(imgList.size());
		
		for(int i = 0; i < LoginController.user_list.size();i++) {
			if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
				for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
					if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
						LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
						for(int k = 0; k < imgList.size(); k++) {
    						System.out.println(k);
    						CustomImage cimage = imgList.get(k);
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
				
				
				for(int i = 0; i < LoginController.user_list.size();i++) {
					if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
						for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
								LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
								for(int k = 0; k < imgList.size(); k++) {
		    						System.out.println(k);
		    						CustomImage cimage = imgList.get(k);
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
			  	//ArrayList<User> users = LoginController.user_list;
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
			

			for(int i = 0; i < LoginController.user_list.size();i++) {
				if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
					for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
						if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName)) {
							LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
							for(int k = 0; k < imgList.size(); k++) {

	    						System.out.println(k);
	    						CustomImage cimage = imgList.get(k);
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
		  	//ArrayList<User> users = LoginController.user_list;
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
			controller.start(primaryStage, this_user, albumName);
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
			controller.start(primaryStage, this_user, albumName);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
		
		
		
	}
	
	public void back(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/UserView.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			UserViewController controller = 
					loader.getController();
			controller.start(primaryStage, this_user);
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
	}
	
	public void logout(ActionEvent e) {

        tableView.setItems(null);
        

		System.out.println("hello, size is  "+ LoginController.user_list.size());
        
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
	
	public void add_to_album(ActionEvent e) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		if(index <0) {
			return;
		}
		CustomImage new_CustomImage = imgList.get(index);
		if(new_CustomImage == null) {
			return;
		}else {
			if(album_box.getValue() == null) {
				return;
			}
			String new_album = album_box.getValue().toString();
			if(new_album == null || new_album.equals(albumName)) {
				return;
			}else {
				for(int i = 0; i < LoginController.user_list.size();i++) {
					if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
						for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(new_album)) {
								//LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
								if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList() == null) {
									LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
		    						CustomImage cimage = new_CustomImage;
		    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 

		    						simage.setTagList(cimage.getTagList());
		    						simage.setDate(cimage.getDate());
		    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
								}else {
									boolean duplicate = false;
									for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size() ; k++) {
										if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(new_CustomImage.getPath())) {
											duplicate = true;
										}
			    					}
									if(duplicate == false) {
			    						CustomImage cimage = new_CustomImage;
			    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 

			    						simage.setTagList(cimage.getTagList());
			    						simage.setDate(cimage.getDate());
			    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
									}
								}
							}
						}
					}
					
				}
				
				//write to file
			  	//ArrayList<User> users = LoginController.user_list;
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
	

	public void move_to_album(ActionEvent e) {
		int index =tableView.getSelectionModel().getSelectedIndex();
		boolean moved = false;
		if(index <0) {
			return;
		}
		CustomImage new_CustomImage = imgList.get(index);
		if(new_CustomImage == null) {
			return;
		}else {
			if(album_box.getValue() == null) {
				return;
			}
			String new_album = album_box.getValue().toString();
			if(new_album == null || new_album.equals(albumName)) {
				return;
			}else {
				for(int i = 0; i < LoginController.user_list.size();i++) {
					if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())) {
						for(int j = 0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(new_album)) {
								//LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
								if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList() == null) {
									LoginController.user_list.get(i).getAlbumData().get(j).setImageList(new ArrayList<StoreableImage>());
		    						CustomImage cimage = new_CustomImage;
		    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 

		    						simage.setTagList(cimage.getTagList());
		    						simage.setDate(cimage.getDate());
		    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
								}else {
									boolean duplicate = false;
									for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size() ; k++) {
										if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(new_CustomImage.getPath())) {
											duplicate = true;
										}
			    					}
									if(duplicate == false) {
			    						CustomImage cimage = new_CustomImage;
			    						StoreableImage simage = new StoreableImage(cimage.getCaption(), cimage.getPath()); 

			    						simage.setTagList(cimage.getTagList());
			    						simage.setDate(cimage.getDate());
			    						LoginController.user_list.get(i).getAlbumData().get(j).getImageList().add(simage);
			    						moved = true;
									}
								}
							}else if((LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumName))) {

								

								boolean duplicate = false;

								for(int m = 0; m < LoginController.user_list.get(i).getAlbumData().size();m++) {
									if(LoginController.user_list.get(i).getAlbumData().get(m).getName().equals(new_album)) {
										for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(m).getImageList().size() ; k++) {
											if(LoginController.user_list.get(i).getAlbumData().get(m).getImageList().get(k).getPath().equals(new_CustomImage.getPath())) {
												System.out.println("what?");
												duplicate = true;
											}
										}
									}
								}
								if(moved == true) {
									duplicate = false;
								}
								
								if(duplicate == false) {
									System.out.println("removing");
									for(int k = 0; k < LoginController.user_list.get(i).getAlbumData().get(j).getImageList().size() ; k++) {
										if(LoginController.user_list.get(i).getAlbumData().get(j).getImageList().get(k).getPath().equals(new_CustomImage.getPath())) {
											LoginController.user_list.get(i).getAlbumData().get(j).getImageList().remove(k);
											imgList.remove(k);
										}
									}
								}
							}
						}
					}
					
				}
				
				//write to file
			  	//ArrayList<User> users = LoginController.user_list;
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
	
	
}