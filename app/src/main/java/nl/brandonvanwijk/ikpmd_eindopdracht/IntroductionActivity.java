package nl.brandonvanwijk.ikpmd_eindopdracht;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IntroductionActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "LaunchPreferences";
    SharedPreferences studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        studentInfo = getSharedPreferences(PREFS_NAME, 0);
        isNew();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final EditText naam = (EditText) findViewById(R.id.naamVeld);

        Button checkNaam = (Button) findViewById(R.id.saveButton);

        checkNaam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentInfo.edit().putString("naam", String.valueOf(naam.getText())).commit();
                studentInfo.edit().putBoolean("new_student", true).commit();
                isNew();
            }
        });
    }

    private void isNew() {
        boolean is_new = studentInfo.getBoolean("new_student", false);
        if(is_new == true) {
            startActivity(new Intent(IntroductionActivity.this, MainActivity.class));
        }
    }

}
