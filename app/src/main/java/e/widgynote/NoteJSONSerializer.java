package e.widgynote;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class NoteJSONSerializer {
    private Context context;
    private String filename;

    public NoteJSONSerializer(Context c, String f){
        context = c;
        filename = f;
    }

    public void saveNotes(ArrayList<Note> notes) throws JSONException, IOException{
        JSONArray array = new JSONArray();

        //put every note into array
        for(Note n : notes){
            array.put(n.toJSON());
        }

        //write file to disk
        Writer writer = null;
        try{
            OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    public ArrayList<Note> loadNotes() throws IOException, JSONException{
        ArrayList<Note> notes = new ArrayList<Note>();
        BufferedReader reader = null;

        try{
            //open and read file into string builder
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                //line breaks are taken out and irrelevant
                jsonString.append(line);
            }
            //parse json with jsontokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            //build array of notes from json objects
            for(int i = 0; i < array.length(); i++){
                notes.add(new Note(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e){
            //ignore.. happens when starting from scratch
        } finally {
            if(reader != null){
                reader.close();
            }
        }
        return notes;
    }
}

