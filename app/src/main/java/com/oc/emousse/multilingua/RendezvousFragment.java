package com.oc.emousse.multilingua;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oc.emousse.multilingua.database.Rendezvous;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RendezvousFragment extends Fragment {
    private Realm _realm;
    private Date todayDate;

    public RendezvousFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _realm = Realm.getDefaultInstance();
        todayDate = new Date(System.currentTimeMillis());

        //inflate view from xml
        View view = inflater.inflate(R.layout.fragment_rendezvous, container, false);

        //find date > todayDate
        RealmResults<Rendezvous> result = _realm.where(Rendezvous.class).greaterThan("date", todayDate).findAll();

        if(result!=null){
            //set recyclerview adapter
        }else{
            //set textview with "nothing to show"
        }

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _realm.close();
    }
}
