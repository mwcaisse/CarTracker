package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-23.
 */
public abstract class Entity {

    public static final String PROPERTY_ID = "id";
    @DatabaseField(generatedId = true)
    private long id;

    public static final String PROPERTY_CREATE_DATE = "createDate";
    @DatabaseField(canBeNull = false)
    private Date createDate;

    public static final String PROPERTY_UPDATE_DATE = "updateDate";
    @DatabaseField(canBeNull = false)
    private Date updateDate;

    public Entity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
