package com.cyberdesignz.studyup.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyberdesignz.studyup.R;
import com.cyberdesignz.studyup.UpdateNote;
import com.cyberdesignz.studyup.info.NoteInfo;

public class NotesAdapter extends ArrayAdapter<NoteInfo> {

    private ArrayList<NoteInfo> notelist;
    private NoteInfo currentNote;
    private Context currentContext;

    public NotesAdapter(Context context, int textViewResourceId,
                        ArrayList<NoteInfo> noteList) {
        super(context, textViewResourceId, noteList);
        // TODO Auto-generated constructor stub
        this.notelist = noteList;
        this.currentContext = context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
    	final int no = notelist.size() - position - 1;
        NoteListHolder notelistHolder = null;
        if (notelist == null || (position + 1) > notelist.size()) //
            return convertView;

        currentNote = notelist.get(no);

        if (convertView == null) {

            LayoutInflater iv = (LayoutInflater) currentContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = iv.inflate(R.layout.review_note_layout_small, null);
            notelistHolder = new NoteListHolder();
            notelistHolder.subject_holder = (TextView) convertView
                    .findViewById(R.id.tv_reviewnotes_layout_subject);
            notelistHolder.topic_holder = (TextView) convertView
                    .findViewById(R.id.tv_reviewnotes_layout_topic);
            notelistHolder.date_holder = (TextView) convertView
                    .findViewById(R.id.tv_reviewnotes_layout_date);
            notelistHolder.detail_holder = (ImageView) convertView
                    .findViewById(R.id.imageView1);
            notelistHolder.has_audio = (ImageView) convertView
                    .findViewById(R.id.audio_indicator);

            notelistHolder.has_image = (ImageView) convertView
                    .findViewById(R.id.noteimage);
            convertView.setTag(notelistHolder);

        } else {

            notelistHolder = (NoteListHolder) convertView.getTag();
        }

        if (currentNote.getSubject() != null) {

            notelistHolder.subject_holder.setText(currentNote.getSubject());
        }
        if (currentNote.getTopic() != null) {

            notelistHolder.topic_holder.setText(currentNote.getTopic());
        }
        if (currentNote.getDateAdded() != null) {

            notelistHolder.date_holder.setText(currentNote.getDateAdded());
        }

        if (currentNote.getAudioNotes().length() != 0) {
            notelistHolder.has_audio.setVisibility(View.VISIBLE);

        } else {
            notelistHolder.has_audio.setVisibility(View.INVISIBLE);
        }
        if (currentNote.getNotesImage().length() != 0) {
            notelistHolder.has_image.setVisibility(View.VISIBLE);

        } else {
            notelistHolder.has_image.setVisibility(View.INVISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                onDetailClick(this, this, no, null);

            }
        });
        return convertView;
    }

    private void onDetailClick(OnClickListener onClickListener,
                               OnClickListener onClickListener2, int position, Object object) {

        Intent intent1 = new Intent(currentContext, UpdateNote.class);
        notelist.get(position).getNotesText();
        notelist.get(position).getSubject();
        notelist.get(position).getTopic();
        notelist.get(position).getId();
        notelist.get(position).getAudioNotes();
        try {
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("note", notelist.get(position).getNotesText());
            intent1.putExtra("subject", notelist.get(position).getSubject());
            intent1.putExtra("topic", notelist.get(position).getTopic());
            intent1.putExtra("note_id", notelist.get(position).getId());
            intent1.putExtra("audio_name", notelist.get(position)
                    .getAudioNotes());
            intent1.putExtra("class_name", notelist.get(position).getClassId());

            intent1.putExtra("note_image", notelist.get(position)
                    .getNotesImage());
            currentContext.startActivity(intent1);
        } catch (Exception ex) {
            Log.d("asd", "dsa");
            Log.d("asd", "dsa");

        }
    }

    public class NoteListHolder {

        TextView subject_holder;
        TextView topic_holder;
        TextView date_holder;
        ImageView detail_holder;
        ImageView has_audio;
        ImageView has_image;

    }

}
