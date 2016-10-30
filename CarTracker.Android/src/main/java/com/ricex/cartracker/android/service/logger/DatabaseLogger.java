package com.ricex.cartracker.android.service.logger;

import com.ricex.cartracker.android.data.entity.ReaderLog;
import com.ricex.cartracker.android.data.manager.ReaderLogManager;
import com.ricex.cartracker.android.data.util.DatabaseHelper;
import com.ricex.cartracker.common.entity.LogType;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-29.
 */
public class DatabaseLogger extends AbstractServiceLogger {

    private ReaderLogManager readerLogManager;

    private DatabaseHelper databaseHelper;

    public DatabaseLogger(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

        readerLogManager = databaseHelper.getReaderLogManager();
    }

    public void log(LogType type, String tag, String message) {
        ReaderLog log = new ReaderLog();

        log.setType(type);
        log.setTag(tag);
        log.setMessage(message);
        log.setDate(new Date());

        readerLogManager.create(log);
    }
}
