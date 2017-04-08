package com.example.listview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


class CustomAdapter extends ArrayAdapter<BikeData> {

    private final LayoutInflater layoutInflater;


    CustomAdapter(@NonNull Context context, List<BikeData> resource) {
        super(context, R.layout.listview_row_layout, resource);
        layoutInflater = LayoutInflater.from(context);
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_row_layout, null);
            holder = new ViewHolder();
            holder.model = (TextView) convertView.findViewById(R.id.Model);
            holder.description = (TextView) convertView.findViewById(R.id.Description);
            holder.price = (TextView) convertView.findViewById(R.id.Price);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BikeData bike = getItem(position);

        if (bike != null) {
            holder.model.setText(bike.getModel());
            holder.description.setText(bike.getDescription());
            holder.price.setText("$" + bike.getPrice());
            if (holder.icon != null) {
                new DownloadImageTask("", holder.icon).execute("http://www.tetonsoftware.com/bikes/" + bike.getPicture());
            }
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView model;
        TextView description;
        TextView price;
        ImageView icon;
    }
}
