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
import com.oc.emousse.multilingua.database.Quizz;
import com.oc.emousse.multilingua.database.User;
import com.oc.emousse.multilingua.pref.MyInterface;
import com.oc.emousse.multilingua.pref.UserShared;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizzFragment extends Fragment implements MyInterface {
    private Realm _realm;

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
                Toast.makeText(getContext(), data.getStringExtra("name"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _realm = Realm.getDefaultInstance();

        String mail = UserShared.getInstance(getContext()).getEmail();
        User u = _realm.where(User.class).equalTo("email", mail).findFirst();
        RealmResults<Lesson> lessons = u.lessons.where().equalTo("enable", true).findAll();
        List<Quizz> quizz = new RealmList<>();
        for (Lesson l : lessons) {
            quizz.add(l.quizz);
        }

        //inflate view from xml
        View view = inflater.inflate(R.layout.fragment_quizz, container, false);

        //setup recycler view and bind data
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list_quizz);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        rv.setAdapter(new QuizzAdapter(quizz,this));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _realm.close();
    }
}
