package view;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import app.Album;
import app.CustomImage;
import app.Tag;
import app.User;

public class UserViewController {
	private User user;
	
	@FXML Label username;
	@FXML ListView<String> albums;
	@FXML TextField selectedAlbum;
	@FXML TextField newName;
	@FXML Button openAlbum;
	@FXML Button renameAlbum;
	@FXML Button deleteAlbum;
	@FXML Button createAlbum;
	@FXML Button photoSearch;
	@FXML Button logout;
	
	public void setUser(User u) {
		this.user = u;
	}
	
	public User gerUser() {
		return this.user;
	}
	User this_user;
	public void start(Stage mainStage, User u) {
		this_user = u;
		setUser(u);
		username.setText(u.toString());
		//try {

				try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data\\users\\"+user.getUsername()+".txt"));
						User temp = (User)ois.readObject();
						for(int i = 0; i < temp.getAlbumData().size(); i++) {
							albums.getItems().add(temp.getAlbumData().get(i).getName());
							user.addAlbum(temp.getAlbumData().get(i).getName());
						}
						ois.close();
						
						//user_list.add(temp);
					}catch(Exception ero) {
						//user_list.add(new User(k));
						//System.out.println(ero.getStackTrace()[0].getLineNumber() );
					}
		albums
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));
		
	}
	
	private void showItemInputDialog(Stage mainStage) {                
		String album = albums.getSelectionModel().getSelectedItem();
		selectedAlbum.setText(album);
	}
	
	public void open(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/photos.fxml"));
		try {
			AnchorPane root = (AnchorPane)loader.load();
			PhotosController photosController = 
					loader.getController();
			photosController.start(primaryStage, user, selectedAlbum.getText());
			Scene scene = new Scene(root, 800, 550);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
		}
	}
	
	public void rename(ActionEvent e) {
		String oldName = selectedAlbum.getText();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Rename " + oldName + " to " + newName.getText());
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			
			//this section isn't working
				albums.getItems().remove(oldName);
				albums.getItems().add(newName.getText());
				user.deleteAlbum(oldName);
				user.addAlbum(newName.getText());
				albums.getSelectionModel().select(newName.getText());
				
				
				//stuff to make persistent update
				for(int i = 0; i < LoginController.user_list.size();i++) {
					if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())){
						for(int j=0; j < LoginController.user_list.get(i).getAlbumData().size();j++) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(oldName)) {
								LoginController.user_list.get(i).getAlbumData().get(j).setName(newName.getText());
							}
						}
					}
				}
				try {
				ArrayList<User> users = LoginController.user_list;
					for(User i: users) {
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\users\\"+i.getUsername()+".txt"));
					oos.writeObject(i);
					oos.close();
		    	}
				}catch(Exception ero) {
					System.out.println(ero);
				}
		}
	}
	public void delete() {
		String albumToDelete = selectedAlbum.getText();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Delete " + albumToDelete + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			//this section isn't working 
				albums.getItems().remove(albumToDelete);
				user.deleteAlbum(albumToDelete);
				albums.getSelectionModel().clearSelection();
				
				
				
				
				
				//stuff to make persisnent changes
				for(int i = 0; i < LoginController.user_list.size();i++) {
					if(LoginController.user_list.get(i).getUsername().equals(this_user.getUsername())){
						LoginController.user_list.get(i).deleteAlbum(albumToDelete);
						System.out.println("album list size"+LoginController.user_list.get(i).getAlbumData().size());
						for(int j = 0; j<LoginController.user_list.get(i).getAlbumData().size();j++ ) {
							if(LoginController.user_list.get(i).getAlbumData().get(j).getName().equals(albumToDelete)) {
								LoginController.user_list.get(i).getAlbumData().remove(j);
								System.out.println("after delete"+LoginController.user_list.get(i).getAlbumData().size());
							}
						}
					}
				}
				try {
					ArrayList<User> users = LoginController.user_list;
						for(User i: users) {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\users\\"+i.getUsername()+".txt"));
						oos.writeObject(i);
						oos.close();
			    	}
					}catch(Exception ero) {
						System.out.println(ero);
					}
			
		}
		
	}
	public void createAlbum(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		try {
		loader.setLocation(
				getClass().getResource("/view/createAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		CreateAlbumController controller = 
				loader.getController();
		controller.start(primaryStage, user);
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
		}
	}
	public void photoSearch(ActionEvent e) {
		Node node = (Node) e.getSource();
		Stage primaryStage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();   
		try {
		loader.setLocation(
				getClass().getResource("/view/photoSearch.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		PhotoSearchController controller = 
				loader.getController();
		controller.start(primaryStage, user);
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
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
