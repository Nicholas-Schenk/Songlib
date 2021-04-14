package app;

public class Tag implements java.io.Serializable{
	private String tag_ID;
	private String tag;
	public Tag(String tagID, String tag){
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
    public String toString() {
    	return this.tag_ID + ":" + this.tag;
    }
}
