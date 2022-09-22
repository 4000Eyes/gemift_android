package com.gift.gemift.Model;

import java.io.Serializable;

public class ImageUploadResponse implements Serializable {

    private String fileName;

    private String image;

    private String imagePath;

    public ImageUploadResponse(String fileName, String image) {
        this.fileName = fileName;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
