package view;

import app.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PhotoSearchController {
	private User user;
	public void setUser(User u) {
		this.user = u;
	}
	
	public User gerUser() {
		return this.user;
	}
	
	@FXML TextField username;
	public void start(Stage mainStage, User u) {
		setUser(u);
		username.setText(user.getUsername());
	}
}
