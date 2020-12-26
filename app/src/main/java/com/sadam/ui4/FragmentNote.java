package com.sadam.ui4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sadam.ui4.Data.MySqLiteOpenHelper;
import com.sadam.ui4.Data.User;
import com.sadam.ui4.Note.Note;
import com.sadam.ui4.Note.NoteAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentNote#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNote extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NoteAdapter noteAdapter;
    private User currentUser;
    private MySqLiteOpenHelper mySqLiteOpenHelper;

    public FragmentNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNote.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNote newInstance(String param1, String param2) {
        FragmentNote fragment = new FragmentNote();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        RecyclerView recyclerViewNote = view.findViewById(R.id.recyclerview_note);
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter();
        recyclerViewNote.setAdapter(noteAdapter);
        noteAdapter.setNoteList(initNotes());
        FloatingActionButton btnAddNote = view.findViewById(R.id.btn_add_note);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.drawable.comment);
                builder.setTitle("this is title!");
                final EditText editTextNoteContent = new EditText(getContext());
                builder.setView(editTextNoteContent);
                builder.setPositiveButton("确定添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content_text = editTextNoteContent.getText().toString();
                        if (content_text == null) {
                            Toast.makeText(getContext(), "content_text is null!", Toast.LENGTH_LONG).show();
                        } else if (content_text.equals("")) {
                            Toast.makeText(getContext(), "content_text is empty!", Toast.LENGTH_LONG).show();
                        } else {
                            Note note = new Note(currentUser, content_text);
                            note.save();
                            noteAdapter.addNote(note);
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    public ArrayList<Note> initNotes() {
        mySqLiteOpenHelper = new MySqLiteOpenHelper(getContext(), MySqLiteOpenHelper.DATABASE_NAME, null, MySqLiteOpenHelper.DATABASE_VERSION);
        currentUser = ActivityLogin.getCurrentUserFromSharedPrefrences(getContext(), mySqLiteOpenHelper);
        ArrayList<Note> notes = currentUser.getNoteArrayList();
        if (notes.isEmpty()) {
            Note note = new Note(currentUser, "this is an example note for your learning how to use it.");
            note.save();
            notes.add(note);
            Note note1 = new Note(currentUser, "this is example2: you can take notice here anytime.");
            note1.save();
            notes.add(note1);
        }
        return notes;
    }
}