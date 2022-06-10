package com.example.myapp.model.student;

import com.example.myapp.utils.ImageHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * model for ExStudent
 */
public class StudentModel {
    String exstId, exstName, exstImg, exstAchieve, exstQuote;

    /**
     * set data constructor for ExStudentModel
     * @param id
     * @param name
     * @param img
     * @param achieve
     * @param quote
     */
    public StudentModel(String id, String name, String img, String achieve, String quote) {
        this.exstId = id;
        this.exstName = name;
        this.exstImg = img;
        this.exstAchieve = achieve;
        this.exstQuote = quote;
    }

    /**
     * set data constructor for ExStudentModel by using jsonObject
     * @param jsonObject
     * @throws JSONException
     */
    public StudentModel(JSONObject jsonObject) throws JSONException {
        this.exstId = jsonObject.get("exst_id").toString();
        this.exstName = jsonObject.get("exst_name").toString();
        this.exstImg = ImageHandler.refinedFetchedLink(jsonObject.get("exst_img").toString());
        this.exstAchieve = jsonObject.get("exst_achieve").toString();
        this.exstQuote = jsonObject.get("exst_quote").toString();
    }
    public String getExstId(){
        return this.exstId;
    }
    public String getExstName(){
        return this.exstName;
    }
    public String getExstImg(){
        return this.exstImg;
    }
    public String getExstAchieve(){
        return this.exstAchieve;
    }
    public String getExstQuote(){
        return this.exstQuote;
    }

}
