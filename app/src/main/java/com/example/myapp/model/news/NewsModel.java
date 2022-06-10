package com.example.myapp.model.news;

import com.example.myapp.utils.ImageHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * model for News
 */
public class NewsModel {
    String postId, authorId, postUpdate, postTitle, postContent, postImg, display_place, authorName;

    /**
     * set data constructor for NewsModel
     * @param postId
     * @param authorId
     * @param postUpdate
     * @param postTitle
     * @param postContent
     * @param postImg
     * @param display_place
     * @param authorName
     */
    public NewsModel(String postId, String authorId, String postUpdate, String postTitle, String postContent, String postImg, String display_place, String authorName) {
        this.postId = postId;
        this.authorId = authorId;
        this.postUpdate = postUpdate;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImg = postImg;
        this.display_place = display_place;
        this.authorName = authorName;
    }

    /**
     * set data constructor for NewsModel by using jsonObject
     * @param jsonObject
     * @throws JSONException
     */
    public NewsModel(JSONObject jsonObject) throws JSONException {
        this.postId = jsonObject.get("post_id").toString();
        this.authorId = jsonObject.get("author_id").toString();
        this.postUpdate = jsonObject.get("post_update").toString();
        this.postTitle = jsonObject.get("post_title").toString();
        this.postContent = jsonObject.get("post_content").toString();
        this.postImg = ImageHandler.refinedFetchedLink(jsonObject.get("post_img").toString());
        this.display_place = jsonObject.get("display_place").toString();
        this.authorName= jsonObject.get("name").toString();

    }
    public String getPostId(){
        return this.postId;
    }
    public String getPostAuthor(){
        return this.authorId;
    }
    public String getPostUpdate(){
        return this.postUpdate;
    }
    public String getPostTitle(){
        return this.postTitle;
    }
    public String getPostContent(){
        return this.postContent;
    }
    public String getPostImg(){
        return this.postImg;
    }
    public String getDisplay_place(){
        return this.display_place;
    }
    public String getAuthorName() {
        return this.authorName;
    }
}
