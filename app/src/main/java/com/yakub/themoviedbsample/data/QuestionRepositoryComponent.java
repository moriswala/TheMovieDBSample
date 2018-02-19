package com.yakub.themoviedbsample.data;

import com.yakub.themoviedbsample.data.repository.QuestionRepository;
import com.yakub.themoviedbsample.AppModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { QuestionRepositoryModule.class, AppModule.class, ApiServiceModule.class,
    DatabaseModule.class})
public interface QuestionRepositoryComponent {
  QuestionRepository provideQuestionRepository();
}
