package com.example.listview;

import java.util.Comparator;

class ComparatorModelPrice implements Comparator<BikeData> {
    @Override
    public int compare(BikeData lhs, BikeData rhs) {

        if (lhs == rhs) {
            return 0;
        }

        return (Double.compare(lhs.getPrice(), rhs.getPrice()));
    }
}
