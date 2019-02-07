package com.example.jyang.navigationdrawer.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.adapter.NoteAdapter;
import com.example.jyang.navigationdrawer.models.Board;
import com.example.jyang.navigationdrawer.models.Note;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();

        if ( getIntent().getExtras() != null) {
            boardId = getIntent().getExtras().getInt("id");
        }
        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        notes = board.getNotes();
        this.setTitle(board.getTitle());
        fab = (FloatingActionButton) findViewById(R.id.fabAddNote);
        listView = (ListView) findViewById(R.id.listViewNote);
        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);
        listView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showalertForCreatingNote("NOTA", "MENSAJE");
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = getWindow();
            //w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Log.d("item menu", item.toString());
        if ( item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
        }
        return true;
    }

    /* dialogs */
    private void showalertForCreatingNote(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);
        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteName = input.getText().toString().trim();
                if ( noteName.length() > 0) {
                    createNewNote(noteName);
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre requerido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createNewNote(String noteName) {
        realm.beginTransaction();
        Note note = new Note(noteName);
        realm.copyToRealm(note);
        board.getNotes().add(note);
        realm.commitTransaction();
    }
}
