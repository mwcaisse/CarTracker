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
public class WebServiceLogger extends AbstractServiceLogger implements  RequestCallback<ReaderLog> {

    private CarTrackerRequestFactory requestFactory;

    public WebServiceLogger(ApplicationPreferences settings) {
        this(new CarTrackerRequestFactory(settings));
    }

    public WebServiceLogger(CarTrackerRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
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
