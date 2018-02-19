package com.yakub.themoviedbsample.data.api;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
  private static final String ACCEPT_HEADER = "Accept";
  private static final String API_KEY = "api_key";
  private static final String JSON_TYPE = "application/json";

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    request = request.newBuilder().addHeader(ACCEPT_HEADER, JSON_TYPE)
            .addHeader(API_KEY, "6177565665ecbc9cc6b09fbe70f78fec").build();
    return chain.proceed(request);
  }
}
