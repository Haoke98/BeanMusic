package com.sadam.ui4.Note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadam.ui4.R;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> noteList = new ArrayList<Note>();
    private Context context;

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
        this.notifyDataSetChanged();
    }

    public NoteAdapter() {
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        this.notifyDataSetChanged();
    }

    public void addNote(Note note) {
        this.noteList.add(note);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_note_item, parent, false);
        context = parent.getContext();
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
