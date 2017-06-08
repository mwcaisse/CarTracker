package com.ricex.cartracker.android.service.task;

/**
 * Created by Mitchell on 2/13/2016.
 */
public abstract class ServiceTask implements Runnable {


    /** The number of secconds to sleep after performing task */
    private double secondsToSleep;

    /** Continue the looping task or not */
    private boolean cont;

    /** Monitor for changing the cont */
    private Object monitor;

    public ServiceTask(double secondsToSleep) {
        cont = true;
        monitor = new Object();
        this.secondsToSleep = secondsToSleep;
    }

    @Override
    public void run() {

        if (!performLoopInitialization()) {
            stop();
            return;
        }

        boolean loopOn = cont;

        while (loopOn) {

            if (!performLoopLogic()) {
                stop();
                break;
            }

            //sleep for configured number of secconds
            /*
            try {
                Thread.sleep((long)(1000 * secondsToSleep));
            }
            catch (InterruptedException e) {
                //we were interrupted... meh. just go back through loop, no need to re-sleep
            }
            */

            //update the ending condition
            synchronized (monitor) {
                loopOn = cont;
            }
        }

        performLoopFinilization();

    }

    /** Called before the loop starts to perform any initialization needed
     *
     * @return Whether to continue or not, if false, task stops
     */
    public abstract boolean performLoopInitialization();

    /** The main loop function, performs any needed loop logic
     *
     * @return Whether to continue or not, if false, task stops
     */
    public abstract boolean performLoopLogic();

    /** Called after the loop has exited to perform any finilzation
     *
     */
    public abstract void performLoopFinilization();

    public void stop() {
        synchronized (monitor) {
            cont = false;
        }
    }

    protected void setSecondsToSleep(int secconds) {
        secondsToSleep = secconds;
    }



}
