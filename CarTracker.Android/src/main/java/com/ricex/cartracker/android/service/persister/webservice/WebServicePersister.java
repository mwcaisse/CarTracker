package com.ricex.cartracker.android.service.persister.webservice;

import com.ricex.cartracker.android.model.OBDReading;
import com.ricex.cartracker.android.service.persister.Persister;
import com.ricex.cartracker.android.settings.CarTrackerSettings;

/**
 * Created by Mitchell on 2/18/2016.
 */
public class WebServicePersister implements Persister {

    private CarTrackerSettings settings;

    private Object monitor;

    private Object waitMonitor;

    private boolean running;

    public WebServicePersister(CarTrackerSettings settings) {
        this.settings = settings;

        monitor = new Object();
        waitMonitor = new Object();
        running = false;
    }

    @Override
    public void run() {

        running = true;

        while (running) {
            try {

                synchronized (waitMonitor) {
                    waitMonitor.wait();
                }

            }
            catch (InterruptedException e) {
                //eh do nothing?
            }
        }
    }

    /** Persists the given OBDReading to the Web Service
     *
     * @param reading The reading to persist
     */
    public void persist(OBDReading reading) {

    }
}
