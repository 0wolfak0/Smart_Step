package com.aspinn.smartstepintern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText emailEdit, passwordEdit, mobileEdit, nameEdit;
    String email, password, name, mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailEdit = findViewById(R.id.editTextText);
        passwordEdit=findViewById(R.id.editTextText2);
        nameEdit = findViewById(R.id.editTextText3);
        mobileEdit = findViewById(R.id.editTextPhone);
    }
    public void reg(View view)
    {
        email = emailEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        name = nameEdit.getText().toString().trim();
        mobile = mobileEdit.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEdit.setError("Please enter Name");

            nameEdit.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Please enter Email");
            emailEdit.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Please enter Password");
            passwordEdit.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "HTTP://192.168.1.8/step/reg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("message").equals("User already registered")){
                                Toast.makeText(getApplicationContext(),  "User al" +
                                        "ready registered", Toast.LENGTH_SHORT).show();
                            }
                            else if(jsonObject.getString("message").equals("Registration success")) {
                                Toast.makeText(getApplicationContext(),  "User registered successfully", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),  "error"+ e, Toast.LENGTH_SHORT).show();
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
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);

                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public void loginJump(View view)
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}