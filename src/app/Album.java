package app;

import java.util.ArrayList;

public class Album {
	private ArrayList<CustomImage> imageList;
	private String name;
	public Album(String new_name){
		this.name = new_name;
		this.imageList = new ArrayList<CustomImage>();
	}
	
    public void setName(String new_name) {
        name = new_name;
    }

    public String getName() {
        return name;
    }
    
    public void setImageList(ArrayList<CustomImage> imagelist) {
        this.imageList = imagelist;
    }

    public ArrayList<CustomImage> getImageList() {
        return imageList;
    }

}
