package com.ricex.cartracker.android.data.manager;

import com.ricex.cartracker.android.data.dao.RawTripDao;
import com.ricex.cartracker.android.data.entity.RawTrip;

/**
 * Created by Mitchell on 2016-10-28.
 */
public class RawTripManager extends ServerEntityManager<RawTrip> {

    private RawTripDao tripDao;

    public RawTripManager(RawTripDao tripDao) {
        super(tripDao);

        this.tripDao = tripDao;
    }

}
