package com.gift.gemift.Model;

import java.io.Serializable;

public class UpdateImagePathResponse implements Serializable {

    private int request_id;

    private String entity_id;

    private String image_url;

    private String image_type;

    public UpdateImagePathResponse(int request_id, String entity_id, String image_url, String image_type) {
        this.request_id = request_id;
        this.entity_id = entity_id;
        this.image_url = image_url;
        this.image_type = image_type;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }
}
