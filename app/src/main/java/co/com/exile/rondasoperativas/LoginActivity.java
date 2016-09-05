package co.com.exile.rondasoperativas;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        final TextInputEditText username = (TextInputEditText) findViewById(R.id.user);
        final TextInputEditText password = (TextInputEditText) findViewById(R.id.password);
        String url = "http://104.236.33.228:8060/usuarios/login/";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("login", response);
                        initHome(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("login", error.toString());
                        Snackbar.make(findViewById(R.id.main_container),"usuario y/o contraseña incorrecta", 800).show();
                        returnToLogin();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(loginRequest);
        showLoading();
    }

    private void showLoading() {
        CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        final TextInputLayout username = (TextInputLayout) findViewById(R.id.username_container);
        final TextInputLayout password = (TextInputLayout) findViewById(R.id.password_container);
        Button send = (Button) findViewById(R.id.send_button);
        progressView.setVisibility(View.VISIBLE);
        username.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        send.setVisibility(View.GONE);
    }

    private void returnToLogin(){
        CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        final TextInputLayout username = (TextInputLayout) findViewById(R.id.username_container);
        final TextInputLayout password = (TextInputLayout) findViewById(R.id.password_container);
        Button send = (Button) findViewById(R.id.send_button);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        progressView.setVisibility(View.INVISIBLE);
    }
    private void initHome(String user){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
