package com.oc.emousse.multilingua.database;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by emousse on 16/11/2016.
 */

public class Lesson extends RealmObject {
    public Quizz quizz;
    public String title;
    public String description;
    public String category;
    public boolean enable;
}
