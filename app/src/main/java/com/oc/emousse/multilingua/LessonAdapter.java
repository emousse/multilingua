package com.oc.emousse.multilingua;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oc.emousse.multilingua.database.Lesson;

import java.util.List;

/**
 * Created by emousse on 16/11/2016.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {
    private List<Lesson> _lessons;

    public LessonAdapter(List<Lesson> lessons){
        _lessons = lessons;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_lesson, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display(_lessons.get(position));
    }

    @Override
    public int getItemCount() {
        return _lessons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final ImageView icon;

        private Lesson currentLesson;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recycler_title);
            description = (TextView) itemView.findViewById(R.id.recycler_description);
            icon = (ImageView) itemView.findViewById(R.id.recycler_icon);
        }

        public void display(Lesson lesson){
            currentLesson = lesson;
            title.setText(lesson.title);
            description.setText(lesson.description);
            icon.setImageResource(R.drawable.logout);
        }
    }
}
