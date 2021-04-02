package view;

import java.util.ArrayList;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.io.IOException;
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
//import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
//import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PhotosController {


	// widget from fxml for tableView
	@FXML	private TableView<CustomImage> tableView;
	@FXML private Button add_new_photo;
	@FXML private TextArea caption_text;
	

	//private ObservableList<String> obsList; 
	private ObservableList<CustomImage> imgList;
	public class Tag{
		private String tag_ID;
		private String tag;
		Tag(String tagID, String tag){
			this.tag_ID = tagID;
			this.tag = tag;
		}

	    public void setTagID(String ID) {
	        tag_ID = ID;
	    }

	    public String getTagID() {
	        return tag_ID;
	    }
	    
	    public void setTag(String tag) {
	        this.tag = tag;
	    }

	    public String getTag() {
	        return tag;
	    }
	}
	
	
	public class CustomImage {

	    private ImageView image;
	    private Text caption;
	    private ArrayList<Tag> tag_list;
	    CustomImage(ImageView img, Text caption) {
	        this.image = img;
	        this.caption = caption;
	        this.tag_list = new ArrayList<Tag>();
	    }

	    public void setImage(ImageView value) {
	        image = value;
	    }

	    public ImageView getImage() {
	        return image;
	    }
	    public void setCaption(Text value) {
	        caption = value;
	    }

	    public Text getCaption() {
	        return caption;
	    }
	}
		

	public void start(Stage mainStage) { 
			tableView.setEditable(true);
		    imgList = FXCollections.observableArrayList();
	        ImageView imageView = new ImageView(new Image("/app/llama.jpg"));
	        imageView.setFitHeight(50);
	        imageView.setFitWidth(50);
	        ImageView imageView2 = new ImageView(new Image("/app/llama.jpg"));
	        imageView2.setFitHeight(50);
	        imageView2.setFitWidth(50);
	        Text text = new Text("This is a picture of a llama");
	        Text text2 = new Text("This the exact same picture of a llama");
	        CustomImage item_1 = new CustomImage(imageView, text);
	        CustomImage item_2 = new CustomImage(imageView2, text2);
	        imgList.addAll(item_1, item_2);

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

		//THIS will open the filechooser so the user can select a photo, need to implement this on a different stage tho.
		/*FileChooser fileChooser = new FileChooser();
		add_new_photo.setOnAction(
	            (EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(final ActionEvent e) {
	                    File file = fileChooser.showOpenDialog(mainStage);
	                    if (file != null) {
	                        System.out.println("HELLO");
	                        
	                        Image new_image;
							try {
								new_image = new Image((file.toURI()).toURL().toString());
							    ImageView temp = new ImageView(new_image);
			                    temp.setFitHeight(50);
			            	    temp.setFitWidth(50);
			            	    CustomImage new_custom_image = new CustomImage(temp, null);
			            	    imgList.add(new_custom_image);
							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
	                        
	                    }
	                }
	            });*/
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
		//System.out.println(temp.size());
        //tableView.setItems(imgList);
		
		
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
		 try {
		        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addPhoto.fxml"));
		        Parent root1 = (Parent) fxmlLoader.load();
		        Stage stage = new Stage();
		        stage.setScene(new Scene(root1, 800, 550));
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(((Node)(event.getSource())).getScene().getWindow());
				stage.show();
		    } catch(Exception e) {
		        e.printStackTrace();
		    }

		
		
		
	}
	
	
}