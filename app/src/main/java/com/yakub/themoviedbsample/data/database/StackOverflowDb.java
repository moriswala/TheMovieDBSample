package com.yakub.themoviedbsample.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.yakub.themoviedbsample.data.model.Question;

@Database(entities = Question.class, version = 1)
public abstract class StackOverflowDb extends RoomDatabase {

  public abstract QuestionDao questionDao();
}
