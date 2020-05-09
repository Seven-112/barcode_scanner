package com.asd.mentesaudvel.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Products {

    String productid;
    String name;
    String price;
    String market;
    String marketid;
    String marketimage;

//    public Products(String productid, String name, String price, String market, String marketid, String isitonfavourites, String isitonmarketlist){
//        this.productid        = productid;
//        this.name             = name;
//        this.price            = price;
//        this.market           = market;
//        this.marketid         = marketid;
//        this.isitonfavourites = isitonfavourites;
//        this.isitonmarketlist = isitonmarketlist;
//    }

    public String getproductid_list(){
        return productid;
    }
    public String getName_list(){
        return name;
    }
    public String getPrice_list(){
        return price;
    }
    public String getMarket_list(){
        return market;
    }
    public String getMarketid_list(){
        return marketid;
    }
    public String getMarketimage(){
        return marketimage;
    }




//    public static Products fromJson(JSONObject jsonObject) {
//        Products b = new Products();
//        // Deserialize json into object fields
//        try {
//            b.productid = jsonObject.getString("productid");
//            b.name = jsonObject.getString("name");
//            b.price = jsonObject.getString("price");
//            b.market = jsonObject.getString("market");
//            b.marketid = jsonObject.getString("marketid");
//            b.isitonfavourites = jsonObject.getString("isitonfavourites");
//            b.isitonmarketlist = jsonObject.getString("isitonmarketlist");
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//        // Return new object
//        return b;
//    }

    public Products(JSONObject object) {
        try {
            productid        = object.getString("productid");
            name             = object.getString("name");
            price            = object.getString("price");
            market           = object.getString("market");
            marketid         = object.getString("marketid");
            marketimage      = object.getString("marketimage");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public static ArrayList<Products> fromJson(JSONArray jsonArray) {
//        JSONObject businessJson;
//        ArrayList<Products> businesses = new ArrayList<Products>(jsonArray.length());
//        // Process each result in json array, decode and convert to business object
//        for (int i=0; i < jsonArray.length(); i++) {
//            try {
//                businessJson = jsonArray.getJSONObject(i);
//            } catch (Exception e) {
//                e.printStackTrace();
//                continue;
//            }
//
//            Products products = Products.fromJson(businessJson);
//            if (products != null) {
//                businesses.add(products);
//            }
//        }
//
//        return businesses;
//    }


}
