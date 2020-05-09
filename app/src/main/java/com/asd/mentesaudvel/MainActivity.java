package com.asd.mentesaudvel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.adapter.ItemProductsAdapter;
import com.asd.mentesaudvel.model.ListVal;
import com.asd.mentesaudvel.model.Products;
import com.asd.mentesaudvel.model.User;
import com.asd.mentesaudvel.ui.FilterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private boolean exit = false;
    private ImageView search,cameraScan,viewList;
    SearchView searchView;
    RelativeLayout top_view;
    RelativeLayout searchPart;
    FrameLayout nav_host_fragment;
    BottomNavigationView nav_view;

    Button cancelBtn;

    private IntentIntegrator scanner;

//    String[] values = new String[] { "product 1",
//            "product 2",
//            "product 3",
//            "product 4",
//            "product 5",
//            "product 6",
//    };
    ArrayList<String> values;
    ArrayList<String> barcodelist;
    ListView listView;

    private ListVal listVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        listVal = new ListVal(false, false, false);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        search = findViewById(R.id.search);
        cameraScan = findViewById(R.id.camera);
        viewList = findViewById(R.id.baricon);
        searchView = findViewById(R.id.searchView);

        top_view = findViewById(R.id.top_view);
        searchPart = findViewById(R.id.searchPart);
        nav_host_fragment = findViewById(R.id.nav_host_fragment);
        nav_view = findViewById(R.id.nav_view);

        cancelBtn = findViewById(R.id.cancelBtn);

        listView = findViewById(R.id.searchList);



        scanner=new IntentIntegrator(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_filter, R.id.navigation_favourite, R.id.navigation_home, R.id.navigation_list, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "search",Toast.LENGTH_SHORT).show();
//                Fragment fragment = new FilterFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                searchPart.setVisibility(View.VISIBLE);
                top_view.setVisibility(View.GONE);
                nav_host_fragment.setVisibility(View.GONE);
                nav_view.setVisibility(View.GONE);

                values = new ArrayList<String>();
                barcodelist = new ArrayList<String>();

                getList();






            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String bar = barcodelist.get(position);
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra("scanValue",bar);
                startActivity(intent);
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPart.setVisibility(View.GONE);
                top_view.setVisibility(View.VISIBLE);
                nav_host_fragment.setVisibility(View.VISIBLE);
                nav_view.setVisibility(View.VISIBLE);
            }
        });
        cameraScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "scan",Toast.LENGTH_SHORT).show();
//                scanner.initiateScan();
                scanner = new IntentIntegrator(MainActivity.this);
                scanner.setCaptureActivity(AnyOrientationCaptureActivity.class);
                scanner.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                scanner.setPrompt("Scan something");
                scanner.setOrientationLocked(false);
                scanner.setBeepEnabled(false);
                scanner.initiateScan();
            }
        });
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "list",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onBackPressed() {

        if (exit) {
            finish();
        } else {
            Toast.makeText(this, this.getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        IntentResult result = scanner.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this,    "Cancelled",Toast.LENGTH_LONG).show();
//            } else {
//                String product = result.getContents();
                String product = "8690526095417";
                if(product!=null)
                {
//                    Toast.makeText(this,    "Product founded",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra("scanValue",product);
                    startActivity(intent);

                }else
                {
                    Toast.makeText(this,    "Product don't exist",Toast.LENGTH_LONG).show();
                }


//            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void getList() {
        final User user = SharedPrefManager.getInstance(MainActivity.this).getUser();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_SEARCH,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONArray array = new JSONArray(response);
                            int length = array.length();

                            for(int i=0;i<length;i++){
                                String name = array.getJSONObject(i).getString("name");
                                String barcode = array.getJSONObject(i).getString("barcode");
                                values.add(name);
                                barcodelist.add(barcode);
                            }




                            // Assign adapter to ListView
                            listView.setAdapter(adapter);


//                            itemAdapter = new ItemProductsAdapter(getActivity(),products,listVal);
//                            itemList.setAdapter(itemAdapter);



                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("str", "");
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String text) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                adapter.getFilter().filter(text);

                return false;
            }
        });
    }

}
