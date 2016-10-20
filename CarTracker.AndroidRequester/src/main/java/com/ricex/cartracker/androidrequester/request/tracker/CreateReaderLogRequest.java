package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.Request;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.ReaderLogResponseType;
import com.ricex.cartracker.common.entity.ReaderLog;

/**
 * Created by Mitchell on 2016-10-19.
 */
public class CreateReaderLogRequest extends AbstractRequest<ReaderLog> {

    private final ReaderLog readerLog;

    /** Creates a new instance of the request with the reader log to create
     *
     * @param applicationPreferences
     * @param readerLog
     */

    public CreateReaderLogRequest(ApplicationPreferences applicationPreferences, ReaderLog readerLog) {
        super(applicationPreferences);
        this.readerLog = readerLog;
    }

    protected RequestResponse<ReaderLog> executeRequest() throws RequestException {
        return postForObject(serverAddress + "/log/reader/", readerLog, new ReaderLogResponseType());
    }
}
