package com.asd.mentesaudvel.model;

import android.app.Application;

public class ListVal extends Application {

    boolean filterVal;
    boolean searchVal;
    boolean favouriteVal;

    public ListVal(boolean filterVal,boolean searchVal,boolean favouriteVal){
        this.filterVal =filterVal;
        this.searchVal = searchVal;
        this.favouriteVal = favouriteVal;
    }

    public boolean getfilterVal(){
        return filterVal;
    }

    public void setfilterVal(boolean filterVal){
        this.filterVal = filterVal;
    }
    public boolean getsearchVal(){
        return searchVal;
    }

    public void setsearchVal(boolean searchVal){
        this.searchVal = searchVal;
    }

    public boolean getfavouriteVal(){
        return favouriteVal;
    }

    public void setfavouriteVal(boolean favouriteVal){
        this.favouriteVal = favouriteVal;
    }
}
