package com.ricex.cartracker.androidrequester.request.tracker;

import com.ricex.cartracker.androidrequester.request.AbstractRequest;
import com.ricex.cartracker.androidrequester.request.ApplicationPreferences;
import com.ricex.cartracker.androidrequester.request.RequestResponse;
import com.ricex.cartracker.androidrequester.request.exception.RequestException;
import com.ricex.cartracker.androidrequester.request.type.BulkUploadResponseType;
import com.ricex.cartracker.common.viewmodel.BulkUploadResult;
import com.ricex.cartracker.common.viewmodel.ReaderLogUpload;

import java.util.List;

/**
 * Created by Mitchell on 2016-11-01.
 */
public class BulkUploadReaderLogRequest extends AbstractRequest<List<BulkUploadResult>> {

    private final List<ReaderLogUpload> readerLogs;

    /** Creates a new Bulk Upload request for the given logs
     *
     * @param applicationPreferences
     */
    public BulkUploadReaderLogRequest(ApplicationPreferences applicationPreferences, List<ReaderLogUpload> readerLogs) {
        super(applicationPreferences);
        this.readerLogs = readerLogs;
    }

    @Override
    protected RequestResponse<List<BulkUploadResult>> executeRequest() throws RequestException {
        return postForObject(serverAddress + "/log/reader/bulk", readerLogs, new BulkUploadResponseType());
    }
}
