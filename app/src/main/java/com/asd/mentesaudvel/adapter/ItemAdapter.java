package com.asd.mentesaudvel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.mentesaudvel.MainActivity;
import com.asd.mentesaudvel.R;
import com.asd.mentesaudvel.model.Item;
import com.asd.mentesaudvel.model.ListVal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_LONG;

public class ItemAdapter extends ArrayAdapter<Item> {


    Context context;
    int layoutResourceId;
    ArrayList<Item> data = new ArrayList<Item>();
    private List<Item> IconTextList = null;
    private ArrayList<Item> arraylist;
    private ListVal listVal;

    public ItemAdapter(Context context, int layoutResourceId,
                       ArrayList<Item> data,ListVal listVal) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.listVal = listVal;

        this.IconTextList = data;
        this.arraylist = new ArrayList<Item>();
        this.arraylist.addAll(IconTextList);

    }

    @Override
    public int getCount() {
        return IconTextList.size();
    }

    @Override
    public Item getItem(int position) {
        return IconTextList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        itemHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new itemHolder();
            holder.textName = (TextView) row.findViewById(R.id.itemTxt);
            holder.textQuantity = (TextView) row.findViewById(R.id.quantityTxt);
            holder.btnFavourite = (Button) row.findViewById(R.id.favBtn);
            holder.btnAdd = (Button) row.findViewById(R.id.addbtn);
            holder.btnSubtract = (Button) row.findViewById(R.id.SubtractBtn);
            holder.imageType = (ImageView) row.findViewById(R.id.imageType);
            row.setTag(holder);
        }
        else {
            holder = (itemHolder) row.getTag();
        }
        Item item = data.get(position);
        holder.textName.setText(item.getName());
        holder.textQuantity.setText(item.getquantity());
        holder.imageType.setImageResource(item.getType());


        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Add button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Subtract button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Favourite button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //visible or invisible add,favourite and subtract Btn and Image.

        if(listVal.getfavouriteVal()){
            holder.imageType.setVisibility(View.INVISIBLE);
            holder.btnAdd.setVisibility(View.GONE);
            holder.btnFavourite.setVisibility(View.GONE);
        }
        if(listVal.getsearchVal()){
            holder.btnAdd.setVisibility(View.GONE);
            holder.btnFavourite.setVisibility(View.GONE);
        }
        if(listVal.getfilterVal()){
            holder.btnSubtract.setVisibility(View.GONE);
        }

        return row;
    }

    static class itemHolder {
        TextView textName;
        TextView textQuantity;
        Button btnAdd;
        Button btnSubtract;
        Button btnFavourite;
        ImageView imageType;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        IconTextList.clear();
        if (charText.length() == 0) {
            IconTextList.addAll(arraylist);
        } else {
            for (Item ICCC : arraylist) {
                if (ICCC.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    IconTextList.add(ICCC);
                }
            }
        }
        notifyDataSetChanged();
    }

}