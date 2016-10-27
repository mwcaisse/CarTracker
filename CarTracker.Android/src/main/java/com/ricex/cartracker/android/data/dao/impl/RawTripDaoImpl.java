package com.ricex.cartracker.android.data.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.ricex.cartracker.android.data.dao.RawTripDao;
import com.ricex.cartracker.android.data.entity.RawTrip;

import java.sql.SQLException;

/**
 * Created by Mitchell on 2016-10-26.
 */
public class RawTripDaoImpl extends BaseDaoImpl<RawTrip, Long> implements RawTripDao {

    public RawTripDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RawTrip.class);
    }
}
