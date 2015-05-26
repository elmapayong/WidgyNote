package e.widgynote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAllNotesFragment extends ListFragment{
    private ArrayList<Note> mNotes;
    private int positionClicked;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.notes_title);
        mNotes = NotesList.get(getActivity().getApplicationContext()).getNotes();

        NoteAdapter adapter = new NoteAdapter(mNotes);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getActivity());
                positionClicked = position;

                deleteDialog.setTitle("Delete note");
                deleteDialog.setMessage("Are you sure you want to delete this note?");

                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note n = ((NoteAdapter)getListAdapter()).getItem(positionClicked);
                        NotesList.get(getActivity()).deleteNote(n);
                        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                deleteDialog.show();
                return true;
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Note n = ((NoteAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), SingleNoteActivity.class);
        i.putExtra(SingleNoteActivity.EXTRA_NOTE_ID, n.getID());
        startActivity(i);
    }

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id){
//        Toast savedToast = Toast.makeText(getActivity().getApplicationContext(), "Drawing saved to Gallery!",
//                                Toast.LENGTH_SHORT);
//        savedToast.show();
//        return true;
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((NoteAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class NoteAdapter extends ArrayAdapter<Note> {
        public NoteAdapter(ArrayList<Note> notes){
            super(getActivity(), 0, notes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_all_notes_item, null);
            }

            Note n = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.list_all_notes_titleTextView);
            titleTextView.setText(n.getTitle());

            return convertView;
        }
    }
}

