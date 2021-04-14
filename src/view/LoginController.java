package view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

public class LoginController {
	
	@FXML Button login_button;
	@FXML TextField username;
	@FXML Button album_view;
	
	ArrayList<String> users = new ArrayList<String>();
	public static ArrayList<User> user_list = new ArrayList<User>();
	
	public void start(Stage mainStage) {
		try {

			user_list = new ArrayList<User>();
			File file = new File("src/app/users.txt");  //stores users so that they persist  
			Scanner s = new Scanner(file);
			System.out.println("opened scanner");
			while(s.hasNextLine()) {
				String k = s.nextLine();
				System.out.println(k);
				users.add(k);
				if(!(k.equals("admin"))) {
					try {
						ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data\\users\\"+k+".txt"));
						User temp = (User)ois.readObject();
						user_list.add(temp);
					}catch(IOException ero) {
						user_list.add(new User(k));
						//System.out.println(ero.getStackTrace()[0].getLineNumber() );
					}
				}
				
				
			}
			
			
			/*
				String k = s.nextLine();
				users.add(k);
				if(!(k.equals("admin"))) {
				System.out.println("user wasn't admin: "+ k);
				User temp = new User(k);
				File userData = new File("data\\users\\" + temp.getUsername() + ".txt");
				BufferedReader reader = new BufferedReader(new FileReader(userData));
				System.out.println("opened user's text file");
				String line = reader.readLine();
				ArrayList<Album> temp_album_list = new ArrayList<Album>();
				while(line != null ) {
					if(line.substring(0,6).equals("ALBUM:")) {
						System.out.println("first line was album");
						Album new_album = new Album(line.substring(6));
						line = reader.readLine();
						if(line != null) {
						while(!(line.substring(0,6).equals("ALBUM:"))) {
							if(line.substring(0,6).equals("PHOTO:")) {
								ArrayList<CustomImage> temp_image_list = new_album.getImageList();
								System.out.println("adding a photo");
								Image new_Image = new Image(line.substring(6));
								ImageView new_ImageView = new ImageView(new_Image);
								CustomImage new_CustomImage = new CustomImage(new_ImageView, null, line.substring(6));
								line = reader.readLine();
								while(!(line.substring(0,6).equals("ALBUM:")) && !(line.substring(0,6).equals("PHOTO:"))) {
									if(line.substring(0, 4).equals("TAG:")) {
										String tag_and_tag_id = line.substring(4);
										String[] tag_arr = tag_and_tag_id.split(":");
										Tag new_tag = new Tag(tag_arr[0], tag_arr[1]);
										System.out.println("added tag:"+new_tag.toString());
									    new_CustomImage.addTag(new_tag);
									}else if(line.substring(0, 5).equals("DATE:")) {
									    Calendar cal = Calendar.getInstance();
										String date_string = line.substring(5);
										if(date_string.equals("0")) {
											new_CustomImage.setDate(null);
											System.out.println("added date:0");
										}else {
											long date_long = Long.parseLong(date_string);
											cal.setTimeInMillis(date_long);
											cal.set(Calendar.MILLISECOND,0);
											new_CustomImage.setDate(cal);
											System.out.println("added date:"+date_string);
										}
									}else if(line.length()>6) {
										if(line.substring(0, 8).equals("CAPTION:")){ 
											String caption_text = line.substring(8);
											new_CustomImage.setCaption(new Text(caption_text));	
											System.out.println("added caption:"+caption_text);
										}								
									}
									line = reader.readLine();
									//ArrayList<CustomImage> temp_image_list = new_album.getImageList();
									if(line == null||line.equals("")) {
										System.out.println("adding image now to album: "+new_album.getName()+ "photo:"+new_CustomImage.getPath());

										temp_image_list.add(new_CustomImage);
										break;
									}else if(line.length()> 5) {
										if((line.substring(0,6).equals("ALBUM:")) || (line.substring(0,6).equals("PHOTO:"))) {
											System.out.println("adding image now to album: "+new_album.getName()+ "photo:"+new_CustomImage.getPath());

											temp_image_list.add(new_CustomImage);
										}
									}
									
								}
								
								//ArrayList<CustomImage> temp_image_list = new_album.getImageList();
								//temp_image_list.add(new_CustomImage);
								System.out.println("added list to album");
								new_album.setImageList(temp_image_list);
								if(line == null || line.equals("")) {
									break;
								}
							}
						}
						}
					
						
						System.out.println("added album to album list");
						temp_album_list.add(new_album);
					}
				
				
					
				}
				temp.setAlbumData(temp_album_list);
				user_list.add(temp);
				}
			}
			s.close();*/
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void Login(ActionEvent e) {
		boolean userExists = false;
		for (String u:users) {
			if(u.equals(username.getText())) {
				userExists = true;
				break;
			}
		}
		if(userExists) {
			User user = new User(username.getText());
			Node node = (Node) e.getSource();
			Stage primaryStage = (Stage) node.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();   
			if(user.getUsername().equals("admin")) {
				loader.setLocation(
						getClass().getResource("/view/adminView.fxml"));
				try {
					AnchorPane root = (AnchorPane)loader.load();
					AdminController controller = 
							loader.getController();
					controller.start(primaryStage);
					Scene scene = new Scene(root, 600, 400);
					primaryStage.setScene(scene);		
				}catch (IOException error) {
				    System.err.println(String.format("Error: %s", error.getMessage()));
				}
			}else {
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
			
			
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("User not found");
			alert.setHeaderText("Username entered does not match any on file, please try again (username is case senstive)");
			alert.showAndWait();
		}
			
	}
		
	public void albumView(ActionEvent e) {
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
}

