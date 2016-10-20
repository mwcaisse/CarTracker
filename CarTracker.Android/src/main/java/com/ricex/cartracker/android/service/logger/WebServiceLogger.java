package com.ricex.cartracker.android.service.logger;

import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestCallback;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.tracker.CarTrackerRequestFactory;
import com.ricex.cartracker.androidrequester.request.tracker.CreateReaderLogRequest;
import com.ricex.cartracker.common.entity.LogType;
import com.ricex.cartracker.common.entity.ReaderLog;

import java.util.Date;

/**
 * Created by Mitchell on 2016-10-19.
 */
public class WebServiceLogger implements ServiceLogger, RequestCallback<ReaderLog> {

    private CarTrackerRequestFactory requestFactory;

    public WebServiceLogger(ApplicationPreferences settings) {
        this(new CarTrackerRequestFactory(settings));
    }

    public WebServiceLogger(CarTrackerRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

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
        String msg = message = "\n" + ex.getMessage() + "\n" + ex.getStackTrace();
    }

    public void log(LogType type, String tag, String message) {
        ReaderLog log = new ReaderLog();

        log.setMessage(message);
        log.setType(type);
        log.setDate(new Date());

        createReaderLog(log);
    }

    protected void createReaderLog(ReaderLog readerLog) {
        CreateReaderLogRequest request = requestFactory.createReaderLogRequest(readerLog);
        request.executeAsync(this);
    }

    @Override
    public void onSuccess(ReaderLog results) {
        //do nothing atm
    }

    @Override
    public void onFailure(RequestException e, RequestResponse<ReaderLog> response) {
        // do nothing atm
    }

    @Override
    public void onError(Exception e) {
        //do nothing atm
    }
}
