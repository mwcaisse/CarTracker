package com.ricex.cartracker.android.data.manager;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ricex.cartracker.android.data.dao.RawReadingDao;
import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class RawReadingManager extends ServerEntityManager<RawReading> {

    private RawReadingDao readingDao;

    public RawReadingManager(RawReadingDao readingDao) {
        super(readingDao);

        this.readingDao = readingDao;
    }

    /** Returns a list of the readings that are unsynced for the given trip
     *
     * @param tripId The id of the trip
     * @return The unsynced readings
     */
    public List<RawReading> getUnsyncedForTrip(long tripId) {
        try {
            QueryBuilder<RawReading , Long> queryBuilder = getQueryBuilder();
            queryBuilder.where().eq(RawReading.PROPERTY_SYNCED_WITH_SERVER, false).and().eq(RawReading.PROPERTY_TRIP, tripId);
            return executeQuery(queryBuilder);
        }
        catch (SQLException e) {
            logException("Failed to fetch unsynced readings for trip: " + tripId, e);
        }
        return null;
    }
}
