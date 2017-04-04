package com.example.listview;

import java.util.Comparator;

class ComparatorLocation implements Comparator<BikeData> {

    @Override
    public int compare(BikeData lhs, BikeData rhs) {

        if (lhs == rhs) {
            return 0;
        }

        return (lhs.getLocation().compareTo(rhs.getLocation()));
    }
}
