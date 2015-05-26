package e.widgynote;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Note {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_IMAGEFILEPATH = "filepath";

    private UUID mID;
    private String mTitle;
    private String mNoteText;
    private String mImageFilepath;


    public Note() {
        mID = UUID.randomUUID();
        mImageFilepath = " ";
        mTitle = "Untitled";
        mNoteText = "TEST";     //CHANGE!!!!!!!!!!!
    }

    public Note(JSONObject json) throws JSONException{
        mID = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mImageFilepath = json.getString(JSON_IMAGEFILEPATH);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getNoteText() {
        return mNoteText;
    }

    public void setNoteText(String noteText) {
        mNoteText = noteText;
    }

    public UUID getID() {
        return mID;
    }

    public void setImageFilepath(){
        mImageFilepath = mID.toString() + ".png";
    }

    public String getImageFilepath(){
        return mImageFilepath;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mID.toString());
        json.put(JSON_TITLE, mTitle.toString());
        json.put(JSON_IMAGEFILEPATH, mImageFilepath.toString());
        return json;
    }

}
