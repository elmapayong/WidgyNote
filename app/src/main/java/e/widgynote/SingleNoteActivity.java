package e.widgynote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;


public class SingleNoteActivity extends ActionBarActivity {
    public static final String EXTRA_NOTE_ID = "noteWidget.NOTE_ID";
    private Note note;
    private boolean isText = false;
    private EditText textbox;
    private InputMethodManager inputManager;
    float touchX, touchY;



    @Override
    public void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show back button with title of current note
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UUID noteID = (UUID)getIntent().getSerializableExtra(EXTRA_NOTE_ID);
        note = NotesList.get(this.getApplicationContext()).getNote(noteID);
        setTitle(note.getTitle());

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.activity_main);

        if(fragment == null){
            fragment = new SingleNoteFragment();
            manager.beginTransaction().add(R.id.activity_main, fragment).commit();
        }
    }


    @Override
    public void onStart(){
        super.onStart();


        DrawingView drawView = (DrawingView)findViewById(R.id.drawing_view);
        //make a new image for the note if there isn't one already stored
        if(note.getImageFilepath().equals(" ")){
            note.setImageFilepath();
        }
        //restore note
        else{
            File dir = getApplicationContext().getDir("savedNotes", Context.MODE_PRIVATE);
            File path = new File(dir, note.getImageFilepath());
//            Toast savedToast2 = Toast.makeText(getApplicationContext(), "checked if file exists",
//                    Toast.LENGTH_SHORT);
//            savedToast2.show();
            try{
                FileInputStream fis = new FileInputStream(path);
                Bitmap b = BitmapFactory.decodeStream(fis);
                fis.close();
                drawView.setCanvasBitmap(b);
//                Toast savedToast = Toast.makeText(getApplicationContext(), "Restored!",
//                        Toast.LENGTH_SHORT);
//                savedToast.show();
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        //to have the keyboard pop up when textbox is focused
        inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        textbox = new EditText(this);
        textbox.setFocusable(true);
        textbox.setFocusableInTouchMode(true);
        textbox.setVisibility(View.GONE);
        textbox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    textbox.setVisibility(View.VISIBLE);
                    inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                    //Toast.makeText(getApplicationContext(), "has focus", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(), "textbox doesn't have focus", Toast.LENGTH_SHORT).show();
                    textbox.setVisibility(View.GONE);
                    textbox.setText("");
                    inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        //shows edittext box if writing is enabled
        DrawingView dv = (DrawingView)findViewById(R.id.drawing_view);
        dv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(isText) {
                    touchX = event.getX();
                    touchY = event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        textbox.setX(touchX);
                        textbox.setY(touchY);
                        textbox.setVisibility(View.VISIBLE);
                        textbox.requestFocus();
                        Toast.makeText(getApplicationContext(), "onTouch", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Clear focus", Toast.LENGTH_SHORT).show();
                    textbox.clearFocus();
                }
                return false;   //keeps ontouch instructions in drawingview class
            }
        });

        RelativeLayout l = (RelativeLayout)findViewById(R.id.single_note);
        l.addView(textbox);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_note_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        DrawingView drawView = (DrawingView)findViewById(R.id.drawing_view);

        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.draw_btn:
                drawView.setErase(false);
                break;
            case R.id.text_btn:
                isText = !isText;
                Toast.makeText(getApplicationContext(), (isText == true? "true": "false"), Toast.LENGTH_SHORT).show();
                if(isText){
                    textbox.requestFocus();
                }
                else{
                    textbox.clearFocus();
                }
                break;
            case R.id.erase_btn:
                drawView.setErase(true);
                break;
            default:
                break;
        }
        return true;
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(resultCode != Activity.RESULT_OK) return;
//    }

    @Override
    public void onPause(){
        super.onPause();
        save();
    }

//    @Override
//    public void onDestroy(){
//        save();
//        super.onDestroy();
//    }

    public void save(){
        //save drawing
        DrawingView drawView = (DrawingView)findViewById(R.id.drawing_view);
        drawView.setDrawingCacheEnabled(true);

        File dir = getApplicationContext().getDir("savedNotes", Context.MODE_PRIVATE);
        File path = new File(dir, note.getImageFilepath());

        try{
            FileOutputStream fos = new FileOutputStream(path);
            drawView.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
//            Toast savedToast = Toast.makeText(getApplicationContext(), "Saved!",
//                    Toast.LENGTH_SHORT);
//            savedToast.show();
        } catch (Exception e){
            e.printStackTrace();
        }

        drawView.setDrawingCacheEnabled(false);

        //save notes to update listfragment
        NotesList.get(getApplicationContext()).saveNotes();
    }


}

