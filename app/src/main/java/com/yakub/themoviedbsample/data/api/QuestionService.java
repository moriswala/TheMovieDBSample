package com.yakub.themoviedbsample.data.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.stackexchange.com/2.2/questions?site=stackoverflow&tagged=android
public interface QuestionService {
  @GET("questions?site=stackoverflow")
  Flowable<QuestionResponse> loadQuestionsByTag(@Query("tagged") String tag);



}
