package com.softinit.notes;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {

        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new NoteOperationAsyncTask(noteDao, NoteOperationAsyncTask.INSERT).execute(note);
    }
    public void update(Note note) {
        new NoteOperationAsyncTask(noteDao, NoteOperationAsyncTask.UPDATE).execute(note);
    }
    public void delete(Note note) {
        new NoteOperationAsyncTask(noteDao, NoteOperationAsyncTask.DELETE).execute(note);
    }
    public void deleteAllNotes() {
        new NoteOperationAsyncTask(noteDao, NoteOperationAsyncTask.DELETE_ALL).execute((Note)null);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class NoteOperationAsyncTask extends AsyncTask<Note, Void, Void> {

        public static final int INSERT=2;
        public static final int UPDATE=4;
        public static final int DELETE=8;
        public static final int DELETE_ALL=16;

        private NoteDao noteDao;
        private int type;

        public NoteOperationAsyncTask(NoteDao noteDao, int type) {
            this.noteDao = noteDao;
            this.type = type;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            switch (type) {
                case INSERT:
                    noteDao.insert(notes[0]);
                    break;
                case UPDATE:
                    noteDao.update(notes[0]);
                    break;
                case DELETE:
                    noteDao.delete(notes[0]);
                    break;
                case DELETE_ALL:
                    noteDao.deleteAllNotes();
                    break;
            }
            return null;
        }
    }
}
