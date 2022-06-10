package com.example.myapp.utils;

import android.util.Log;

public class ImageHandler {

    /**
     * format link string
     * @param fetchedLink
     * @return String
     */
    static public String refinedFetchedLink(String fetchedLink){
        String result = fetchedLink.replace("\\", "/");
        Log.e("refinedLink", result);
        return result;
    }
}
