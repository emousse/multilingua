package com.oc.emousse.multilingua;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.emousse.multilingua.database.Quizz;
import com.oc.emousse.multilingua.pref.UserShared;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuizzActivity extends AppCompatActivity {
    public static final String QUIZZ_QUESTION = "question";
    public static final String QUIZZ_ANSWER = "answer";

    private String _question;
    private boolean _answer;
    private RadioButton _rbFalse;
    private RadioButton _rbTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        _question = getIntent().getStringExtra(QUIZZ_QUESTION);
        _answer = getIntent().getBooleanExtra(QUIZZ_ANSWER, false);

        TextView q1 = (TextView) findViewById(R.id.quizz_question_1);
        q1.setText(Html.fromHtml(_question));

        _rbTrue = (RadioButton) findViewById(R.id.true_1);
        _rbFalse = (RadioButton) findViewById(R.id.false_1);
    }

    public void checkAnswer(View v){
        if(validateQuizz()){
            //quizz réussi
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        } else{
            //toast with fail message
            Toast.makeText(this, "FALSE", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validateQuizz(){
        boolean valid = true;
        if(_answer){
            //if true check radio button true is checked
            if(!_rbTrue.isChecked())
                valid = false;
        } else{
            //check radio button false is checked
            if(!_rbFalse.isChecked())
                valid = false;
        }
        return valid;
    }
}
