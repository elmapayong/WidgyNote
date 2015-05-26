package e.widgynote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;


import java.util.UUID;

public class SingleNoteFragment extends Fragment {
    private Note mNote;
    private EditText mMainTextEditText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        //mNote = NotesList.get(getActivity()).getNote(noteID);

//        //get screen dimensions
//        WindowManager wm = (WindowManager) getActivity().getBaseContext().getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics display = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(display);
//
//        //get actionbar's height
//        int actionBarHeight = 0;
//        TypedValue tv = new TypedValue();
//        if(getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
//            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//        }
//
//        setupDrawing(display.widthPixels, display.heightPixels - actionBarHeight);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_single_note, parent, false);

//        mMainTextEditText = (EditText)v.findViewById(R.id.single_note_textTextView);
//        mMainTextEditText.setBackground(null);
//
//        mMainTextEditText.setText(mNote.getNoteText());

        return v;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//
//
//        //one way to get screen dimensions
//        WindowManager wm = (WindowManager) getActivity().getBaseContext().getSystemService(Context.WINDOW_SERVICE);
////        Display screen = wm.getDefaultDisplay();
////        Point size = new Point();
////        screen.getSize(size);
//
//        //get actionbarheight
//        TypedValue tv = new TypedValue();
//        getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
//        int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
//
//        //another way to get screen dimensions
//        DisplayMetrics metrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(metrics);
////        mMainTextEditText.setText(size.x + "  " + size.y
////        + "\n" + metrics.heightPixels + " " + metrics.widthPixels
////        + "\n" + actionBarHeight);
//
//    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        NotesList.get(getActivity()).saveNotes();
//    }
}

