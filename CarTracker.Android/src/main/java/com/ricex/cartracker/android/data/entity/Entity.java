package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Mitchell on 2016-10-23.
 */
public abstract class Entity {

    @DatabaseField(id = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
