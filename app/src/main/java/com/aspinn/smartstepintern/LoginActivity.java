package com.aspinn.smartstepintern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText emailEdit, passwordEdit;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEdit = findViewById(R.id.editTextText4);
        passwordEdit = findViewById(R.id.editTextText5);
    }




    public void login(View view)
    {
        email = emailEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "HTTP://192.168.1.8/step/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray stdArray = new JSONArray(response);
                            JSONObject studentObject = stdArray.getJSONObject(0);



                            Toast.makeText(getApplicationContext(),  "Login Success", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(LoginActivity.this,StepActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),  "Invalid username or password", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }


}