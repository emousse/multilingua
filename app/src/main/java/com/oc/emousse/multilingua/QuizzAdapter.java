package com.oc.emousse.multilingua;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.emousse.multilingua.database.Lesson;
import com.oc.emousse.multilingua.database.Quizz;

import java.util.List;

/**
 * Created by emousse on 23/11/2016.
 */

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.MyViewHolder> {
    private List<Quizz> _quizz;

    public QuizzAdapter(List<Quizz> quizz) {
        _quizz = quizz;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_quizz, parent, false);
        return new QuizzAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(_quizz.get(position));
    }

    @Override
    public int getItemCount() {
        return _quizz.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final ImageView icon;

        //store current quizz
        private Quizz currentQuizz;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recycler_title_quizz);
            description = (TextView) itemView.findViewById(R.id.recycler_description_quizz);
            icon = (ImageView) itemView.findViewById(R.id.recycler_icon_quizz);
        }

        public void display(Quizz quizz){
            currentQuizz = quizz;
            title.setText(quizz.question);
            description.setText(quizz.answer);
            icon.setImageResource(R.drawable.logout);
        }
    }
}
