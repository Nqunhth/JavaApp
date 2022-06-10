package com.example.myapp.model.teacher;

import com.example.myapp.utils.ImageHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * model for Teacher
 */
public class TeacherModel {
    String teacherId, teacherName, teacherGender, teacherImg, teacherAchieve, chargeOf, teacherDescription;

    /**
     * set data constructor for TeacherModel
     * @param id
     * @param name
     * @param gender
     * @param img
     * @param achieve
     * @param charge
     * @param description
     */
    public TeacherModel(String id, String name, String gender, String img, String achieve, String charge, String description) {
        this.teacherId = id;
        this.teacherName = name;
        this.teacherGender = gender;
        this.teacherImg = img;
        this.teacherAchieve = achieve;
        this.chargeOf = charge;
        this.teacherDescription = description;
    }

    /**
     * set data constructor for TeacherModel by using jsonObject
     * @param jsonObject
     * @throws JSONException
     */
    public TeacherModel(JSONObject jsonObject) throws JSONException {
        this.teacherId = jsonObject.get("teacher_id").toString();
        this.teacherName = jsonObject.get("teacher_name").toString();
        this.teacherGender = jsonObject.get("teacher_gender").toString();;
        this.teacherImg = ImageHandler.refinedFetchedLink(jsonObject.get("teacher_img").toString());
        this.teacherAchieve = jsonObject.get("teacher_achieve").toString();;
        this.chargeOf = jsonObject.get("in_charge_of").toString();;
        this.teacherDescription = jsonObject.get("teacher_description").toString();;
    }
    public String getTeacherId(){
        return this.teacherId;
    }
    public String getTeacherName(){
        return this.teacherName;
    }
    public String getTeacherGender(){
        return this.teacherGender;
    }
    public String getTeacherImg(){
        return this.teacherImg;
    }
    public String getTeacherAchieve(){
        return this.teacherAchieve;
    }
    public String getChargeOf(){
        return this.chargeOf;
    }
    public String getTeacherDescription(){
        return this.teacherDescription;
    }
}
