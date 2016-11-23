package com.oc.emousse.multilingua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.oc.emousse.multilingua.database.Quizz;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuizzActivity extends AppCompatActivity {
    private Realm _realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        _realm = Realm.getDefaultInstance();

        RealmResults<Quizz> results = _realm.where(Quizz.class).equalTo("lesson.enable", true).findAll();

        TextView viewById = (TextView) findViewById(R.id.textView);
        viewById.setText(results.get(0).question);
    }
}
