package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ricex.cartracker.android.data.dao.impl.RawReadingDaoImpl;
import com.ricex.cartracker.android.data.dao.impl.RawTripDaoImpl;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-23.
 */
@DatabaseTable(tableName = "trip", daoClass = RawTripDaoImpl.class)
public class RawTrip extends ServerEntity {


    public static final String PROPERTY_START_DATE = "startDate";
    @DatabaseField(canBeNull = false)
    private Date startDate;

    public static final String PROPERTY_END_DATE = "endDate";
    @DatabaseField
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
