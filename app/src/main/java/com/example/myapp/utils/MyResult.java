package com.example.myapp.utils;

import org.json.JSONArray;

public final class MyResult {
    private final int status;
    private final JSONArray jsonArray;

    /**
     * Set contructor
     * @param _status
     * @param _jsonArray
     */
    public MyResult(int _status, JSONArray _jsonArray) {
        this.status = _status;
        this.jsonArray =_jsonArray;
    }

    public int getStatus() {
        return status;
    }

    public JSONArray getJSONArray() {
        return jsonArray;
    }
}
