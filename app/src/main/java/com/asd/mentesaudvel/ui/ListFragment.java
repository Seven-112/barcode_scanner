package com.asd.mentesaudvel.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.asd.mentesaudvel.R;
import com.asd.mentesaudvel.SharedPrefManager;
import com.asd.mentesaudvel.SingletonRequestQueue;
import com.asd.mentesaudvel.URLs;
import com.asd.mentesaudvel.VolleySingleton;
import com.asd.mentesaudvel.adapter.ItemAdapter;
import com.asd.mentesaudvel.adapter.ItemProductsAdapter;
import com.asd.mentesaudvel.model.Item;
import com.asd.mentesaudvel.model.ListVal;
import com.asd.mentesaudvel.model.Products;
import com.asd.mentesaudvel.model.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListFragment extends Fragment {

    ListView itemList;
    ItemProductsAdapter itemAdapter;
    ArrayList<Item> itemArray = new ArrayList<Item>();
    SearchView editsearch;
    ListVal listVal;
//    ImageView productImg;

    ArrayList<Products> products;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list, container, false);
        listVal = new ListVal(false, true, false);

        itemList = (ListView) root.findViewById(R.id.listView);

        products = new ArrayList<Products>();

        getList();

//        editsearch = (SearchView) root.findViewById(R.id.search);

//        productImg = (ImageView) root.findViewById(R.id.productImg);

//        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String text = newText;
//                itemAdapter.filter(text);
//                return false;
//            }
//        });

        return root;
    }


    private void getList() {
        final User user = SharedPrefManager.getInstance(getActivity()).getUser();

        //if everything is fine
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URLs.URL_MARKETLIST_GET,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            JSONArray array = obj.getJSONArray("favourites");
                            int length = array.length();

                            for(int i=0;i<length;i++){
                                Products productss = new Products(array.getJSONObject(i));
                                products.add(productss);
                            }

                            itemAdapter = new ItemProductsAdapter(getActivity(),products,listVal);
                            itemList.setAdapter(itemAdapter);

//                            Log.i(":", String.valueOf(array.get(0)));
//                            Products products = new Products(array.getJSONObject(0));
//                            Log.i(":", products.getName_list());
//                            int i = 0;

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

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

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}