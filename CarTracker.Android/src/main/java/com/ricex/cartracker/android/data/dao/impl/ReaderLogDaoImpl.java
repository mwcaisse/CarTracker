package com.ricex.cartracker.android.data.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.ricex.cartracker.android.data.dao.ReaderLogDao;
import com.ricex.cartracker.android.data.entity.ReaderLog;

import java.sql.SQLException;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class ReaderLogDaoImpl extends BaseDaoImpl<ReaderLog, Long> implements ReaderLogDao {

    public ReaderLogDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ReaderLog.class);
    }
}
