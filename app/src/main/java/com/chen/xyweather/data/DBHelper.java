package com.chen.xyweather.data;

import android.content.Context;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import database.LocalCity;
import database.dao.DaoMaster;
import database.dao.DaoSession;
import database.dao.LocalCityDao;

/**
 * Created by chen on 17-5-8.
 * 数据库操作
 */

public class DBHelper {
    private static final String DB_NAME = "yBook";
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private LocalCityDao localCityDao;

    private static Context mContext;
    private static DBHelper mInstance;


    public static DBHelper initHelper(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        getDaoSession(mContext);
        return getInstance(mContext);

    }

    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper();
                    mInstance.localCityDao = daoSession.getLocalCityDao();
                }
            }
        }

        return mInstance;
    }

    public static DaoMaster getDaoMaster(Context context) {

        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }

    /**
     * 数据操作
     */

    public void insertCity(LocalCity localCity) {
        localCityDao.insertOrReplace(localCity);
    }

    public void deleteByCity(String city) {
        QueryBuilder<LocalCity> queryBuilder = localCityDao.queryBuilder();
        DeleteQuery<LocalCity> deleteQuery = queryBuilder.where(LocalCityDao.Properties.City.eq(city)).buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
    }


    public boolean isSaved(String city) {
        QueryBuilder<LocalCity> qb = localCityDao.queryBuilder();
        qb.where(LocalCityDao.Properties.City.eq(city));
        return qb.buildCount().count() > 0;
    }

    public int getCount() {
        QueryBuilder<LocalCity> qb = localCityDao.queryBuilder();
        qb.orderDesc(LocalCityDao.Properties.Id);
        return qb.list().size();
    }

}
