package app;

import java.util.ArrayList;

public class User implements java.io.Serializable{
	String username;
	ArrayList<String> albumList;
	ArrayList<Album> albumData;
	public User() {
		this.username = "";
		albumList = new ArrayList<String>();
		albumData = new ArrayList<Album>();
	}
	
	public User(String username) {
		this.username = username;
		albumList = new ArrayList<String>();
	}
	
	public User(String username, ArrayList<String> albumList) {
		this.username = username;
		this.albumList = albumList;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public ArrayList<String> getAlbumList() {
		return this.albumList;
	}
	
	public void setAlbumList(ArrayList<String> albumList) {
		this.albumList = albumList;
	}
	
	public void addAlbum(String albumname) {
		this.albumList.add(albumname);
	}
	
	public void deleteAlbum(String albumName) {
		this.albumList.remove(albumName);
	}
	
	public ArrayList<Album> getAlbumData() {
		return this.albumData;
	}
	
	public void setAlbumData(ArrayList<Album> albumData) {
		this.albumData = albumData;
	}
	
	public String toString() {
		return this.username;
	}
	
	
}
