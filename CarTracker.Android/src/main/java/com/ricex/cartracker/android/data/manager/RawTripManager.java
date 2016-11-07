package com.ricex.cartracker.android.data.manager;

import com.j256.ormlite.stmt.QueryBuilder;
import com.ricex.cartracker.android.data.dao.RawReadingDao;
import com.ricex.cartracker.android.data.dao.RawTripDao;
import com.ricex.cartracker.android.data.entity.RawReading;
import com.ricex.cartracker.android.data.entity.RawTrip;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class RawTripManager extends ServerEntityManager<RawTrip> {

    private RawTripDao tripDao;

    private RawReadingDao readingDao;

    public RawTripManager(RawTripDao tripDao, RawReadingDao readingDao) {
        super(tripDao);

        this.tripDao = tripDao;
        this.readingDao = readingDao;
    }

    public List<RawTrip> getTripsWithUnsyncedReadings() {
        try {
            QueryBuilder<RawTrip, Long> queryBuilder = getQueryBuilder();

            QueryBuilder<RawReading, Long> readingQb = readingDao.queryBuilder();
            readingQb.where().eq(RawReading.PROPERTY_SYNCED_WITH_SERVER, false);

            queryBuilder.join(readingQb);
            return executeQuery(queryBuilder);
        }
        catch (SQLException e) {
            logException("Failed to fetch trips with unsynced readings", e);
        }
        return null;
    }

}
