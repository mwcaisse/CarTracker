package com.ricex.cartracker.android.service;

/**
 * Created by Mitchell on 2/17/2016.
 */
public interface ServiceLogger {

    public void info(String tag, String message);

    public void warn(String tag, String message);

    public void error(String tag, String message);

    public void error(String tag, String message, Exception e);
}
