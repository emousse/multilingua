package com.oc.emousse.multilingua;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.emousse.multilingua.database.Lesson;
import com.oc.emousse.multilingua.database.User;
import com.oc.emousse.multilingua.pref.MyInterface;
import com.oc.emousse.multilingua.pref.UserShared;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFragment extends Fragment {
    private Realm _realm;


    public LessonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _realm = Realm.getDefaultInstance();

        //find all lessons
        String mail = UserShared.getInstance(getContext()).getEmail();
        User u = _realm.where(User.class).equalTo("email", mail).findFirst();

        //inflate fragment_lesson xml
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        //setup recycler view and bind data
        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.list_lessons);
        rv.setLayoutManager(new LinearLayoutManager(container.getContext()));
        rv.setAdapter(new LessonAdapter(u.lessons));

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _realm.close();
    }

}
