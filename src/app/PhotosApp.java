package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
*/
import javafx.application.Application;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.LoginController;
//import javafx.stage.WindowEvent;
import view.PhotosController;
import view.LoginController;
public class PhotosApp extends Application {	

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/LoginView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		LoginController controller = 
				loader.getController();
		controller.start(primaryStage);
		Scene scene = new Scene(root, 800, 550);
		primaryStage.setScene(scene);
		//ComboBox combo = new ComboBox;
		primaryStage.setOnCloseRequest(event -> {
			try{
				ArrayList<User> users = LoginController.user_list;
				for(User i: users) {
					if(!(i.getUsername().equals("admin"))) {
					PrintWriter p = new PrintWriter("data\\users\\" + i.getUsername()+".txt");
					ArrayList<Album> temp_album_list = i.getAlbumData();
					if(temp_album_list!=null) {
					for(Album j: temp_album_list) {
						p.print("ALBUM:"+j.getName()+"\n");
						ArrayList<CustomImage> temp_img_list = j.getImageList();
						for(CustomImage k: temp_img_list) {
							p.print("PHOTO:"+k.getPath()+"\n");
							ArrayList<Tag> temp_tag_list = k.getTagList();
							for(Tag n: temp_tag_list) {
								p.print("TAG:"+n.toString()+"\n");
							}
							p.print("DATE:"+ k.getDate().getTimeInMillis()+"\n");
							p.print("CAPTION:"+k.getCaption().getText()+"\n");
						}
					}
					}
					p.close();
				}
				}
			}catch(IOException e) {
				System.out.println(e);
			}
		});
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		//System.out.println("when does this print?");
		

	}

}