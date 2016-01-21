package nl.brandonvanwijk.ikpmd_eindopdracht.Services;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.brandonvanwijk.ikpmd_eindopdracht.Gson.GsonRequest;
import nl.brandonvanwijk.ikpmd_eindopdracht.Models.CourseModel;


/**
 * Created by Brandon on 11-Jan-16.
 */
public class CourseService {

    private final Context context;
    private String url;
    private RequestQueue requestQueue;
    private ArrayList<CourseModel> courses = new ArrayList<>();

    public CourseService(Context context) {
        this.context = context;
        url = "http://www.fuujokan.nl/subject_lijst.json";
        requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.start();
    }

    public ArrayList<CourseModel> findAll(Response.Listener<List<CourseModel>> listener, Response.ErrorListener errorListener) {
        Type type = new TypeToken<List<CourseModel>>(){}.getType();
        GsonRequest<List<CourseModel>> findAllMovies = new GsonRequest<>(url, type, null, listener,
                errorListener);
        requestQueue.add(findAllMovies);
        return courses;
    }

    public ArrayList<CourseModel> findAll() {
        GsonRequest<List<CourseModel>> findAllMovies = new GsonRequest<>(url, CourseModel.class, null, succesListener(),
                errorListener());
        requestQueue.add(findAllMovies);
        return courses;
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    private Response.Listener<List<CourseModel>> succesListener() {
        return new Response.Listener<List<CourseModel>>() {
            @Override
            public void onResponse(List<CourseModel> response) {
                for (CourseModel module: response) {
                    System.out.println(module);
                }
            }
        };
    }
}
