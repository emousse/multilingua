package com.oc.emousse.multilingua.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emousse on 07/11/2016.
 */

public class User extends RealmObject {
    public String email;

    public String name;
    public String password;
}
