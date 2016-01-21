package nl.brandonvanwijk.ikpmd_eindopdracht.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import nl.brandonvanwijk.ikpmd_eindopdracht.App;
import nl.brandonvanwijk.ikpmd_eindopdracht.InvoerActivity;
import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;
import nl.brandonvanwijk.ikpmd_eindopdracht.R;

public class CourseListActivity extends AppCompatActivity {
    private ListView mListView;
    List<CourseModel> vakken;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(CourseModel.getAll().size() == 0) {
            ((App) getApplication()).getCourseService().findAll(successListener(), errorListener(CourseListActivity.this));
        }
        setContentView(R.layout.vakken_list);

        fillList();

    }

    private void fillList() {
        mListView = (ListView) findViewById(R.id.my_list_view);

        vakken = CourseModel.getAll();

        int totalEcts = 0;
        for(CourseModel course: vakken) {

            if(course.getGrade() >= 5.5) {
                totalEcts += course.getEcts();
            }
            SharedPreferences preferences = getSharedPreferences("MY_FILE", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putInt("ects", totalEcts);
            editor.apply();

        }
        courseAdapter = new CourseAdapter(CourseListActivity.this, 0, vakken);
        mListView.setAdapter(courseAdapter);
    }

    private Response.Listener<List<CourseModel>> successListener() {
        return new Response.Listener<List<CourseModel>>() {
            @Override
            public void onResponse(List<CourseModel> courses) {

                ActiveAndroid.beginTransaction();
                try {
                    for (CourseModel course: courses) {
                        course.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();

                } finally {
                    ActiveAndroid.endTransaction();
                    fillList();

                }
            }
        };
    }

    private Response.ErrorListener errorListener(final CourseListActivity courseListActivity) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(courseListActivity.getCurrentFocus(), "Kan modules niet ophalen", Snackbar.LENGTH_LONG).show();
                Log.e("Volley error", error.getMessage());
            }
        };
    }
}

