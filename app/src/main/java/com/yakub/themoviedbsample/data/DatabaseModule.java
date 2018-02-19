package com.yakub.themoviedbsample.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.yakub.themoviedbsample.data.database.QuestionDao;
import com.yakub.themoviedbsample.data.database.StackOverflowDb;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class DatabaseModule {
  private static final String DATABASE = "database_name";

  @Provides
  @Named(DATABASE)
  String provideDatabaseName() {
    return Config.DATABASE_NAME;
  }

  @Provides
  @Singleton
  StackOverflowDb provideStackOverflowDao(Context context, @Named(DATABASE) String databaseName) {
    return Room.databaseBuilder(context, StackOverflowDb.class, databaseName).build();
  }

  @Provides
  @Singleton
  QuestionDao provideQuestionDao(StackOverflowDb stackOverflowDb) {
    return stackOverflowDb.questionDao();
  }
}
