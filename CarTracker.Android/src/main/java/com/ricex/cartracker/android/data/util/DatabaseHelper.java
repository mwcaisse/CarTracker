package com.ricex.cartracker.android.data.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;

import java.sql.SQLException;


/**
 * Created by Mitchell on 2016-10-28.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String LOG_TAG = "CT_DB_HELPER";

    private static final String DATABASE_NAME = "cartracker.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            createTables(database, connectionSource);
        }
        catch (SQLException e) {
            android.util.Log.e(LOG_TAG, "Couldn't create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            deleteTables(database, connectionSource);
            createTables(database, connectionSource);
        }
        catch (SQLException e) {
            android.util.Log.e(LOG_TAG, "Couldn't update database!", e);
        }
    }

    private void createTables(SQLiteDatabase database, ConnectionSource connection) throws SQLException {
        TableUtils.createTable(connectionSource, Log.class);
        TableUtils.createTable(connectionSource, RawReading.class);
        TableUtils.createTable(connectionSource, RawTrip.class);
    }

    private void deleteTables(SQLiteDatabase database, ConnectionSource connection) throws SQLException {
        TableUtils.dropTable(connectionSource, Log.class, true);
        TableUtils.dropTable(connectionSource, RawReading.class, true);
        TableUtils.dropTable(connectionSource, RawTrip.class, true);
    }
}
