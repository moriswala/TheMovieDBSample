package com.yakub.themoviedbsample.data.api;

import com.google.gson.annotations.SerializedName;
import com.yakub.themoviedbsample.data.model.Question;
import java.util.List;

public class QuestionResponse {
  @SerializedName("items")
  private List<Question> questions;

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }
}
