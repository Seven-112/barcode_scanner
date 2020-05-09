package com.asd.mentesaudvel.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.asd.mentesaudvel.R;
import com.asd.mentesaudvel.model.MarketFilter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemFilterAdapter extends ArrayAdapter<MarketFilter> {

    List<MarketFilter> markets;
    private Activity context;

    public ItemFilterAdapter(Activity context, List<MarketFilter> markets) {
        super(context, R.layout.layout_item_filterlist, markets );

        this.context = context;
        this.markets = markets;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View filterItem = inflater.inflate(R.layout.layout_item_filterlist, null, true);

        ImageView imageType = (ImageView) filterItem.findViewById(R.id.imageType);
        TextView itemTxt = (TextView) filterItem.findViewById(R.id.itemTxt);
        Switch switchBtn = (Switch) filterItem.findViewById(R.id.switchBtn);

        MarketFilter Item = markets.get(position);

        Picasso.get().load(Item.getMarketimage()).into(imageType);

        itemTxt.setText(Item.getMarketName());

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "unChecked", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return filterItem;

    }
}
