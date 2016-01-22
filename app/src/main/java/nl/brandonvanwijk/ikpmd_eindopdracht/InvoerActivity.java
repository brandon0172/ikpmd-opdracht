package nl.brandonvanwijk.ikpmd_eindopdracht;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nl.brandonvanwijk.ikpmd_eindopdracht.List.CourseListActivity;
import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;

public class InvoerActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "LaunchPreferences";
    AppCompatEditText editText;
    CourseModel course;
    int totalEcts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer);

        course = CourseModel.find(getIntent().getLongExtra("course_id", 0));

        TextView vak = (TextView) findViewById(R.id.vakNaam);
        TextView periode = (TextView) findViewById(R.id.vakPeriode);
        TextView ects = (TextView) findViewById(R.id.vakEcts);
        vak.setText(course.getName());
        periode.setText("Periode " + course.getPeriod());
        ects.setText("Ects: " + course.getEcts());


        editText = (AppCompatEditText) findViewById(R.id.vakken_detail_edit_text);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Button opslaan = (Button) findViewById(R.id.vakken_detail_button);
        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCijfer();
                Snackbar snackbar = Snackbar
                        .make((RelativeLayout)findViewById(R.id.vakken_detail_layout), "Cijfer wordt verwerkt", Snackbar.LENGTH_LONG);
                snackbar.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }
        });
    }

    public void saveCijfer() {
        String cijferText = editText.getText().toString();
        double grade = Double.parseDouble(cijferText);
        if(cijferText.equals("")) {
            course.setGrade(0);
            course.update();
        } else if(cijferText.length() < 4 && grade <= 10) {
            course.setGrade(grade);
            course.update();
        } else {
            Snackbar snackbar = Snackbar
                    .make((RelativeLayout) findViewById(R.id.vakken_detail_layout),
                            "Het ingevoerde cijfer is niet goed gekeurd!",
                            Snackbar.LENGTH_LONG);

            snackbar.show();
        }
    }

}
