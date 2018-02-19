package com.yakub.themoviedbsample.ui.questions;

import com.yakub.themoviedbsample.data.model.Question;
import com.yakub.themoviedbsample.ui.base.BasePresenter;
import java.util.List;

public interface QuestionsContract {
  interface View {
    void showQuestions(List<Question> questions);

    void clearQuestions();

    void showNoDataMessage();

    void showErrorMessage(String error);

    void showQuestionDetail(Question question);

    void stopLoadingIndicator();

    void showEmptySearchResult();
  }

  interface Presenter extends BasePresenter<QuestionsContract.View> {
    void loadQuestions(boolean onlineRequired);

    void getQuestion(long questionId);

    void search(String questionTitle);
  }
}
