package view;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public void start(Stage mainStage, User u) {
		setUser(u);
		username.setText(u.toString());
		try {
		File userData = new File("data\\users\\" + user.getUsername() + ".txt");
		Scanner reader = new Scanner(userData);
		while(reader.hasNextLine()) {
			String data = reader.nextLine();
			if(data.length() > 5 && data.substring(0, 6).equals("ALBUM:")) {
				albums.getItems().add(data.substring(6));
				user.addAlbum(data.substring(6));
				
			}
		}
		reader.close();
		}catch (IOException error) {
		    System.err.println(String.format("Error: %s", error.getMessage()));
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
			try {
				File userData = new File("data\\users\\" + user.getUsername() + ".txt");
				BufferedReader reader = new BufferedReader(new FileReader(userData));
				String line = reader.readLine();
				String oldContent = "";
		        while (line != null) 
		        {
		            oldContent = oldContent + line + System.lineSeparator();
		            line = reader.readLine();
		        }
		        String newContent = oldContent.replaceAll("ALBUM:" + oldName, "ALBUM:" + newName.getText());
		        FileWriter writer = new FileWriter(userData);
		        writer.write(newContent);
				reader.close();
				writer.close();
				}catch (IOException error) {
				    System.err.println(String.format("Error: %s", error.getMessage()));
				}
				albums.getItems().remove(oldName);
				albums.getItems().add(newName.getText());
				user.deleteAlbum(oldName);
				user.addAlbum(newName.getText());
				albums.getSelectionModel().select(newName.getText());
		}
	}
	public void delete() {
		String albumToDelete = selectedAlbum.getText();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Delete " + albumToDelete + "?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				File userData = new File("data\\users\\" + user.getUsername() + ".txt");
				BufferedReader reader = new BufferedReader(new FileReader(userData));
				String line = reader.readLine();
				String userDataContent = "";
		        while (line != null) {
		        	if(line.length() > 5 && (line).equals("ALBUM:" + albumToDelete)) {
		        		line = reader.readLine();
		        		while(line != null && (line.length() < 6 || !line.substring(0,6).equals("ALBUM:"))) {
		        			line = reader.readLine();
		        		}
		        	}
		        	userDataContent = userDataContent + line + System.lineSeparator();
		            line = reader.readLine();
		        }
		        String newContent = userDataContent.replaceAll("null", "");
		        FileWriter writer = new FileWriter(userData);
		        writer.write(newContent);
				reader.close();
				writer.close();
				}catch (IOException error) {
				    System.err.println(String.format("Error: %s", error.getMessage()));
				}
				albums.getItems().remove(albumToDelete);
				user.deleteAlbum(albumToDelete);
				albums.getSelectionModel().clearSelection();
			
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
		//ComboBox combo = new ComboBox;
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
		//ComboBox combo = new ComboBox;
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
		//ComboBox combo = new ComboBox;
		primaryStage.show();
		}catch(IOException error) {
		      System.out.println(error);
		}
	}
}
