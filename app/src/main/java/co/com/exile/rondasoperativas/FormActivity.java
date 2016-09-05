package co.com.exile.rondasoperativas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {
    String id;
    String user_id;
    int registro_id;
    private ArrayList<String> campos;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String user = intent.getStringExtra("user");
        id = intent.getStringExtra("form");

        toolbar.setTitle(name);
        loadForm();
        getUserId(user);
        createRegistro();

        setSupportActionBar(toolbar);

        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.form_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FormAdapter(campos);
        mRecyclerView.swapAdapter(mAdapter, false);
    }

    private void loadForm(){
        String url = "http://104.236.33.228:8060/formulario/list/campo/?formulario="+id;
        JsonObjectRequest formRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    initializeData();
                    JSONArray object_list = response.getJSONArray("object_list");
                    for (int i = 0; i < object_list.length(); i++){
                        JSONObject campo = object_list.getJSONObject(i);
                        String nombre = campo.getString("nombre");
                        campos.add(nombre);
                    }
                    mAdapter = new FormAdapter(campos);
                    mRecyclerView.swapAdapter(mAdapter, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Activities", error.toString());
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(formRequest);
    }

    public void saveRegistro(final View view){
        String url = "http://104.236.33.228:8060/formulario/form/registro/"+ registro_id +"/";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Registrado con exito", Toast.LENGTH_LONG).show();
                        finish();
                        Log.i("form", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("login", new String(error.networkResponse.data));
                        Snackbar.make(findViewById(R.id.main_container),"formulario mal diligenciado", 800).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < mRecyclerView.getChildCount(); i++){
                    TextInputLayout container = (TextInputLayout) mRecyclerView.getChildAt(i);
                    TextInputEditText field = (TextInputEditText) container.findViewById(R.id.field);
                    params.put(campos.get(i), field.getText().toString());
                }
                params.put("formulario", id);
                params.put("operario", user_id);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }

    private void createRegistro(){
        String url = "http://104.236.33.228:8060/formulario/form/registro/create/";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject user = new JSONObject(response);
                            registro_id = user.getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("form", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("login", error.toString());
                        Snackbar.make(findViewById(R.id.main_container),"formulario mal diligenciado", 800).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("formulario", id);
                params.put("operario", user_id);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
    }

    private void getUserId(String json){
        try {
            JSONObject user = new JSONObject(json);
            user_id = user.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initializeData() {
        campos = new ArrayList<>();
    }
}
