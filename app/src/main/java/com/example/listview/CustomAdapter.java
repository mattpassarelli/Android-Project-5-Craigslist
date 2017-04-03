package com.example.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class CustomAdapter extends ArrayAdapter<BikeData> {

    private String model, description;
    private double price;


    public CustomAdapter(@NonNull Context context, List<BikeData> resource) {
        super(context, R.layout.listview_row_layout ,resource);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View customView = inflater.inflate(R.layout.listview_row_layout, parent, false);


        BikeData bike = getItem(position);
        if (bike != null) {
            model = bike.getModel();
            description = bike.getDescription();
            price = bike.getPrice();
        }


        TextView modelField = (TextView) customView.findViewById(R.id.Model);
        TextView descriptionField = (TextView) customView.findViewById(R.id.Description);
        TextView priceField = (TextView) customView.findViewById(R.id.Price);


        modelField.setText(model);
        descriptionField.setText(description);
        priceField.setText("$" + price);

        return customView;
    }
}
