package com.oc.emousse.multilingua;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oc.emousse.multilingua.database.Rendezvous;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by emousse on 29/11/2016.
 */

public class RendezvousAdapter extends RecyclerView.Adapter<RendezvousAdapter.MyViewHolder> {
    private List<Rendezvous> _rdv;

    public RendezvousAdapter(List<Rendezvous> list){
        _rdv = list;
    }

    @Override
    public RendezvousAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_rendezvous,parent,false);
        return new RendezvousAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RendezvousAdapter.MyViewHolder holder, int position) {
        holder.display(_rdv.get(position));
    }

    @Override
    public int getItemCount() {
        return _rdv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private Rendezvous _currentRdv;
        private TextView _title, _date;
        
        public MyViewHolder(View itemView) {
            super(itemView);
            _title = (TextView) itemView.findViewById(R.id.recycler_title_rendezvous);
            _date = (TextView) itemView.findViewById(R.id.recycler_date_rendezvous);
        }
        
        public void display(Rendezvous rdv){
            _currentRdv = rdv;
            _title.setText(rdv.title);
            _date.setText(rdv.date.toString());
        }
    }
}
