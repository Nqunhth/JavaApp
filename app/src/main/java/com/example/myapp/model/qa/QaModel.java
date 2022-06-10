package com.example.myapp.model.qa;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * model for Qa
 */
public class QaModel {

    String qaId, qaMainSub, qaSubSub_a, qaSubSub_b, qaQ, qaA;

    /**
     * set empty constructor
     */
    public QaModel(){
        this.qaId = "";
        this.qaMainSub = "";
        this.qaSubSub_a = "";
        this.qaSubSub_b = "";
        this.qaQ = "";
        this.qaA = "";
    }

    /**
     * set data constructor for QaModel
     * @param qaId
     * @param qaMainSub
     * @param qaSubSub_a
     * @param qaSubSub_b
     * @param qaQ
     * @param qaA
     */
    public QaModel(String qaId, String qaMainSub, String qaSubSub_a, String qaSubSub_b, String qaQ, String qaA) {
        this.qaId = qaId;
        this.qaMainSub = qaMainSub;
        this.qaSubSub_a = qaSubSub_a;
        this.qaSubSub_b = qaSubSub_b;
        this.qaQ = qaQ;
        this.qaA = qaA;
    }

    /**
     * set data constructor for QaModel by using jsonObject
     * @param jsonObject
     * @throws JSONException
     */
    public QaModel(JSONObject jsonObject) throws JSONException {
        this.qaId = jsonObject.get("qa_id").toString();
        this.qaMainSub = jsonObject.get("main_subject").toString();
        this.qaSubSub_a = jsonObject.get("sub_subject_a").toString();
        this.qaSubSub_b = jsonObject.get("sub_subject_b").toString();
        this.qaQ = jsonObject.get("question").toString();
        this.qaA = jsonObject.get("answer").toString();

    }
    public String getQaId(){
        return this.qaId;
    }
    public String getQaMainSub(){
        return this.qaMainSub;
    }
    public String getQaSubSub_a(){
        return this.qaSubSub_a;
    }
    public String getQaSubSub_b(){
        return this.qaSubSub_b;
    }
    public String getQaQ(){
        return this.qaQ;
    }
    public String getQaA(){
        return this.qaA;
    }
}
