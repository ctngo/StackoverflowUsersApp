package com.chrisngo.wagcodingchallenge.domain.restClient;

public interface RequestCompletedListener {
    void onRequestCompleted(String request, boolean success, String response, String error);
}
