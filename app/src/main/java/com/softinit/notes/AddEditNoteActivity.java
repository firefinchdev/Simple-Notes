package com.softinit.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = BuildConfig.APPLICATION_ID.concat("EXTRA_ID");
    public static final String EXTRA_TITLE = BuildConfig.APPLICATION_ID.concat("EXTRA_TITLE");
    public static final String EXTRA_DESC = BuildConfig.APPLICATION_ID.concat("EXTRA_DESC");
    public static final String EXTRA_PRIORITY = BuildConfig.APPLICATION_ID.concat("EXTRA_PRIORITY");

    private ActionBar actionBar;

    private EditText etTitle;
    private EditText etDesc;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initialize();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
        } else {
            setTitle("Edit Note");
        }

    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String desc = etDesc.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please insert Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESC, desc);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data); //Indicates that activity's work was success
        finish();

    }

    private void initialize() {
        acquireIds();
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle(R.string.edit_note_title);
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDesc.setText(intent.getStringExtra(EXTRA_DESC));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle(R.string.add_note_title);
        }
    }

    private void acquireIds() {
        actionBar = getSupportActionBar();

        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note: saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
