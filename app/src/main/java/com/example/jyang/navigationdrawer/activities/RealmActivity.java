package com.example.jyang.navigationdrawer.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jyang.navigationdrawer.R;
import com.example.jyang.navigationdrawer.adapter.BoardAdapter;
import com.example.jyang.navigationdrawer.models.Board;
import com.example.jyang.navigationdrawer.models.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>{


    @BindView(R.id.fabAddBoard)
    FloatingActionButton fab;
    private Realm realm;
    private ListView listView;
    private BoardAdapter adapter;
    private RealmResults<Board> boards;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Realm");
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showalertForCreatingBoard("titulo", "texto");
            }
        });

        realm = Realm.getDefaultInstance();

        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);
        listView = (ListView) findViewById(R.id.listViewBoard);
        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RealmActivity.this, NoteActivity.class);
                intent.putExtra("id", boards.get(position).getId());
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Log.d("item menu", item.toString());
        if ( item.getItemId() == android.R.id.home ) {
            super.onBackPressed();
        }
        return true;
    }
    // CRUD actions

    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();

        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Board board = new Board(boardName);
                realm.copyToRealm(board);
            }
        });*/
    }

    /* dialogs */
    private void showalertForCreatingBoard(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);
        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if ( boardName.length() > 0) {
                    createNewBoard(boardName);
                } else {
                    Toast.makeText(getApplicationContext(), "Nombre requerido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onChange(RealmResults<Board> boards) {
        adapter.notifyDataSetChanged();
    }
}
