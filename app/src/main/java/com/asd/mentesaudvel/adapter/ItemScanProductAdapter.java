package com.asd.mentesaudvel.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.MainActivity;
import com.asd.mentesaudvel.R;
import com.asd.mentesaudvel.SharedPrefManager;
import com.asd.mentesaudvel.URLs;
import com.asd.mentesaudvel.VolleySingleton;
import com.asd.mentesaudvel.model.ListVal;
import com.asd.mentesaudvel.model.Products;
import com.asd.mentesaudvel.model.ScanProducts;
import com.asd.mentesaudvel.model.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemScanProductAdapter extends ArrayAdapter<ScanProducts> {

    List<ScanProducts> markets;
    private Activity context;

    TextView marketName;
//    TextView product_name;
    TextView PriceTxt;
    Button btnAdd;
    Button btnSubtract;
    Button btnFavourite;
    ImageView marketImg;

    String marketid,productid;

    public ItemScanProductAdapter(Activity context, List<ScanProducts> markets) {
        super(context, R.layout.layout_item_scan_list, markets );

//        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.markets = markets;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View filterItem = inflater.inflate(R.layout.layout_item_scan_list, null, true);

//        product_name = (TextView)  filterItem.findViewById(R.id.product_name);
        marketName   = (TextView)  filterItem.findViewById(R.id.itemTxt);
        PriceTxt     = (TextView)  filterItem.findViewById(R.id.quantityTxt);
        btnFavourite = (Button)    filterItem.findViewById(R.id.favBtn);
        btnAdd       = (Button)    filterItem.findViewById(R.id.addbtn);
        btnSubtract  = (Button)    filterItem.findViewById(R.id.SubtractBtn);
        marketImg    = (ImageView) filterItem.findViewById(R.id.imageType);

        ScanProducts Item = markets.get(position);

//        Item.getproductid_list();
//        product_name.setText(Item.getName_list());
        marketName.setText(Item.getmarket_scan());
        PriceTxt.setText(Item.getprice_scan());

        marketid = Item.getmarketid_scan();
        productid = Item.getproductid_scan();

        Picasso.get().load(Item.getmarketimage_scan()).into(marketImg);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add button Clicked", Toast.LENGTH_SHORT).show();
                addMarketListitem();

            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Favourite button Clicked", Toast.LENGTH_SHORT).show();
                addFavouriteitem();
            }
        });

        return filterItem;

    }

    private void addFavouriteitem() {
        final User user = SharedPrefManager.getInstance(getContext()).getUser();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_FAVOURITE_ADD,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("result").equals("1")) {

                                Toast.makeText(getContext(), "added to fav list.", Toast.LENGTH_LONG).show();


                            }else{
                                Toast.makeText(getContext(), "error.", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("productid", marketid);
                params.put("marketid", productid);
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void addMarketListitem() {
        final User user = SharedPrefManager.getInstance(getContext()).getUser();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_MARKETLIST_ADD,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            if (obj.getString("result").equals("1")) {

                                Toast.makeText(getContext(), "added to market list.", Toast.LENGTH_LONG).show();


                            }else{
                                Toast.makeText(getContext(), "error.", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", user.getEmail());
                params.put("password", user.getPassword());
                params.put("productid", marketid);
                params.put("marketid", productid);
                params.put("token",URLs.TOKEN);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}

