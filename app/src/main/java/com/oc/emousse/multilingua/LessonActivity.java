package com.oc.emousse.multilingua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.emousse.multilingua.pref.UserShared;

public class LessonActivity extends AppCompatActivity {
    public static final String LESSON_TITLE = "title";
    public static final String LESSON_DESCRIPTION = "description";

    private long _currentTimestamp = System.currentTimeMillis() / 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        String title = getIntent().getStringExtra(LESSON_TITLE);
        String description = getIntent().getStringExtra(LESSON_DESCRIPTION);

        TextView twTitle = (TextView) findViewById(R.id.lesson_title);
        TextView twDescription = (TextView) findViewById(R.id.lesson_description);
        twTitle.setText(title);
        twDescription.setText(description);

        if(UserShared.getInstance(this).setLastLessonTimestamp(_currentTimestamp)){
            Toast.makeText(getApplicationContext(),"COMMIT",Toast.LENGTH_SHORT).show();

        }
    }
}