package com.example.jyang.navigationdrawer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.models.Board;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class BoardAdapter extends BaseAdapter {

    private Context context;
    private List<Board> boards;
    private int layout;


    public BoardAdapter(Context context, List<Board> boards, int layout) {
        this.context = context;
        this.boards = boards;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            vh.title = (TextView) convertView.findViewById(R.id.textViewBoardTitle);
            vh.notes = (TextView) convertView.findViewById(R.id.textViewBoardNote);
            vh.createdAt = (TextView) convertView.findViewById(R.id.textViewBoardDate);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Board board = boards.get(position);
        vh.title.setText(board.getTitle());
        int numberOfNotes = board.getNotes().size();
        String textForNotes = (numberOfNotes == 1) ? numberOfNotes + " Note":  numberOfNotes + " Notes";
        vh.notes.setText(textForNotes);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String created = df.format(board.getCreatedAt());

        vh.createdAt.setText(created);
        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView notes;
        TextView createdAt;
    }
}
