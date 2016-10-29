package com.ricex.cartracker.android.data.manager;

import com.j256.ormlite.dao.Dao;
import com.ricex.cartracker.android.data.dao.RawReadingDao;
import com.ricex.cartracker.android.data.entity.RawReading;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class RawReadingManager extends EntityManager<RawReading> {

    private RawReadingDao readingDao;

    public RawReadingManager(RawReadingDao readingDao) {
        super(readingDao);

        this.readingDao = readingDao;
    }
}
