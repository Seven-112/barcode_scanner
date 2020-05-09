package com.asd.mentesaudvel.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanProducts {

    String productid;
    String name;
    String price;
    String market;
    String marketid;
    String marketimage;
    String isitonfavourites;
    String isitonmarketlist;

    public ScanProducts(JSONObject object) {
        try {
            productid        = object.getString("productid");
            name             = object.getString("name");
            price            = object.getString("price");
            market           = object.getString("market");
            marketid         = object.getString("marketid");
            marketimage      = object.getString("marketimage");
            isitonfavourites = object.getString("isitonfavourites");
            isitonmarketlist = object.getString("isitonmarketlist");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getproductid_scan(){
        return productid;
    }

    public void setproductid_scan(String productid){
        this.productid = productid;
    }

    public String getname_scan(){
        return name;
    }

    public String getprice_scan(){
        return price;
    }

    public String getmarket_scan(){
        return market;
    }

    public String getmarketid_scan(){
        return marketid;
    }

    public String getmarketimage_scan(){
        return marketimage;
    }

    public String getisitonfavourites_scan(){
        return isitonfavourites;
    }

    public String getisitonmarketlist_scan(){
        return isitonmarketlist;
    }
}
