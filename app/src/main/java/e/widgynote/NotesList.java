package e.widgynote;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class NotesList {
    private static final String FILENAME = "notes.json";

    private static NotesList sNotesList;
    private Context mAppContext;

    private ArrayList<Note> mNotes;
    private NoteJSONSerializer mSerializer;


    private NotesList(Context appContext){
        mAppContext = appContext;
        mSerializer = new NoteJSONSerializer(mAppContext, FILENAME);

        try{
            mNotes = mSerializer.loadNotes();
        } catch (Exception e){
            mNotes = new ArrayList<Note>();
        }

//        //TEST Notes List - DELETE
//        for(int i = 0; i < 10; i++){
//            Note n = new Note();
//            n.setTitle("Note " + i);
//            n.setNoteText("La La La\n Yey :)\n" + i);
//            mNotes.add(n);
//        }
    }

    public static NotesList get(Context c){
        if(sNotesList == null){
            sNotesList = new NotesList(c.getApplicationContext());
        }
        return sNotesList;
    }

//    public void deleteNote(int index){
//        mNotes.
//    }

    public void addNote(Note n){
        String title = "Note " + mNotes.size();     //DELETE
        n.setTitle(title);        //DELETE
        mNotes.add(n);
    }

    public Note getNote(UUID id){
        for(Note n : mNotes){
            if(n.getID().equals(id))
                return n;
        }
        return null;
    }

    public void deleteNote(Note n){
        //delete stored note image
        File dir = mAppContext.getDir("savedNotes", Context.MODE_PRIVATE);
        File file = new File(dir, n.getImageFilepath());
        if(file.exists()) {
            file.delete();
        }

        sNotesList.mNotes.remove(n);
    }

//    public Note getNote(int position){
//        return mNotes.get(position);
//    }

    public ArrayList<Note> getNotes(){
        return mNotes;
    }


    public boolean saveNotes(){
        try{
            mSerializer.saveNotes(mNotes);
            return true;
        } catch (Exception e){
            System.out.println("Unable to save notes");
            return false;
        }
    }

    public Note getTop() {
        return mNotes.get(0);
    }
}
