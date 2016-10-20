package com.ricex.cartracker.android.service.logger;

/**
 * Created by Mitchell on 2016-10-19.
 */
public interface ServiceLogger {

    public void debug(String tag, String message);

    public void info(String tag, String message);

    public void warn(String tag, String message);

    public void warn(String tag, String message, Throwable ex);

    public void error(String tag, String message);

    public void error(String tag, String message, Throwable ex);

}
