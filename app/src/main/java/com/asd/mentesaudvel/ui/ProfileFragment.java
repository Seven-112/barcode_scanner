package com.asd.mentesaudvel.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.R;
import com.asd.mentesaudvel.SharedPrefManager;
import com.asd.mentesaudvel.URLs;
import com.asd.mentesaudvel.VolleySingleton;
import com.asd.mentesaudvel.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {
    EditText edtName,edtEmail,edtPassword,edtAge;
    Button btnUpdateUserprofile;
    RadioButton radioButtonMale,radioButtonFemale;
    RadioGroup radioGroupGender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        edtName = root.findViewById(R.id.edtName);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPassword = root.findViewById(R.id.edtPassword);
        edtAge = root.findViewById(R.id.edtAge);

        radioButtonMale = (RadioButton) root.findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) root.findViewById(R.id.radioButtonFemale);
        radioGroupGender = root.findViewById(R.id.radioGender);

        btnUpdateUserprofile = root.findViewById(R.id.btnUpdateUserprofile);

        edtName.setEnabled(false);
        edtEmail.setEnabled(false);
        edtPassword.setEnabled(false);
        edtAge.setEnabled(false);
        radioButtonMale.setEnabled(false);
        radioButtonFemale.setEnabled(false);

        btnUpdateUserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.isEnabled())
                {
                    edtName.setEnabled(false);
                    edtEmail.setEnabled(false);
                    edtPassword.setEnabled(false);
                    edtAge.setEnabled(false);
                    radioButtonMale.setEnabled(false);
                    radioButtonFemale.setEnabled(false);

                    final String gender = ((RadioButton) root.findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
                    final String genderNum ;

                    if(gender.equals("Male")){
                        genderNum = "1";
                    }else{
                        genderNum = "2";
                    }

                    final String username = edtName.getText().toString().trim();
                    final String email = edtEmail.getText().toString().trim();
                    final String age = edtAge.getText().toString().trim();
                    final String password = edtPassword.getText().toString().trim();




                    StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_UDATE,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        //converting response to json object
                                        JSONObject obj = new JSONObject(response);


                                        if (obj.getString("success").equals("1")) {
                                            User user = new User(11,username,email,genderNum,age,password);

                                            SharedPrefManager.getInstance(getActivity()).userLogin(user);

                                            btnUpdateUserprofile.setText("Edit Profile");

                                            Toast.makeText(getActivity(), "Update Successful. ", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

                }
                else
                {
                    edtName.setEnabled(true);
                    edtEmail.setEnabled(true);
                    edtPassword.setEnabled(true);
                    edtAge.setEnabled(true);
                    radioButtonMale.setEnabled(true);
                    radioButtonFemale.setEnabled(true);

                    btnUpdateUserprofile.setText("Update Profile");

                }
            }
        });

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        edtName.setText(user.getName());
        edtEmail.setText(user.getEmail());
        edtPassword.setText(user.getPassword());
        edtAge.setText(user.getAge());

        String gender = user.getGender();
        if(gender.equals("1")){
            radioButtonMale.setChecked(true);
        }else {
            radioButtonFemale.setChecked(true);
        }

        return root;
    }
}