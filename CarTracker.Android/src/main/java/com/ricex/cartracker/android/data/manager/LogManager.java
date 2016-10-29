package com.ricex.cartracker.android.data.manager;

import com.j256.ormlite.dao.Dao;
import com.ricex.cartracker.android.data.dao.LogDao;
import com.ricex.cartracker.android.data.entity.Log;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class LogManager extends EntityManager<Log> {

    private LogDao logDao;

    protected LogManager(LogDao logDao) {
        super(logDao);

        this.logDao = logDao;
    }
}
