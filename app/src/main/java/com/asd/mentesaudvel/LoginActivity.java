package com.asd.mentesaudvel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton, phoneLoginButton;
    private EditText userEmail, userPassword;
    private TextView needNewAccountLink;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_login);

        InitializeFields();
//        userEmail.setText("android1@test.com");
//        userPassword.setText("zzzzzz");
        userEmail.setText("muhammet2_1996@hotmail.com");
        userPassword.setText("password");

        needNewAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToRegisterActivity();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }
        });


    }


    private void AllowUserToLogin() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_LONG).show();
        }
        else{

            userLogin();
        }
    }

    private void userLogin() {
        //first getting the values
        final String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Please enter your username");
            userEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Please enter your password");
            userPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_GET,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("result").equals("1")) {

                                String UserName = obj.getString("user");
                                String UserGender = obj.getString("gender");
                                String UserAge = obj.getString("age");

                                User user = new User(11,UserName,email,UserGender,UserAge,password);

                                SharedPrefManager.getInstance(LoginActivity.this).userLogin(user);

                                Toast.makeText(LoginActivity.this, "Log in Successful. ", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Please check your email and password.", Toast.LENGTH_LONG).show();
                            }

//                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");
//
//                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("name"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender"),
//                                        userJson.getString("age"),
//                                        userJson.getString("password")
//                                );
//
//                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(LoginActivity.this).userLogin(user);
//                                //starting the profile activity
//
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                finish();
//                            } else {
//                                Toast.makeText(LoginActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void InitializeFields() {
        loginButton = (Button) findViewById(R.id.login_button);
        userEmail = (EditText) findViewById(R.id.login_email);
        userPassword = (EditText) findViewById(R.id.login_password);
        needNewAccountLink = (TextView) findViewById(R.id.need_new_account_link);
        progressBar = findViewById(R.id.progressBar);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (currentUser != null) {
//            SendUserToMainActivity();
//        }
//    }

    private void SendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(registerIntent);
        finish();
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}