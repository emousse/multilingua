package com.oc.emousse.multilingua;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oc.emousse.multilingua.database.Rendezvous;
import com.oc.emousse.multilingua.database.User;
import com.oc.emousse.multilingua.pref.UserShared;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RendezvousFragment extends Fragment{
    private Realm _realm;
    private Date todayDate;
    private RealmResults<Rendezvous> _result;
    private RendezvousAdapter _rdvAdapter;
    private FloatingActionButton _floatingButton;

    public RendezvousFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 1){
                _rdvAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _realm = Realm.getDefaultInstance();
        todayDate = new Date(System.currentTimeMillis());

        //inflate view from xml
        View view = inflater.inflate(R.layout.fragment_rendezvous, container, false);

        //set floating action button
        _floatingButton = (FloatingActionButton) view.findViewById(R.id.addRdv);
        _floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddRendezvousActivity.class);
                startActivityForResult(i,1);
            }
        });
        //find date > todayDate
        String mail = UserShared.getInstance(getContext()).getEmail();
        User u = _realm.where(User.class).equalTo("email", mail).findFirst();
        _result = u.rdv.where().greaterThan("date", todayDate).findAll();

        if(_result!=null){
            //set recyclerview adapter and bind data
            RecyclerView recycler = (RecyclerView) view.findViewById(R.id.list_rendezvous);
            recycler.setLayoutManager(new LinearLayoutManager(container.getContext()));
            _rdvAdapter = new RendezvousAdapter(_result);
            recycler.setAdapter(_rdvAdapter);
        }else{
            //set textview with "nothing to show"
        }

        // Inflate the layout for this fragment
        return view;
    }

    public void add(Rendezvous item, int position) {
        _result.add(position, item); // on insère le nouvel objet dans notre       liste d'article lié à l'adapter
        _rdvAdapter.notifyItemInserted(position); // on notifie à l'adapter ce changement
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _realm.close();
    }
}
