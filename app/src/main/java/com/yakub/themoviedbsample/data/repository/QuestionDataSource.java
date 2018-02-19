package com.yakub.themoviedbsample.data.repository;

import com.yakub.themoviedbsample.data.model.Question;
import io.reactivex.Flowable;
import java.util.List;

public interface QuestionDataSource {
  Flowable<List<Question>> loadQuestions(boolean forceRemote);

  void addQuestion(Question question);

  void clearData();
}
