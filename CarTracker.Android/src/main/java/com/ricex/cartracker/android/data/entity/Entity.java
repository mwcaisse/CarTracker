package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-23.
 */
public abstract class Entity {

    @DatabaseField(id = true, generatedId = true)
    private long id;

    @DatabaseField(canBeNull = false)
    private Date createDate;

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
