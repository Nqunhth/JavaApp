package com.example.myapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {

    /**
     * turn String to JSONObject
     * @param tableName
     * @param resource
     * @return MyResult
     */
    static public MyResult turnToJSONObject(String tableName, String resource){
        JSONArray restfulJsonArray = null;
        Integer status = 0;
        try {
            status = 1;
            JSONObject resultJsonObject = new JSONObject(resource);
            restfulJsonArray = resultJsonObject.getJSONArray(tableName);
        } catch (JSONException e) {
            status = 0;
            e.printStackTrace();
        }
        MyResult result = new MyResult(status, restfulJsonArray);
        return result;
    }
}