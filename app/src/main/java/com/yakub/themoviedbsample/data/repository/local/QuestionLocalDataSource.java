package com.yakub.themoviedbsample.data.repository.local;

import com.yakub.themoviedbsample.data.database.QuestionDao;
import com.yakub.themoviedbsample.data.model.Question;
import com.yakub.themoviedbsample.data.repository.QuestionDataSource;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

public class QuestionLocalDataSource implements QuestionDataSource {

  private QuestionDao questionDao;

  @Inject
  public QuestionLocalDataSource(QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Override
  public Flowable<List<Question>> loadQuestions(boolean forceRemote) {
    return questionDao.getAllQuestions();
  }

  @Override
  public void addQuestion(Question question) {
    // Insert new one
    questionDao.insert(question);
  }

  @Override
  public void clearData() {
    // Clear old data
    questionDao.deleteAll();
  }
}
