package com.sadam.ui4.FragmentSelfPage.FragmentNote;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadam.ui4.Data.Note;
import com.sadam.ui4.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView content;
    private TextView changedTime;
    private Context context;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.note_content);
        changedTime = itemView.findViewById(R.id.note_changedtime);
        context = itemView.getContext();
    }

    public void bind(Note note) {
        content.setText(note.getContent());
        changedTime.setText(note.getStringChangedTime());
    }
}
