package com.yakub.themoviedbsample;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.yakub.themoviedbsample.data.DaggerQuestionRepositoryComponent;
import com.yakub.themoviedbsample.data.QuestionRepositoryComponent;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class AndroidApplication extends Application {

  private QuestionRepositoryComponent repositoryComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initializeDependencies();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      Stetho.initializeWithDefaults(this);
    }

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }
    LeakCanary.install(this);
  }

  private void initializeDependencies() {
    repositoryComponent = DaggerQuestionRepositoryComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public QuestionRepositoryComponent getQuestionRepositoryComponent() {
    return repositoryComponent;
  }
}
