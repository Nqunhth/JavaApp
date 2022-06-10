package com.example.myapp.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

public class Fetcher {

    /**
     * fetch data from Web API
     * @param url
     * @param onCompleteListener
     */
    static public void fetchDataFrom(String url, Listener<String> onCompleteListener){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                FetchData fetchData = new FetchData(url);
                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        //Log.e("FetchData", result);
                        onCompleteListener.on(result);
                    }
                }
            }
        });
    }
}
