package org.addhen.birudo.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @author Henry Addo
 */
public abstract class Entity {

    @SerializedName("id")
    private Long _id;

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }
}
