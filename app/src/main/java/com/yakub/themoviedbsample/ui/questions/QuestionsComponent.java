package com.yakub.themoviedbsample.ui.questions;

import com.yakub.themoviedbsample.data.QuestionRepositoryComponent;
import com.yakub.themoviedbsample.ui.base.ActivityScope;
import com.yakub.themoviedbsample.util.schedulers.SchedulerModule;
import dagger.Component;

@ActivityScope
@Component(modules = {QuestionsPresenterModule.class, SchedulerModule.class}, dependencies = {
    QuestionRepositoryComponent.class
})
public interface QuestionsComponent {
  void inject(QuestionsActivity questionsActivity);
}
