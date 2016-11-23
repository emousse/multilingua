package com.oc.emousse.multilingua;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.emousse.multilingua.database.Lesson;
import com.oc.emousse.multilingua.pref.UserShared;

import java.util.List;

import io.realm.Realm;

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
        private final View itemContainer;

        //store current lesson
        private Lesson currentLesson;

        //get the last open lesson timestamp in the constructor for getContext
        private  long lastLessonTimestamp;

        //get the 24h ago timestamp
        private long yesterdayTimestamp = (System.currentTimeMillis() - 86400000) / 1000;

        private Realm _realm;

        public MyViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.recycler_title);
            description = (TextView) itemView.findViewById(R.id.recycler_description);
            icon = (ImageView) itemView.findViewById(R.id.recycler_icon);
            itemContainer = (View) itemView.findViewById(R.id.itemContainer);

            lastLessonTimestamp = UserShared.getInstance(itemView.getContext()).getLastLessonTimestamp();

            //configure onClickListener to redirect to the lesson if lastTimestamp < yesterdayTimestamp
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserShared.getInstance(itemView.getContext()).getLastLessonTimestamp()<yesterdayTimestamp || currentLesson.enable){
                        _realm = Realm.getDefaultInstance();
                        _realm.beginTransaction();
                        currentLesson.enable = true;
                        _realm.commitTransaction();
                        notifyDataSetChanged();
                        Intent i = new Intent(v.getContext(),LessonActivity.class);
                        i.putExtra(LessonActivity.LESSON_TITLE,currentLesson.title);
                        i.putExtra(LessonActivity.LESSON_DESCRIPTION,currentLesson.description);
                        v.getContext().startActivity(i);

                    }
                    else {
                        Toast.makeText(itemView.getContext(),"Un peu de patience",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        public void display(Lesson lesson){
            currentLesson = lesson;
            title.setText(lesson.title);
            description.setText(lesson.category);
            icon.setImageResource(R.drawable.logout);
            itemContainer.setEnabled(lesson.enable);
        }
    }
}
