package com.ricex.cartracker.android.data.manager;

import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ricex.cartracker.android.data.entity.Entity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Mitchell on 2016-10-28.
 */
public abstract class EntityManager<T extends Entity> {

    private static final String LOG_TAG = "CT_ENTITY_MANAGER";

    private Dao<T, Long> entityDao;

    protected EntityManager(Dao<T, Long> entityDao) {
        this.entityDao = entityDao;
    }

    /** Queries the database for the entity with the given id
     *
     * @param id The id of the entity
     * @return the entity, or null if no entity with that id exists
     */

    public T get(long id) {
        try {
            return entityDao.queryForId(id);
        }
        catch (SQLException e) {
            logException("Failed to find entity with id: " + id, e);
            return null;
        }
    }

    /** Creates the given entity, The given entity instance will be updated with its ID after creation
     *
     * @param entity The entity to create
     * @return True if successful, false otherwise
     */
    public boolean create(T entity) {
        try {
            entity.setCreateDate(new Date());
            entity.setUpdateDate(new Date());
            entityDao.create(entity);
            return true;
        }
        catch (SQLException e) {
            logException("Failed to create entity.", e);
            return false;
        }
    }

    /** Creates all of the given entities. Each instance of the entitiy will be updated after
     *      creation, with their ID
     *
     * @param entities The entities to create
     * @return true if sucessful, false otherwise
     */
    public boolean create(Collection<T> entities) {
        try {
            for (T entity : entities) {
                entity.setCreateDate(new Date());
                entity.setUpdateDate(new Date());
            }
            entityDao.create(entities);
            return true;
        }
        catch (SQLException e) {
            logException("Failed to create entities.", e);
            return false;
        }
    }

    /** Updates the given entity
     *
     * @param entity The entity to update
     * @return true if successful, false otherwise
     */
    public boolean update(T entity) {
        try {
            entity.setUpdateDate(new Date());
            entityDao.update(entity);
            return true;
        }
        catch (SQLException e) {
            logException("Failed to update entity.", e);
            return false;
        }
    }

    /** Bulk updates a list of entities
     *
     * @param entities The entities to update
     * @return True if successful, false otherwise
     */

    public boolean update(final List<T> entities) {
        try {
            entityDao.callBatchTasks(new Callable<Void>() {
                public Void call() throws SQLException {
                    for (T entity : entities) {
                        entityDao.update(entity);
                    }
                    return null;
                }
            });

            return true;
        }
        catch (Exception e) {
            logException("Failed to update entities.", e);
            return false;
        }
    }

    /** Deletes the entity with the given id
     *
     * @param id The id of the entity to delete
     * @return true if successful, false otherwise
     */

    public boolean delete(long id) {
        try {
            entityDao.deleteById(id);
            return true;
        }
        catch (SQLException e) {
            logException("Failed to delete entity.", e);
            return false;
        }
    }

    /** Helper method to get the query builder
     *
     * @return
     */
    protected QueryBuilder<T, Long> getQueryBuilder() {
        return entityDao.queryBuilder();
    }

    /** Executes a query for a list of results
     *
     * @param query The query to execute
     * @return The results
     * @throws SQLException If an error occured while executing the query
     */
    protected List<T> executeQuery(QueryBuilder<T, Long> query) throws SQLException {
        PreparedQuery<T> preparedQuery = query.prepare();
        return entityDao.query(preparedQuery);
    }

    /** Executes a query and returns the first result
     *
     * @param query The query to execute
     * @return The first result of the query
     * @throws SQLException If an error occured while executing the query
     */
    protected T queryForFirst(QueryBuilder<T, Long> query) throws SQLException {
        PreparedQuery<T> preparedQuery = query.prepare();
        return entityDao.queryForFirst(preparedQuery);
    }

    protected void logException(String message, Exception e) {
        Log.e(LOG_TAG, message, e);
    }
}
