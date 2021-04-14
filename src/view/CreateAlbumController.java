package view;

import java.io.BufferedWriter;
import view.LoginController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import app.Album;
import app.CustomImage;
import app.Tag;
import app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateAlbumController {
	private User user;
	public void setUser(User u) {
		this.user = u;
	}
	
	public User gerUser() {
		return this.user;
	}
	
	@FXML Label username;
	@FXML TextField newAlbumName;
	@FXML Button createNewAlbum;
	@FXML Button logout;
	@FXML Button back;
	public void start(Stage mainStage, User u) {
		setUser(u);
		username.setText(username.getText() + " " + user.getUsername());
	}
	
	public void create(ActionEvent e) {
		if(user.getAlbumList().contains(newAlbumName.getText())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate album");
			alert.setHeaderText("Cannot create an album with that name becuase one already exists");
			alert.showAndWait();
		}
		else {
			try {
				File userData = new File("data\\users\\" + user.getUsername() + ".txt");
				userData.createNewFile();
				//File userData2 = new File("data\\users\\" + user.getUsername() + "2.txt");
				//userData2.createNewFile();
				System.out.print("created file " + user.getUsername());
			    user.addAlbum(newAlbumName.getText());
			    //user.setAlbumData(album_data);
			    System.out.println(LoginController.user_list.size());
			    for(int z = 0; z< LoginController.user_list.size();z++) {
				    if(LoginController.user_list.get(z).getUsername().equals(user.getUsername())) {	
				    	//LoginController.user_list.remove(z);
				    	//LoginController.user_list.add(z, user);
				    	if(LoginController.user_list.get(z).getAlbumData() == null) {
				    		System.out.println("IT WAS NULL??");
				    		LoginController.user_list.get(z).setAlbumData(new ArrayList<Album>());
				    	}
				    	LoginController.user_list.get(z).getAlbumData().add(new Album(newAlbumName.getText()));
				    	System.out.println("IT WORKED??");
				    	for(int i = 0; i < LoginController.user_list.get(z).getAlbumData().size(); i++) {
				    		System.out.println(LoginController.user_list.get(z).getAlbumData().get(i).getImageList());
				    	}
				    	//System.out.println(LoginController.user_list.get(z).getAlbumData().get(0).getName());
				    	ArrayList<User> users = LoginController.user_list;
				    	for(User i: users) {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\users\\"+i.getUsername()+".txt"));
							oos.writeObject(i);
							oos.close();
				    	}
				
				    	

				    	
				    	
				    	
				    	
				    }
			    }
			}catch(IOException error) {
			      System.out.println(error);
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
				photosController.start(primaryStage, user, newAlbumName.getText());
				Scene scene = new Scene(root, 800, 550);
				primaryStage.setScene(scene);
				primaryStage.show();
			}catch (IOException error) {
			    System.err.println(String.format("Error: %s", error.getMessage()));
			}
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
			controller.start(primaryStage, user);
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
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
		}
	}
}
