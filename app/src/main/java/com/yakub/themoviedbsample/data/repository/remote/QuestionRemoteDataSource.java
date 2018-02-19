package com.yakub.themoviedbsample.data.repository.remote;

import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.api.QuestionResponse;
import com.yakub.themoviedbsample.data.api.QuestionService;
import com.yakub.themoviedbsample.data.model.Question;
import com.yakub.themoviedbsample.data.repository.QuestionDataSource;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

public class QuestionRemoteDataSource implements QuestionDataSource {
  private QuestionService questionService;

  @Inject
  public QuestionRemoteDataSource(QuestionService questionService) {
    this.questionService = questionService;
  }

  @Override
  public Flowable<List<Question>> loadQuestions(boolean forceRemote) {
    return questionService.loadQuestionsByTag(Config.ANDROID_QUESTION_TAG).map(QuestionResponse::getQuestions);
  }

  @Override
  public void addQuestion(Question question) {
    //Currently, we do not need this for remote source.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public void clearData() {
    //Currently, we do not need this for remote source.
    throw new UnsupportedOperationException("Unsupported operation");
  }
}
