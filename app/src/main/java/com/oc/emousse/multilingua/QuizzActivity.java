package com.oc.emousse.multilingua;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizzActivity extends AppCompatActivity {
    public static final String QUIZZ_QUESTION = "question";
    public static final String QUIZZ_ANSWER = "answer";
    public static final String LESSON_ID = "lesson_id";

    private String _question;
    private boolean _answer;
    private int _lessonId;
    private RadioButton _rbFalse;
    private RadioButton _rbTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        _question = getIntent().getStringExtra(QUIZZ_QUESTION);
        _answer = getIntent().getBooleanExtra(QUIZZ_ANSWER, false);
        _lessonId = getIntent().getIntExtra(LESSON_ID,0);

        TextView q1 = (TextView) findViewById(R.id.quizz_question_1);
        q1.setText(Html.fromHtml(_question));

        _rbTrue = (RadioButton) findViewById(R.id.true_1);
        _rbFalse = (RadioButton) findViewById(R.id.false_1);
    }

    public void checkAnswer(View v){
        if(validateQuizz()){
            //quizz réussi
            setResult(Activity.RESULT_OK, new Intent().putExtra(LESSON_ID,_lessonId));
            finish();

        } else{
            //toast with fail message
            Toast.makeText(this, "Mauvaise réponse!", Toast.LENGTH_SHORT).show();
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
