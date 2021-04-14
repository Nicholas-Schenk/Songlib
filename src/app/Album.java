package app;

import java.util.ArrayList;

public class Album implements java.io.Serializable{
	private ArrayList<StoreableImage> imageList;
	private String name;
	public Album(String new_name){
		this.name = new_name;
		this.imageList = new ArrayList<StoreableImage>();
	}
	
    public void setName(String new_name) {
        name = new_name;
    }

    public String getName() {
        return name;
    }
    
    public void setImageList(ArrayList<StoreableImage> imagelist) {
        this.imageList = imagelist;
    }

    public ArrayList<StoreableImage> getImageList() {
        return imageList;
    }

}
