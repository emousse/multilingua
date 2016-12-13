package com.oc.emousse.multilingua;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oc.emousse.multilingua.database.Lesson;
import com.oc.emousse.multilingua.database.User;
import com.oc.emousse.multilingua.pref.MyInterface;
import com.oc.emousse.multilingua.pref.UserShared;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizzFragment extends Fragment implements MyInterface {
    private Realm _realm;
    private RealmResults<Lesson> _lessons;
    private QuizzAdapter _qu;

    public QuizzFragment() {
        // Required empty public constructor
    }

    @Override
    public void run(Intent intent) {
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 1){
                final int lessonId = data.getIntExtra(QuizzActivity.LESSON_ID,0);
                Toast.makeText(getContext(), Integer.toString(lessonId), Toast.LENGTH_SHORT).show();
                _realm.beginTransaction();
                _lessons.where().equalTo("id",lessonId).findFirst().isCompleted = true;
                _realm.commitTransaction();
                _qu.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _realm = Realm.getDefaultInstance();

        String mail = UserShared.getInstance(getContext()).getEmail();
        User u = _realm.where(User.class).equalTo("email", mail).findFirst();
        _lessons = u.lessons.where().equalTo("enable", true).findAll();

        //inflate view from xml
        View view = inflater.inflate(R.layout.fragment_quizz, container, false);

        //setup recycler view and bind data
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list_quizz);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        _qu = new QuizzAdapter(_lessons,this);
        rv.setAdapter(_qu);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _realm.close();
    }
}
