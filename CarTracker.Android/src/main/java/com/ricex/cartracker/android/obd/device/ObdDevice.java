package com.ricex.cartracker.android.obd.device;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mitchell on 2017-09-15.
 */

public abstract class ObdDevice {


    public abstract void connect() throws ObdDeviceConnectionFailedException;

    public abstract boolean isConnected();

    public abstract InputStream getInputStream() throws IOException;

    public abstract OutputStream getOutputStream() throws IOException ;

    public abstract void disconnect();

}
