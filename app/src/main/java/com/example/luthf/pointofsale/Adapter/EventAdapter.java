package com.example.luthf.pointofsale.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luthf.pointofsale.R;

import java.util.List;

/**
 * Created by luthf on 9/29/2015.
 */
public class    EventAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> movieItems;

    public EventAdapter(Activity activity, List<Event> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item, null);





        TextView name = (TextView) convertView.findViewById(R.id.txtview);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView sum = (TextView) convertView.findViewById(R.id.sum);

        // getting movie data for the row
        Event m = movieItems.get(position);


        // title
        name.setText(m.getName());

        // rating
        price.setText(m.getPrice());

        sum.setText(m.getSum());

        // release year


        return convertView;
    }

}
