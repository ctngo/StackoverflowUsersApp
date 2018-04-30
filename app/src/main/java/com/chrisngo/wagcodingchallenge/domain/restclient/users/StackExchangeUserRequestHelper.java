package com.chrisngo.wagcodingchallenge.domain.restclient.users;

import android.content.Context;

import com.chrisngo.wagcodingchallenge.domain.restclient.RequestCompletedListener;
import com.chrisngo.wagcodingchallenge.domain.restclient.StackExchangeRequestHelperBase;
import com.chrisngo.wagcodingchallenge.util.Constants;
import com.chrisngo.wagcodingchallenge.util.ParamStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class StackExchangeUserRequestHelper {
    private Context context;
    private RequestCompletedListener requestCompletedListener;
    private static final String TAG = StackExchangeUserRequestHelper.class.getSimpleName();
    private static final String BASE_STACK_OVERFLOW_USERS_URL = Constants.BASE_STACK_OVERFLOW_USERS_URL;


    public StackExchangeUserRequestHelper(Context context) {
        this.context = context;
    }

    public StackExchangeUserRequestHelper(Context context, RequestCompletedListener requestCompletedListener) {
        this.context = context;
        this.requestCompletedListener = requestCompletedListener;
    }

    public Context getContext() {
        return context;
    }

    public RequestCompletedListener getRequestCompletedListener() {
        return requestCompletedListener;
    }

    /**
     * Gets list of first page of users from StackExchange API
     */
    public void requestUsersListString() {
        // add params
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        String formattedParams = ParamStringBuilder.buildParamString(params);
        StackExchangeRequestHelperBase requestHelper = new StackExchangeRequestHelperBase(getContext(), getRequestCompletedListener());
        requestHelper.requestStringGet(BASE_STACK_OVERFLOW_USERS_URL + formattedParams);
    }



}
