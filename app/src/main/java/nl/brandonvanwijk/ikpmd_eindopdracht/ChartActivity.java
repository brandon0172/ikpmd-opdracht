package nl.brandonvanwijk.ikpmd_eindopdracht;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;

public class ChartActivity extends AppCompatActivity {
    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;
    List<CourseModel> vakken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        vakken = new ArrayList<>();
        vakken = CourseModel.getAll();

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        checkGrades();
        showStudieVoortgang();
    }

    private void checkGrades() {

        vakken.clear();
        vakken = CourseModel.getAll();

        int ects = 0;
        for (CourseModel course : vakken) {

            if (course.getGrade() >= 5.5) {
                ects += course.getEcts();
            }
            setData(ects);
        }
    }

    private void setData(int aantal) {
        currentEcts = aantal;
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(60 - currentEcts, 1));
        xValues.add("Resterende ECTS");

        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        if (currentEcts <10) {
            colors.add(Color.rgb(244,81,30));
        } else if (currentEcts < 40){
            colors.add(Color.rgb(235,0,0));
        } else if  (currentEcts < 50) {
            colors.add(Color.rgb(253,216,53));
        } else {
            colors.add(Color.rgb(67,160,71));
        }

        colors.add(Color.rgb(255,0,0));


        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        //dataSet.setDrawValues(false); //schrijf getal niet
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data); // bind dataset aan chart.
        mChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", "" + currentEcts);
    }

    public void showStudieVoortgang() {
        if(currentEcts < 40) {
            Toast toast = Toast.makeText(this, "Bindend Negatief Studieadvies ! Dit kan beter !", Toast.LENGTH_LONG);
            toast.show();
        } else if(currentEcts > 40 && currentEcts < 50) {
            Toast toast = Toast.makeText(this, "Je hebt op dit moment een tussenjaar. Nog even doorzetten !", Toast.LENGTH_LONG);
            toast.show();
        } else if (currentEcts > 50) {
            Toast toast = Toast.makeText(this, "Je bent over naar het volgende jaar. Gefelicteerd !", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}


