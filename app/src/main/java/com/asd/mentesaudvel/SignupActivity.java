package com.asd.mentesaudvel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class SignupActivity extends AppCompatActivity {

    private Button createAccountButton;
    private EditText userName,userEmail,userAge, userGender, userPassword,userConfirmPassword;
    private TextView alreadyHaveAccount;
    RadioGroup radioGroupGender;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_signup);

        InitializeFields();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginActivity();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }

        });
    }

    private void InitializeFields() {
        createAccountButton = (Button)findViewById(R.id.signup_button);
        userName = (EditText)findViewById(R.id.signup_name);
        userEmail = (EditText)findViewById(R.id.signup_email);
        userAge = (EditText)findViewById(R.id.age);
        userPassword = (EditText)findViewById(R.id.signup_password);
        userConfirmPassword = (EditText)findViewById(R.id.signup_repassword);
        alreadyHaveAccount = (TextView)findViewById(R.id.already_have_account);
        radioGroupGender = findViewById(R.id.radioGender);

        loadingBar = new ProgressDialog(this);
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToMainActivity(){
        Intent mainIntent = new Intent(SignupActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private String CreateNewAccount() {
        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String age = userAge.getText().toString();
        String password = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter name...", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Please enter age...", Toast.LENGTH_LONG).show();
        }
        else {
//            loadingBar.setTitle("Creating New Account");
//            loadingBar.setMessage("Please wait, while we are creating new account for you");
//            loadingBar.setCanceledOnTouchOutside(true);
//            loadingBar.show();

//            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//
//            RequestHandler requestHandler = new RequestHandler();
//            //creating request parameters
//            HashMap<String, String> params = new HashMap<>();
//            params.put("username", name);
//            params.put("email", email);
//            params.put("password", password);
//            params.put("gender", "gender");
//            params.put("age",age);
//
//            //returing the response
//            return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
//
//            try {
//                //converting response to json object
//                JSONObject obj = new JSONObject(s);
//
//                //if no error in response
//                if (!obj.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                    //getting the user from the response
//                    JSONObject userJson = obj.getJSONObject("user");
//
//                    //creating a new user object
//                    User user = new User(
//                            userJson.getInt("id"),
//                            userJson.getString("username"),
//                            userJson.getString("email"),
//                            userJson.getString("gender"),
//                            userJson.getString("gender"),
//                            userJson.getString("gender")
//                    );
//
//                    //storing the user in shared preferences
//                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                    //starting the profile activity
//                    finish();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                } else {
//                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            registerUser();
        }
        return name;
    }

    private void registerUser() {
        final String username = userName.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();
        final String repassword = userConfirmPassword.getText().toString().trim();
        final String age = userAge.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String genderNum ;

        if(gender.equals("Male")){
            genderNum = "1";
        }else{
            genderNum = "2";
        }
        //first we will do the validations
        if (TextUtils.isEmpty(username)) {
            userName.setError("Please enter username");
            userName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Please enter your email");
            userEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Enter a valid email");
            userEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            userPassword.setError("Enter a password");
            userPassword.requestFocus();
            return;
        }
        if (!password.equals(repassword)) {
            userConfirmPassword.setError("Enter matching password");
            userConfirmPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_REGISTER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);


                            if (obj.getString("result").equals("1")) {
                                User user = new User(11,username,email,genderNum,age,password);

                                SharedPrefManager.getInstance(SignupActivity.this).userLogin(user);

                                Toast.makeText(SignupActivity.this, "Sign Up Successful. ", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignupActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", genderNum);
                params.put("age", age);
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


}