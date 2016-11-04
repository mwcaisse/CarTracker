package com.ricex.cartracker.android.data.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.ricex.cartracker.android.data.dao.RawReadingDao;
import com.ricex.cartracker.android.data.entity.RawReading;

import java.sql.SQLException;

/**
 * Created by Mitchell on 2016-10-26.
 */
public class RawReadingDaoImpl extends BaseDaoImpl<RawReading, Long> implements RawReadingDao {

    public RawReadingDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RawReading.class);
    }
}
