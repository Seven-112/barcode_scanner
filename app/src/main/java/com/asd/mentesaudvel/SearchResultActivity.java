package com.asd.mentesaudvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.adapter.ItemProductsAdapter;
import com.asd.mentesaudvel.adapter.ItemScanProductAdapter;
import com.asd.mentesaudvel.model.ListVal;
import com.asd.mentesaudvel.model.ScanProducts;
import com.asd.mentesaudvel.model.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    ImageView productImg;
    String scanValue;
    TextView productName;
    ListView itemList;
    Button backBtn;

    ItemScanProductAdapter itemAdapter;
    ArrayList<ScanProducts> products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_searchresult);


        Intent intent = getIntent();
        scanValue = intent.getStringExtra("scanValue");

        productImg = (ImageView) findViewById(R.id.productImg);
        itemList = (ListView) findViewById(R.id.listView);
        productName = (TextView) findViewById(R.id.productName);
        backBtn = (Button) findViewById(R.id.backBtn);
        products = new ArrayList<ScanProducts>();

        Picasso.get().load(URLs.URL_NONIMAGE).into(productImg);

//        Picasso.get().load("https:\\/\\/ayb.akinoncdn.com\\/products\\/2019\\/10\\/09\\/4804\\/79f31dd4-2b73-490e-9401-ed5277885896_size780x780_quality60_cropCenter.jpg").into(productImg);

        getList();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getList() {
        final User user = SharedPrefManager.getInstance(SearchResultActivity.this).getUser();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_BARCODE_SCAN + scanValue,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
//                            JSONObject obj = new JSONObject(response);

                            JSONArray array = new JSONArray(response);
                            int length = array.length();

                            String isSuccess = array.getJSONObject(0).getString("success");

                            if(isSuccess.equals("1")){
                                String name = array.getJSONObject(0).getString("name");
                                String imgURL = array.getJSONObject(0).getString("image");

                                productName.setText(name);

                                Picasso.get().load(imgURL).into(productImg);

                                for(int i=1;i<length;i++){
                                    ScanProducts productss = new ScanProducts(array.getJSONObject(i));
                                    products.add(productss);
                                }

                                itemAdapter = new ItemScanProductAdapter(SearchResultActivity.this,products);
                                itemList.setAdapter(itemAdapter);
                            }else{
                                Toast.makeText(SearchResultActivity.this, "Aradığınız ürün bulunamadı.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchResultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(SearchResultActivity.this).addToRequestQueue(stringRequest);
    }
}
