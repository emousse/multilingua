package com.oc.emousse.multilingua.database;

import io.realm.RealmObject;

/**
 * Created by emousse on 16/11/2016.
 */

public class Lesson extends RealmObject {
    public String title;
    public String description;
    public boolean enable;
}
