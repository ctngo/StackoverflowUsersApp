package com.chrisngo.wagcodingchallenge.presenter;

import android.content.Context;
import android.util.Log;

import com.chrisngo.wagcodingchallenge.domain.model.ResponseItems;
import com.chrisngo.wagcodingchallenge.domain.restClient.RequestCompletedListener;
import com.chrisngo.wagcodingchallenge.domain.restClient.StackExchangeUserRequestHelper;
import com.chrisngo.wagcodingchallenge.util.Constants;
import com.chrisngo.wagcodingchallenge.util.ParamStringBuilder;
import com.chrisngo.wagcodingchallenge.view.activity.UserView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UserPresenter {
    private Context context;
    private StackExchangeUserRequestHelper requestHelper;
    private UserView userView;
    private StackExchangeRequestCompletedListener requestCompletedListener =
            new StackExchangeRequestCompletedListener();
    private static final String TAG = UserPresenter.class.getSimpleName();
    private static final String BASE_STACK_OVERFLOW_USERS_URL = Constants.BASE_STACK_OVERFLOW_USERS_URL;


    public UserPresenter(Context context, UserView userView) {
        this.context = context;
        this.userView = userView;
        this.requestHelper = new StackExchangeUserRequestHelper(context, requestCompletedListener);
    }

    public void fetchStackOverflowUsers() {
        // add params
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        String formattedParams = ParamStringBuilder.buildParamString(params);
        requestHelper.requestUsersListString(BASE_STACK_OVERFLOW_USERS_URL + formattedParams);
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
