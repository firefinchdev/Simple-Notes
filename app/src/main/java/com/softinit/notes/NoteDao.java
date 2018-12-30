package com.softinit.notes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


// General rule to create a Dao for each entity(each table)
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE from notes")
    void deleteAllNotes();

    @Query("select * from notes order by priority desc")
    LiveData<List<Note>> getAllNotes();
}
