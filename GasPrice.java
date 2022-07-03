//***************************************************************
//
//  Developer:         Cory Munselle
//
//  Program #:         8
//
//  File Name:         Program8.java
//
//  Course:            COSC 4301 - Modern Programming
//
//  Due Date:          04/20/22
//
//  Instructor:        Prof. Fred Kumi
//
//  Description:
//     Holds basic information about a single gas data point
//
//***************************************************************

public class GasPrice {

    private final String day;
    private final String month;
    private final String year;
    private final double price;

    //**************************************************************
    //
    //  Method:       Constructor(s)
    //
    //  Description:  Initializes most used variables
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public GasPrice(String month, String day, String year, double price) {

        // The code is set up so it doesn't matter what's in these
        // if there's something weird in the strings it just doesn't
        // find anything in the gas calculator so I figured it was fine
        // to not check them
        this.day = day;
        this.month = month;
        this.year = year;

        // This is just in case the price is wrong, prevents weird calculations
        if (price < 0.0) {
            this.price = 0;
        }
        else {
            this.price = price;
        }

    }

    // default constructor, only used in fringe cases where the max and min aren't found
    public GasPrice() {
        this("", "", "", 0);
    }

    //**************************************************************
    //
    //  Method:       Getters
    //
    //  Description:  Returns attributes of the class
    //
    //  Parameters:   None
    //
    //  Returns:      String day, String month, String year, double price
    //
    //**************************************************************
    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    //**************************************************************
    //
    //  Method:       getDate
    //
    //  Description:  Returns date formatted in the same way as input file
    //
    //  Parameters:   None
    //
    //  Returns:      String getMonth()-getDay()-getYear()
    //
    //**************************************************************
    public String getDate() {
        return String.format("%s-%s-%s", getMonth(), getDay(), getYear());
    }

    //**************************************************************
    //
    //  Method:       toString
    //
    //  Description:  Overridden toString implementation for convenient output
    //
    //  Parameters:   None
    //
    //  Returns:      String Date: getMonth()-getDay()-getYear() Price: $getPrice()
    //
    //**************************************************************
    @Override
    public String toString() {
        return String.format("%s%s%s%s%s%s%s%.3f", "Date: ", getDay(), "-", getMonth(), "-", getYear(), " Price: $", getPrice());
    }
}
