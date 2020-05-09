package com.asd.mentesaudvel.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MarketFilter {

    String id;
    String name;
    String marketimage;
    String isItOnMarketFilter;


    public MarketFilter(JSONObject object) {
        try {
            id                 = object.getString("id");
            name               = object.getString("name");
            marketimage        = object.getString("marketimage");
            isItOnMarketFilter = object.getString("isItOnMarketFilter");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getid_filter(){
        return id;
    }

    public void setid_filter(String id){
        this.id = id;
    }

    public String getMarketName(){
        return name;
    }

    public void setMarketName(String name){
        this.name = name;
    }

    public String getMarketimage(){
        return marketimage;
    }

    public void setMarketimage(String marketimage){
        this.marketimage = marketimage;
    }

    public String getisItOnMarketFilter(){
        return isItOnMarketFilter;
    }

    public void setisItOnMarketFilter(String isItOnMarketFilter){
        this.isItOnMarketFilter = isItOnMarketFilter;
    }
}
