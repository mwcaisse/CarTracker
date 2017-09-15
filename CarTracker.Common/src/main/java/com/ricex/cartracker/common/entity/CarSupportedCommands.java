package com.ricex.cartracker.common.entity;

/**
 * Created by Mitchell on 2017-09-15.
 */

public class CarSupportedCommands extends AbstractEntity {

    private long carId;

    private int pids0120Bitmask;

    private int pids2140Bitmask;

    private int pids4160Bitmask;

    private int pids6180Bitmask;

    private int pids81A0Bitmask;

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public int getPids0120Bitmask() {
        return pids0120Bitmask;
    }

    public void setPids0120Bitmask(int pids0120Bitmask) {
        this.pids0120Bitmask = pids0120Bitmask;
    }

    public int getPids2140Bitmask() {
        return pids2140Bitmask;
    }

    public void setPids2140Bitmask(int pids2140Bitmask) {
        this.pids2140Bitmask = pids2140Bitmask;
    }

    public int getPids4160Bitmask() {
        return pids4160Bitmask;
    }

    public void setPids4160Bitmask(int pids4160Bitmask) {
        this.pids4160Bitmask = pids4160Bitmask;
    }

    public int getPids6180Bitmask() {
        return pids6180Bitmask;
    }

    public void setPids6180Bitmask(int pids6180Bitmask) {
        this.pids6180Bitmask = pids6180Bitmask;
    }

    public int getPids81A0Bitmask() {
        return pids81A0Bitmask;
    }

    public void setPids81A0Bitmask(int pids81A0Bitmask) {
        this.pids81A0Bitmask = pids81A0Bitmask;
    }
}
