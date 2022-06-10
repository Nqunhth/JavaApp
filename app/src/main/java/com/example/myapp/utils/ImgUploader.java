package com.example.myapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ImgUploader extends AsyncTask<Void, Void, String> {
    private static final String UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String TAG = ImgUploader.class.getSimpleName();
    private Activity mActivity;
    private Uri mImageUri;  // local Uri to upload
    private String CLIENT_ID = "2ea9bd6c41d0bf2";

    /**
     * set contructor
     * @param imageUri
     * @param activity
     */
    public ImgUploader(Uri imageUri, Activity activity) {
        this.mImageUri = imageUri;
        this.mActivity = activity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        InputStream imageIn = null;
        try {
            //Refine jpg
            Bitmap bMap = MediaStore.Images.Media.getBitmap(this.mActivity.getContentResolver(), mImageUri);;
            if(bMap != null){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                imageIn = new ByteArrayInputStream(baos.toByteArray());
            }
            else
                Log.e("null", "null");

        } catch (FileNotFoundException e) {
            Log.e(TAG, "could not open InputStream", e);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = null;
        InputStream responseIn = null;

        try {
            conn = (HttpURLConnection) new URL(UPLOAD_URL).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Authorization", "Client-ID " + CLIENT_ID);


            OutputStream out = conn.getOutputStream();
            copy(imageIn, out);
            out.flush();
            out.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseIn = conn.getInputStream();
                return onInput(responseIn);
            }
            else {
                Log.i(TAG, "responseCode=" + conn.getResponseCode());
                responseIn = conn.getErrorStream();
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(responseIn);
                while (scanner.hasNext()) {
                    sb.append(scanner.next());
                }
                Log.i(TAG, "error response: " + sb.toString());
                return null;
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error during POST", ex);
            return null;
        } finally {
            try {
                responseIn.close();
            } catch (Exception ignore) {}
            try {
                conn.disconnect();
            } catch (Exception ignore) {}
            try {
                imageIn.close();
            } catch (Exception ignore) {}
        }
    }
    private static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[8192];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    protected String onInput(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }

        JSONObject root = new JSONObject(sb.toString());
        String id = root.getJSONObject("data").getString("id");

        return "http://i.imgur.com/" + id + ".png";
    }

}
