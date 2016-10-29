package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ricex.cartracker.android.data.dao.impl.LogDaoImpl;
import com.ricex.cartracker.common.entity.LogType;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-28.
 */
@DatabaseTable(tableName = "log", daoClass = LogDaoImpl.class)
public class Log extends ServerEntity {

    @DatabaseField
    private LogType type;

    @DatabaseField
    private String message;

    @DatabaseField
    private Date date;

}
