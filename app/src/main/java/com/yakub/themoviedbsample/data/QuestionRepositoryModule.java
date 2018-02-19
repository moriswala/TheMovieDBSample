package com.yakub.themoviedbsample.data;

import com.yakub.themoviedbsample.data.repository.Local;
import com.yakub.themoviedbsample.data.repository.QuestionDataSource;
import com.yakub.themoviedbsample.data.repository.Remote;
import com.yakub.themoviedbsample.data.repository.local.QuestionLocalDataSource;
import com.yakub.themoviedbsample.data.repository.remote.QuestionRemoteDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class QuestionRepositoryModule {

  @Provides
  @Local
  @Singleton
  public QuestionDataSource provideLocalDataSource(QuestionLocalDataSource questionLocalDataSource) {
    return questionLocalDataSource;
  }

  @Provides
  @Remote
  @Singleton
  public QuestionDataSource provideRemoteDataSource(QuestionRemoteDataSource questionRemoteDataSource) {
    return questionRemoteDataSource;
  }

}
