package com.ricex.cartracker.android.data.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Mitchell on 2016-10-23.
 */
public abstract class ServerEntity extends Entity {


    public static final String PROPERTY_SERVER_ID = "serverId";
    @DatabaseField
    private long serverId;

    public static final String PROPERTY_SYNCED_WITH_SERVER = "syncedWithServer";
    @DatabaseField
    private boolean syncedWithServer;

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public boolean isSyncedWithServer() {
        return syncedWithServer;
    }

    public void setSyncedWithServer(boolean syncedWithServer) {
        this.syncedWithServer = syncedWithServer;
    }
}
