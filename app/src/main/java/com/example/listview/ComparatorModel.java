package com.example.listview;

import java.util.Comparator;

class ComparatorModel implements Comparator<BikeData> {

    @Override
    public int compare(BikeData lhs, BikeData rhs) {
        return (lhs.getModel().compareTo(rhs.getModel()));
    }
}
