package com.ricex.cartracker.android.service.logger;

import android.util.Log;

import com.ricex.cartracker.common.entity.LogType;

/**
 * Created by Mitchell on 2016-10-29.
 */
public abstract class AbstractServiceLogger implements ServiceLogger {

    @Override
    public void debug(String tag, String message) {
        log(LogType.DEBUG, tag, message);
    }

    @Override
    public void info(String tag, String message) {
        log(LogType.INFO, tag, message);
    }

    @Override
    public void warn(String tag, String message) {
        log(LogType.WARN, tag, message);
    }

    @Override
    public void warn(String tag, String message, Throwable ex) {
        log(LogType.WARN, tag, message, ex);
    }

    @Override
    public void error(String tag, String message) {
        log(LogType.ERROR, tag, message);
    }

    @Override
    public void error(String tag, String message, Throwable ex) {
        log(LogType.ERROR, tag, message, ex);
    }

    public void log(LogType type, String  tag, String message, Throwable ex) {
        String msg = message + "\n" + ex.getMessage() + "\n" + Log.getStackTraceString(ex);
        log (type, tag, msg);
    }

    public abstract void log(LogType type, String tag, String message);

}
