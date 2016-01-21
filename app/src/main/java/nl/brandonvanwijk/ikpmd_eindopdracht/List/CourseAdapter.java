package nl.brandonvanwijk.ikpmd_eindopdracht.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.brandonvanwijk.ikpmd_eindopdracht.InvoerActivity;
import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;
import nl.brandonvanwijk.ikpmd_eindopdracht.R;

/**
 * Created by Brandon on 11-Jan-16.
 */
public class CourseAdapter extends ArrayAdapter {
    Context context;

    public CourseAdapter(Context context, int resource, List courses) {
        super(context, resource, courses);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        final CourseModel course = (CourseModel) getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.view_content_row, parent, false);
        }
        TextView courseName = (TextView) view.findViewById(R.id.subject_name);
        TextView courseECTS = (TextView) view.findViewById(R.id.subject_ects);
        TextView courseGrade = (TextView) view.findViewById(R.id.subject_grade);
        courseName.setText(course.getName());
        courseECTS.setText("Ects: " + Integer.toString(course.getEcts()));
        courseGrade.setText("Cijfer: " + Double.toString(course.getGrade()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InvoerActivity.class);
                intent.putExtra("course_id", course.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}


