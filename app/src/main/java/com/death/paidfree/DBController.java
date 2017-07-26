package com.death.paidfree;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by deathcode on 20/07/17.
 */


public class DBController {

    private static DBController instance;
    private final Realm realm;

    public DBController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static DBController with(Fragment fragment) {

        if (instance == null) {
            instance = new DBController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static DBController with(Activity activity) {

        if (instance == null) {
            instance = new DBController(activity.getApplication());
        }
        return instance;
    }

    public static DBController with(Application application) {

        if (instance == null) {
            instance = new DBController(application);
        }
        return instance;
    }

    public static DBController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from UserResponseModel.class
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Deals.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Deals> getDeals() {
        return realm.where(Deals.class).findAll().sort("add_time", Sort.DESCENDING);
    }

    //query a single item with the given id
    public Deals getDeal(String id) {
        return realm.where(Deals.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public int hasUserResponse() {
        Log.e("SIZE", ""+realm.where(Deals.class).findAll().size());
        return realm.where(Deals.class).findAll().size();
    }


    public boolean checkIfIdExists(String id) {
        RealmQuery<Deals> query = realm.where(Deals.class)
                .equalTo("id", id);
        return query.count() == 0 ? false : true;
    }
}