package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML ListView<String> userList;
	@FXML TextField selectedUser;
	@FXML TextField newUser;
	@FXML Button createUser;
	@FXML Button deleteUser;
	@FXML Button logout;
	
	public void start(Stage mainStage) {
		try {
			File file = new File("src/app/users.txt");  //stores users so that they persist  
			Scanner s = new Scanner(file);
			while(s.hasNextLine()) {
				String k = s.nextLine();
				if (!k.equals("")) userList.getItems().add(k);
			}
			s.close();
		}catch (Exception e) {
			System.out.println("ERROR");
		}
		userList
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showItemInputDialog(mainStage));
	}
	
	private void showItemInputDialog(Stage mainStage) {                
		String username = userList.getSelectionModel().getSelectedItem();
		selectedUser.setText(username);
	}
	
	public void createUser(ActionEvent e) {
		String newUsername = newUser.getText();
		if (newUsername == null || newUsername.equals("")) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No username");
			alert.setHeaderText("Cannot create a user without a username");
			alert.showAndWait();
			return;
		}
		boolean duplicateUser = false;
		for(String username:userList.getItems()) {
			if (username.equals(newUsername)){
				duplicateUser = true;
				break;
			}		
		}
		if(!duplicateUser) {
			//add user to userlist and add txt file for user
			try {
				File userFile = new File("data\\users\\" + newUsername + ".txt");
				userFile.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/app/users.txt", true));
			    writer.append("\n");
			    writer.append(newUsername);
			    writer.close();
			    File userPhotosDirectory = new File("data\\photos\\" + newUsername);
			    userPhotosDirectory.mkdir();
			    userList.getItems().add(newUsername);
			    newUser.setText("");
			}catch(IOException error) {
			      System.out.println(error);
			    }
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate username");
			alert.setHeaderText("Cannot create a user with that username becuase one already exists");
			alert.showAndWait();
		}
	}
	public void deleteUser(ActionEvent e) {
		if(!selectedUser.getText().equals("")) {
			try {
				File userFile = new File("data\\users\\" + selectedUser.getText() + ".txt");
				userFile.delete();
				
				PrintWriter p = new PrintWriter("src/app/users.txt");
				for(String username:userList.getItems()) {
					if (username.equals(selectedUser.getText())) continue;
					p.print(username);
					p.print("\n");
				}
				p.close();
			    p.close();
			    File userPhotoDir = new File("data\\photos\\" + selectedUser.getText());
			    String[] photos = userPhotoDir.list();
			    if(photos != null) {
				    for(String photoFile: photos) {
				    	File photo = new File(userPhotoDir.getPath(), photoFile);
				    	photo.delete();
				    }
				}
			    userPhotoDir.delete();
			    
			    userList.getItems().remove(selectedUser.getText());
			}catch(IOException error) {
			      System.out.println(error);
			}
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
