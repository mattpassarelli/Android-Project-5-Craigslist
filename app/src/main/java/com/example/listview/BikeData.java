package com.example.listview;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * See builderpattern example project for how to do builders
 * they are essential when constructing complicated objects and
 * with many optional fields
 */
public class BikeData {
    public static final int COMPANY = 0;
    public static final int MODEL = 1;
    public static final int PRICE = 2;
    public static final int LOCATION = 3;
    private final String company;
    private final String model;
    private final double price;
    private final String description;
    private final String location;
    private final String date;
    private final String picture;
    private final String link;

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Company: " + company +
                "\n" +
                "Model: " + model +
                "\n" +
                "Price: $" + price +
                "\n" +
                "Location: " + location +
                "\n" +
                "Date Listed: " + date +
                "\n" +
                "Description: " + description +
                "\n" +
                "Link: " + link;
    }

    private BikeData(Builder b) {
        company = b.Company;
        model = b.Model;
        price = b.Price;
        description = b.Description;
        location = b.Location;
        date = b.Date;
        picture = b.Picture;
        link = b.Link;
    }

    public String getCompany()
    {
        return company;
    }

    public String getModel()
    {
        return model;
    }

    public double getPrice()
    {
        return price;
    }

    public String getDescription()
    {
        return description;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDate()
    {
        return date;
    }

    public String getPicture()
    {
        return picture;
    }

    public String getLink()
    {
        return link;
    }




    /**
     * @author lynn builder pattern, see page 11 Effective Java UserData mydata
     *         = new
     *         UserData.Builder(first,last).addProject(proj1).addProject(proj2
     *         ).build()
     */
    public static class Builder {
        final String Company;
        final String Model;
        final Double Price;
        String Description;
        String Location;
        String Date;
        String Picture;
        String Link;

        // Model and price required
        Builder(String Company, String Model, Double Price) {
            this.Company = Company;
            this.Model = Model;
            this.Price = Price;
        }

        // the following are setters
        // notice it returns this bulder
        // makes it suitable for chaining
        Builder setDescription(String Description) {
            this.Description = Description;

            return this;
        }

        Builder setLocation(String Location) {
            this.Location = Location;

            return this;
        }

        Builder setDate(String Date) {

            this.Date = Date;

            return this;
        }

        Builder setPicture(String Picture) {
            this.Picture = Picture;

            return this;
        }

        Builder setLink(String Link) {
            this.Link = Link;


            return this;
        }

        // use this to actually construct Bikedata
        // without fear of partial construction
        public BikeData build() {
            return new BikeData(this);
        }
    }
}
