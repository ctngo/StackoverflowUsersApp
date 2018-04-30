package com.chrisngo.wagcodingchallenge.presenter;

import android.content.Context;
import android.util.Log;

import com.chrisngo.wagcodingchallenge.domain.model.ResponseItems;
import com.chrisngo.wagcodingchallenge.domain.restclient.RequestCompletedListener;
import com.chrisngo.wagcodingchallenge.domain.restclient.users.StackExchangeUserRequestHelper;
import com.chrisngo.wagcodingchallenge.view.activity.UserView;
import com.google.gson.Gson;


public class UserPresenter {
    private Context context;
    private StackExchangeUserRequestHelper requestHelper;
    private UserView userView;
    private StackExchangeRequestCompletedListener requestCompletedListener =
            new StackExchangeRequestCompletedListener();
    private static final String TAG = UserPresenter.class.getSimpleName();


    public UserPresenter(Context context, UserView userView) {
        this.context = context;
        this.userView = userView;
        this.requestHelper = new StackExchangeUserRequestHelper(context, requestCompletedListener);
    }

    /**
     * Gets list of first page of users on Stackoverflow
     */
    public void fetchStackOverflowUsers() {

        requestHelper.requestUsersListString();
    }

    class StackExchangeRequestCompletedListener implements RequestCompletedListener{
        @Override
        public void onRequestCompleted(String request, boolean success, String response, String error) {
            Gson gson = new Gson();
            if (success) {
                ResponseItems items = gson.fromJson(response, ResponseItems.class);
                userView.updateInfo(items.getUsers());
            }
            else
                Log.e(TAG, error + " on " + request);
        }
    }

}
