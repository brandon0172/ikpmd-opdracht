package nl.brandonvanwijk.ikpmd_eindopdracht.Models;

/**
 * Created by Brandon on 10-Jan-16.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseModel extends Model {

    @Expose
    @Column(name = "name")
    @SerializedName("name")
    private String name;

    @Expose
    @Column(name = "ects")
    @SerializedName("ects")
    private int ects;

    @Expose
    @Column(name = "grade")
    @SerializedName("grade")
    private double grade;

    @Expose
    @Column(name = "period")
    @SerializedName("period")
    private String period;


    public CourseModel(){
        super();
    }

    public CourseModel(String courseName, int ects, double grade, String period){
        super();
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public static List<CourseModel> getAll() {
        return new Select()
                .from(CourseModel.class)
                .orderBy("period ASC")
                .execute();
    }

    public static CourseModel find(long id) {
        return CourseModel.load(CourseModel.class, id);
    }

    public void update() {
        new Update(CourseModel.class)
                .set("grade = ?", getGrade())
                .where("id = ?",getId())
                .execute();
    }
    @Override
    public String toString() {
        return "CourseModel{" +
                "name='" + name + '\'' +
                ", ects='" + ects + '\'' +
                ", grade='" + grade + '\'' +
                ", code='" + period + '\'' +
                '}';
    }
}
