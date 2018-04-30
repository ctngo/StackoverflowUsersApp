package com.chrisngo.wagcodingchallenge.util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParamStringBuilder {
    public static String buildParamString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        try {
            for (String key : params.keySet()) {
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(params.get(key), "UTF-8"));
                result.append("&");
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("ParamStringBuilder", "UnsupportedEncodingException: " + e.getMessage());
        }
        if(result.length() > 0) {
            result.deleteCharAt(result.length()-1);
        }
        return result.toString();
    }
}
