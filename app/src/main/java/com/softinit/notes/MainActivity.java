package com.softinit.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_NOTE_REQUEST = 1;

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAddnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiate();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
            }
        });

        btnAddnote.setOnClickListener(this);
    }

    private void initiate() {
        recyclerView = findViewById(R.id.rv_view);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        btnAddnote = findViewById(R.id.btn_add_note);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_note:
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            noteViewModel.insert(
                    new Note(
                            data.getStringExtra(AddNoteActivity.EXTRA_TITLE),
                            data.getStringExtra(AddNoteActivity.EXTRA_DESC),
                            data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1)
                    )
            );
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } else {    //default value is RESULT_CANCELLED
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
