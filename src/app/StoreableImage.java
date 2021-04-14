package app;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import app.Tag;

public class StoreableImage implements java.io.Serializable{

    //private ImageView image;
    private String caption;
    private ArrayList<Tag> tag_list;
    private Calendar date;
    private String path;
    public StoreableImage(String caption, String imgpath) { //ImageView img
        //this.image = img;
        this.caption = caption;
        this.tag_list = new ArrayList<Tag>();
        this.path = imgpath;
    }
    public void setPath(String value) {
        path = value;
    }

    public String getPath() {
        return path;
    }  
    public void setCaption(String value) {
        caption = value;
    }

    public String getCaption() {
        return caption;
    }
    public void addTag(Tag tag){
    	this.tag_list.add(tag);
    }
    public void removeTag(Tag tag) {
    	this.tag_list.remove(tag);
    }
    public ArrayList<Tag> getTagList(){
    	return this.tag_list;
    }
    public void setTagList(ArrayList<Tag> new_tag_list){
    	this.tag_list = new_tag_list;
    }
    public void setDate(Calendar date) {
    	this.date = date;
    }
    public Calendar getDate() {
    	return this.date;
    }
    public String getStringDate() {
	    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
	    if(this.date ==  null) return null;
	    return df.format(this.date.getTime());
    }
}
