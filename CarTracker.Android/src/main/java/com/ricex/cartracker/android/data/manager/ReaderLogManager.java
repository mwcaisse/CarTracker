package com.ricex.cartracker.android.data.manager;

import com.ricex.cartracker.android.data.dao.ReaderLogDao;
import com.ricex.cartracker.android.data.entity.ReaderLog;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class ReaderLogManager extends EntityManager<ReaderLog> {

    private ReaderLogDao readerLogDao;

    public ReaderLogManager(ReaderLogDao readerLogDao) {
        super(readerLogDao);

        this.readerLogDao = readerLogDao;
    }
}
