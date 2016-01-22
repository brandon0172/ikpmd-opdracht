package nl.brandonvanwijk.ikpmd_eindopdracht;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nl.brandonvanwijk.ikpmd_eindopdracht.List.CourseListActivity;
import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<CourseModel> vakken;
    public static final String PREFS_NAME = "LaunchPreferences";
    SharedPreferences studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentInfo = getSharedPreferences(PREFS_NAME, 0);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vakken = new ArrayList<>();
        vakken = CourseModel.getAll();


        if(isOnline() == false) {
            Toast.makeText(this, "Geen internetverbinding !", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        final EditText naam = (EditText) findViewById(R.id.gebruikersnaam);
//
//        final TextView welcomeStudent = (TextView) findViewById(R.id.studentnaam);
//
//        Button checkNaam = (Button) findViewById(R.id.saveName);
//
//        checkNaam.
//
//        checkNaam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                studentInfo.edit().putString("naam", String.valueOf(naam.getText())).commit();
//                studentInfo.edit().putBoolean("new_student", false).commit();
//            }
//        });

        TextView actualEc = (TextView) findViewById(R.id.voortgang);
        actualEc.setText("Aantal actuele studiepunten: " + showActualEcts() + "/60");
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            Log.d("Vakken ingedrukt", "Start CourseListActivity");
            startActivity(new Intent(MainActivity.this, CourseListActivity.class));
        } else if (id == R.id.nav_gallery) {
            Log.d("Visualisatie ingedrukt", "Start ChartActivity");
            startActivity(new Intent(MainActivity.this, ChartActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private int showActualEcts() {
        vakken.clear();
        vakken = CourseModel.getAll();

        int ects = 0;
        for (CourseModel course : vakken) {

            if (course.getGrade() >= 5.5) {
                ects += course.getEcts();
            }
        }
        return ects;
    }

}
