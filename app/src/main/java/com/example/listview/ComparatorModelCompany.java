package com.example.listview;

import java.util.Comparator;

class ComparatorCompany implements Comparator<BikeData> {

    @Override
    public int compare(BikeData lhs, BikeData rhs) {

        if (lhs == rhs) {
            return 0;
        }

        return (lhs.getCompany().compareTo(rhs.getCompany()));
    }
}
