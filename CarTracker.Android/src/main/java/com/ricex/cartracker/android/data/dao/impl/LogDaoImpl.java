package com.ricex.cartracker.android.data.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.ricex.cartracker.android.data.dao.LogDao;
import com.ricex.cartracker.android.data.entity.Log;

import java.sql.SQLException;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

    protected LogDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Log.class);
    }
}
