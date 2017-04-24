package com.example.sitestoeat.presentation.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sitestoeat.R;
import com.example.sitestoeat.presentation.model.Restaurant;

import java.util.List;

/**
 * Created by wilsoncastiblanco on 8/20/16.
 */
public class RestaurantsAdapter extends ArrayAdapter<Restaurant> {

    List<Restaurant> restaurants;

    public RestaurantsAdapter(Context context, List<Restaurant> restaurantList){
        super(context, R.layout.item_restaurant ,restaurantList);
        this.restaurants = restaurantList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_restaurant, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if(position % 2 == 0){
            convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background_gray));
        }else{
            convertView.setBackgroundColor(Color.WHITE);
        }

        Restaurant restaurant = this.getItem(position);
        viewHolder.textViewRestaurantName.setText(restaurant.getName());
        viewHolder.textViewRestaurantSpeciality.setText(restaurant.getSpeciality());
        return convertView;
    }

    private class ViewHolder{
        TextView textViewRestaurantName;
        TextView textViewRestaurantSpeciality;

        public ViewHolder(View view){
            textViewRestaurantName = (TextView) view.findViewById(R.id.textViewRestaurantName);
            textViewRestaurantSpeciality = (TextView) view.findViewById(R.id.textViewRestaurantSpeciality);
        }
    }
}
