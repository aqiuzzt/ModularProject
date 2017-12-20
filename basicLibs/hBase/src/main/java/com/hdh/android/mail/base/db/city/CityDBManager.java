package com.hdh.android.mail.base.db.city;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.bean.City;
import com.hdh.common.thread.VoidThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Martin on 2017/3/26.
 */

public class CityDBManager {
    private static CityDBManager sManager;
    private boolean inited;
    private static final String DATABASE_NAME = "city_v1.db";
    private static final String DATABASE_TABLE_NAME = "city";
    private List<City> selectionList;
    private ItemOptionsWrapper mItemOptionsWrapper;

    private CityDBManager() {
    }

    private void moveDBFileToDatabaseDirectory(Context appContext) throws Exception {
        appContext = appContext.getApplicationContext();
        File file = appContext.getDatabasePath(DATABASE_NAME);
        if (!file.exists()) {
            file = new File(file.getParent());
            if (!file.exists()) file.mkdirs();
            InputStream is = BaseApplication.get().getAssets().open(DATABASE_NAME);
            OutputStream ost = new FileOutputStream(file.getAbsolutePath() + File.separator + DATABASE_NAME);
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = is.read(buff)) != -1) {
                ost.write(buff, 0, len);
            }
            ost.flush();
            ost.close();
            is.close();
        }
        inited = true;
    }

    public static CityDBManager getManager() {
        if (sManager == null) {
            synchronized (CityDBManager.class) {
                if (sManager == null) {
                    sManager = new CityDBManager();
                }
            }
        }
        return sManager;
    }

    public Observable<Boolean> init(Context appContext) {
        final Context finalContext = appContext;
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                moveDBFileToDatabaseDirectory(finalContext);
                e.onNext(true);
                inited = true;
                e.onComplete();
            }
        });
    }

    public void initAsync(Context appContext) {
        final Context finalContext = appContext;
        new VoidThread() {
            @Override
            public void doInBackground() {
                try {
                    moveDBFileToDatabaseDirectory(finalContext);
                    inited = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Observable<List<City>> queryCityByLevel(int level) {
        final int finalLevel = level;
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "level=?", new String[]{String.valueOf(finalLevel)}, null, null, null);
                List<City> list = null;
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new ArrayList<City>());
                    e.onComplete();
                    return;
                }
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                    list.add(city);
                }
                cursor.close();
                db.close();
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    public Observable<City> queryCityByName(final String name) {
        return Observable.create(new ObservableOnSubscribe<City>() {
            @Override
            public void subscribe(ObservableEmitter<City> e) throws Exception {
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "name=?", new String[]{name}, null, null, null);
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new City());
                    e.onComplete();
                    return;
                }
                City city = new City();
                while (cursor.moveToNext()) {
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                }
                cursor.close();
                db.close();
                e.onNext(city);
                e.onComplete();
            }
        });
    }


    /**
     * 根据城市 level 查找城市
     *
     * @param level 层级
     * @return
     */
    public Observable<List<City>> queryCityByLevelOrder(int level) {
        final int finalLevel = level;
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                if (selectionList != null && selectionList.size() > 0) {
                    e.onNext(selectionList);
                    e.onComplete();
                    return;
                }
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "level=?", new String[]{String.valueOf(finalLevel)}, null, null, null);
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new ArrayList<City>());
                    e.onComplete();
                    return;
                }
                selectionList = new ArrayList<>();
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                    selectionList.add(city);
                    // 排序
                    Collections.sort(selectionList, new Comparator<City>() {

                        @Override
                        public int compare(City lhs, City rhs) {
                            if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                                return lhs.name.compareTo(rhs.name);
                            } else {
                                if ("#".equals(lhs.getInitialLetter())) {
                                    return 1;
                                } else if ("#".equals(rhs.getInitialLetter())) {
                                    return -1;
                                }
                                return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                            }
                        }
                    });
                }
                cursor.close();
                db.close();
                e.onNext(selectionList);
                e.onComplete();
            }
        });
    }

    private SQLiteDatabase openDatabase(Context appContext, int flags) {
        return SQLiteDatabase.openDatabase(appContext.getDatabasePath(DATABASE_NAME).getAbsolutePath(), null, flags);
    }


    public Observable<List<City>> queryCityByParentId(String parentId, int level) {
        final String finalParentId = parentId;
        final int finalLevel = level;
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "parentId=? and level=?", new String[]{finalParentId, String.valueOf(finalLevel)}, null, null, null);
                List<City> list = null;
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new ArrayList<City>());
                    e.onComplete();
                    return;
                }
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                    list.add(city);
                }
                cursor.close();
                db.close();
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    public Observable<List<City>> fuzzySearchCity(final int level, final String name) {
        //select * from city where level=2 and (pinyin like "%chang%" or firstLetter like "%Z%" or firstLetters like "%ZLL") order by firstLetter
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "level=? and (name like ? or pinyin like ? " +
                                "or firstLetter like ? or firstLetters like ?)",
                        new String[]{String.valueOf(level), "%" + name + "%", "" + name + "%", "" + name + "%", "" + name + "%"}, null, null, null);
                List<City> list = null;
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new ArrayList<City>());
                    e.onComplete();
                    return;
                }
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                    list.add(city);
                }
                cursor.close();
                db.close();
                e.onNext(list);
                e.onComplete();
            }
        });
    }

    public Observable<List<City>> queryCitysByLevelAndId(final int level, final String parentId) {
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
                Cursor cursor = db.query(DATABASE_TABLE_NAME, null, "level=? and parentId=?",
                        new String[]{String.valueOf(level), parentId}, null, null, null);
                List<City> list = null;
                if (cursor == null || cursor.getCount() == 0) {
                    if (cursor != null) cursor.close();
                    e.onNext(new ArrayList<City>());
                    return;
                }
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(cursor.getLong(cursor.getColumnIndex("id")));
                    city.parentId = String.valueOf(cursor.getLong(cursor.getColumnIndex("parentId")));
                    city.level = cursor.getInt(cursor.getColumnIndex("level"));
                    city.name = cursor.getString(cursor.getColumnIndex("name"));
                    city.type = cursor.getString(cursor.getColumnIndex("type"));
                    city.pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
                    city.letter = cursor.getString(cursor.getColumnIndex("firstLetter"));
                    city.firstLetters = cursor.getString(cursor.getColumnIndex("firstLetters"));
                    list.add(city);
                }
                cursor.close();
                db.close();
                e.onNext(list);
            }
        });
    }

    //将三级数据全部读取出来
    public Observable<ItemOptionsWrapper> loadAllCityData() {
        return Observable.create(new ObservableOnSubscribe<ItemOptionsWrapper>() {
            @Override
            public void subscribe(ObservableEmitter<ItemOptionsWrapper> e) throws Exception {
                ItemOptionsWrapper itemOptionsWrapper = queryAllCitiesDataFromDBOrMemory();
                e.onNext(itemOptionsWrapper);
            }
        });
    }

    public void clearPCAData() {
        if (mItemOptionsWrapper != null) {
            mItemOptionsWrapper.clear();
        }
    }

    private ItemOptionsWrapper queryAllCitiesDataFromDBOrMemory() {
        if (mItemOptionsWrapper != null) {
            if (mItemOptionsWrapper.item1Options != null && mItemOptionsWrapper.item1Options.size() > 0
                    && mItemOptionsWrapper.item2Options != null && mItemOptionsWrapper.item2Options.size() > 0
                    && mItemOptionsWrapper.item3Options != null && mItemOptionsWrapper.item3Options.size() > 0
                    ) {
                return mItemOptionsWrapper;
            }
        }
        SQLiteDatabase db = openDatabase(BaseApplication.get(), SQLiteDatabase.OPEN_READONLY);
        //查处所有的城市
        Cursor pCursor = getCityByLevel(db, 1);
        List<City> listProvince = new ArrayList<>(pCursor.getCount());
        while (pCursor.moveToNext()) {
            City city = new City();
            city.id = String.valueOf(pCursor.getLong(pCursor.getColumnIndex("id")));
            city.name = pCursor.getString(pCursor.getColumnIndex("name"));
            listProvince.add(city);
        }
        pCursor.close(); //省数据获取完成

        List<City> item1Options = listProvince;
        //循环获取省下所有的市数据
        List<List<City>> item2Options = new ArrayList<>();
        for (int i = 0; i < listProvince.size(); i++) {
            Cursor cCursor = getCityByLevel(db, 2, listProvince.get(i).id);
            List<City> listCity = new ArrayList<>(cCursor.getCount());
            while (cCursor.moveToNext()) {
                City city = new City();
                city.id = String.valueOf(cCursor.getLong(cCursor.getColumnIndex("id")));
                city.name = cCursor.getString(cCursor.getColumnIndex("name"));
                listCity.add(city);
            }
            item2Options.add(listCity);
            cCursor.close(); //城数据获取完成
        }
        //循环获取市下所有的区数据
        List<List<List<City>>> item3Options = new ArrayList<>();
        for (int i = 0; i < item2Options.size(); i++) {
            List<City> cities = item2Options.get(i);
            List<List<City>> caList = new ArrayList<>();
            for (int j = 0; j < cities.size(); j++) {
                Cursor aCursor = getCityByLevel(db, 3, cities.get(j).id);
                List<City> areas = new ArrayList<>(aCursor.getCount());
                while (aCursor.moveToNext()) {
                    City city = new City();
                    city.id = String.valueOf(aCursor.getLong(aCursor.getColumnIndex("id")));
                    city.name = aCursor.getString(aCursor.getColumnIndex("name"));
                    areas.add(city);
                }
                caList.add(areas);
                aCursor.close();
            }
            item3Options.add(caList);
        }
        db.close();
        return mItemOptionsWrapper = new ItemOptionsWrapper(item1Options, item2Options, item3Options);
    }

    protected Cursor getCityByLevel(SQLiteDatabase db, int level) {
        return db.query(DATABASE_TABLE_NAME, new String[]{"id", "name"}, "level=?", new String[]{String.valueOf(level)}, null, null, "id");
    }

    protected Cursor getCityByLevel(SQLiteDatabase db, int level, String parentId) {
        return db.query(DATABASE_TABLE_NAME, new String[]{"id", "name",}, "level=? and parentId=?", new String[]{String.valueOf(level), parentId}, null, null, "id");
    }

    public class ItemOptionsWrapper {
        public List<City> item1Options;
        public List<List<City>> item2Options;
        public List<List<List<City>>> item3Options;

        public ItemOptionsWrapper(List<City> item1Options, List<List<City>> item2Options, List<List<List<City>>> item3Options) {
            this.item1Options = item1Options;
            this.item2Options = item2Options;
            this.item3Options = item3Options;
        }

        void clear() {
            if (item1Options != null) {
                item1Options.clear();
                item1Options = null;
            }

            if (item2Options != null) {
                item2Options.clear();
                item2Options = null;
            }

            if (item3Options != null) {
                item3Options.clear();
                item3Options = null;
            }
        }
    }


}
