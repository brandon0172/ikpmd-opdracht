package nl.brandonvanwijk.ikpmd_eindopdracht;

import com.activeandroid.ActiveAndroid;

import nl.brandonvanwijk.ikpmd_eindopdracht.Services.CourseService;

/**
 * Created by Brandon on 11-Jan-16.
 */
public class App extends com.activeandroid.app.Application {
    private static CourseService courseService;

    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        courseService = new CourseService(this);
    }

    public static CourseService getCourseService() {
        return courseService;
    }
}
