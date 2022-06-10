package com.example.myapp.model.imageSlider;

/**
 * model for ImageSlider
 */
public class SliderData {

    private int imgUri;

    /**
     * set silder consrtuctor
     * @param imgUri
     */
    public SliderData(int imgUri) {
        this.imgUri = imgUri;
    }
    public int getImgUrl() {
        return imgUri;
    }
    public void setImgUrl(int imgUri) { this.imgUri = imgUri; }

}
