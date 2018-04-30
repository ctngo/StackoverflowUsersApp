package com.chrisngo.wagcodingchallenge.domain.restclient;

public interface RequestCompletedListener {
    void onRequestCompleted(String request, boolean success, String response, String error);
}
