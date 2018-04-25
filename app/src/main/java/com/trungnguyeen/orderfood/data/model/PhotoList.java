package com.trungnguyeen.orderfood.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by trungnguyeen on 4/22/18.
 */

public class PhotoList implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("guid")
    @Expose
    private String guid;

    /**
     * No args constructor for use in serialization
     *
     */
    public PhotoList() {
    }

    /**
     *
     * @param guid
     * @param id
     * @param caption
     */
    public PhotoList(Integer id, String caption, String guid) {
        super();
        this.id = id;
        this.caption = caption;
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
