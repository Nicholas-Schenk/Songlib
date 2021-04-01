package view;

/*import java.util.ArrayList;
import javafx.stage.FileChooser;
import java.io.File;*/
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

public class PhotosController {


	// widget from fxml for tableView
	@FXML	private TableView<CustomImage> tableView;
	@FXML private Button add_new_photo;
	@FXML private TextArea caption_text;
	

	//private ObservableList<String> obsList; 
	private ObservableList<CustomImage> imgList;
	public class CustomImage {

	    private ImageView image;
	    private Text caption;
	    CustomImage(ImageView img, Text caption) {
	        this.image = img;
	        this.caption = caption;
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
	                    }
	                }
	            });
		 */
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
	
	
}