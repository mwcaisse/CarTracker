package com.ricex.cartracker.androidrequester.request.exception;

/**
 * Created by Mitchell on 2017-09-20.
 */

public class BadRequestException  extends RequestException{

    private static final long serialVersionUID = 1L;

    public BadRequestException(String error) {
        super(error);
    }

    public BadRequestException(String error, Exception cause) {
        super(error, cause);
    }

}
