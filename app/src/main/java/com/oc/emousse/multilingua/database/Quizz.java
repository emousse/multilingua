package com.oc.emousse.multilingua.database;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by emousse on 21/11/2016.
 */

public class Quizz extends RealmObject {
    public String description;
    public String question;
    public boolean answer;
}
