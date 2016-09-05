package co.com.exile.rondasoperativas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Equipo> equipos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVAdapter(equipos);
        mRecyclerView.swapAdapter(mAdapter, false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadActivities();
    }

    private void loadActivities(){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        final String date = formater.format(c.getTime());
        Log.i("Activities", "date: " + date);
        String url = "http://104.236.33.228:8060/actividades/calendar/?start="+date+"&end="+date;
        JsonArrayRequest loginRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                initializeData();
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject activity = response.getJSONObject(i);
                        JSONObject e = activity.optJSONObject("equipo");
                        JSONObject u = e.optJSONObject("unidad");
                        JSONObject p = u.optJSONObject("planta");
                        String nombre = e.getString("nombre");
                        String unidad = u.getString("nombre");
                        String planta = p.getString("nombre");
                        int formulario = e.getInt("formulario");
                        Equipo equipo = new Equipo(formulario, nombre, unidad, planta, false);
                        equipos.add(equipo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mAdapter = new RVAdapter(equipos);
                mRecyclerView.swapAdapter(mAdapter, false);
                Log.e("Activities", response.toString());
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Activities", error.toString());
                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }


    private void initializeData() {
        equipos = new ArrayList<>();
    }

    public void initForm(View view){
        ViewGroup row = (ViewGroup) view.getParent();
        ImageView checkImage = (ImageView) row.findViewById(R.id.check_image);
        TextView name = (TextView) row.findViewById(R.id.equipo);
        String content = checkImage.getContentDescription().toString();
        initForm(content, name.getText().toString());
    }

    private void initForm(String formPk, String name){
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("form", formPk);
        intent.putExtra("name", name);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}
