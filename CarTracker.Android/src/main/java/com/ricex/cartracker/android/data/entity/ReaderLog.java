package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ricex.cartracker.android.data.dao.impl.ReaderLogDaoImpl;
import com.ricex.cartracker.common.entity.LogType;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-28.
 */
@DatabaseTable(tableName = "reader_log", daoClass = ReaderLogDaoImpl.class)
public class ReaderLog extends ServerEntity {

    @DatabaseField
    private LogType type;

    @DatabaseField
    private String tag;

    @DatabaseField
    private String message;

    @DatabaseField
    private Date date;


    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
