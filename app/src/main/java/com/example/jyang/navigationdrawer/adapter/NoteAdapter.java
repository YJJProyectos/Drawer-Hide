package com.example.jyang.navigationdrawer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> notes;
    private int layout;


    public NoteAdapter(Context context, List<Note> notes, int layout) {
        this.context = context;
        this.notes  = notes;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Note getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.description = (TextView) convertView.findViewById(R.id.textViewNoteDescription);
            vh.createdAt = (TextView) convertView.findViewById(R.id.textViewNoteCreatedAt);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Note note = notes.get(position);
        vh.description.setText(note.getDescription());


        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String created = df.format(note.getCreatedAt());
        vh.createdAt.setText(created);
        return convertView;
    }

    public class ViewHolder {
        TextView description;
        TextView createdAt;
    }
}
