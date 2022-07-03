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
//     Runs calculations based on the aggregate gas data
//
//***************************************************************

import java.util.Comparator;
import java.util.LinkedList;

public class GasCalculator {

    private final LinkedList<GasPrice> gasPrices;
    private final FileProcessor fileIO;
    private String currDate;

    //**************************************************************
    //
    //  Method:       Constructor
    //
    //  Description:  Initializes most used variables
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public GasCalculator() {
        gasPrices = new LinkedList<>();
        fileIO = new FileProcessor();
        currDate = "";
    }

    //**************************************************************
    //
    //  Method:       loadPrices
    //
    //  Description:  Populates gasPrices LinkedList with GasPrice objects
    //                derived from file data
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void loadPrices(String filename) {
        fileIO.openFile(filename);
        fileIO.processFile(gasPrices);
        fileIO.closeFile();
    }

    //**************************************************************
    //
    //  Method:       debugGasPrices
    //
    //  Description:  Displays full LinkedList contents after population
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void debugGasPrices() {
        for (GasPrice gasPrice : gasPrices) {
            System.out.println(gasPrice.toString());
        }
    }


    //**************************************************************
    //
    //  Method:       avgYearly
    //
    //  Description:  Calculates the average gas price for passed in year
    //
    //  Parameters:   String year
    //
    //  Returns:      double gasAvg
    //
    //**************************************************************
    public double avgYearly(String year) {
        double gasAvg = 0;

        // Scary stuff, but it essentially converts the gasPrices list
        // into a stream, filters out everything except for the passed in
        // year, generates a map, and sums that map.
        // This is divided by the number of entries in that year.
        try {
            gasAvg = gasPrices.stream()
                    .filter(gasPrice -> gasPrice.getYear().matches(year))
                    .mapToDouble(GasPrice::getPrice).sum()
                    /
                    gasPrices.stream()
                            .filter(gasPrice -> gasPrice.getYear().matches(year))
                            .count();
        }
        // Should never be hit, but we want to avoid any division by zero
        // so I added it just in case
        catch (ArithmeticException e) {
            System.out.println("No values found for that year. Returning zero.");
        }

        return gasAvg;
    }

    //**************************************************************
    //
    //  Method:       avgMonthly
    //
    //  Description:  Calculates the average gas price for passed in year and month
    //
    //  Parameters:   String month, String year
    //
    //  Returns:      double gasAvg
    //
    //**************************************************************
    public double avgMonthly(String month, String year) {
        double gasAvg = 0;

        // Same process as avgYearly, but with month added: convert to
        // stream, filter out everything except for the passed in
        // year and month, generate a map, and sum that map.
        // This is divided by the number of entries in that year and month.
        try {
            gasAvg = gasPrices.stream()
                    .filter(gasPrice -> gasPrice.getYear().matches(year) && gasPrice.getMonth().matches(month))
                    .mapToDouble(GasPrice::getPrice).sum()
                    /
                    gasPrices.stream()
                            .filter(gasPrice -> gasPrice.getYear().matches(year) && gasPrice.getMonth().matches(month))
                            .count();
        }
        // No division by zero allowed
        catch (ArithmeticException e) {
            System.out.println("No values found for that year. Returning zero.");
        }

        return gasAvg;
    }

    //**************************************************************
    //
    //  Method:       highestYearly
    //
    //  Description:  Calculates the highest gas price of the year passed in
    //
    //  Parameters:   String year
    //
    //  Returns:      max.getPrice()
    //
    //**************************************************************
    public double highestYearly(String year) {
        // Generate stream, filter based on year, find the maximum based on a comparison of gas prices.
        // If nothing is found, generate a new GasPrice object with the default values and return that.
        // Prevents errors.
        GasPrice max = gasPrices.stream().filter(gasPrice -> gasPrice.getYear().matches(year)).max(Comparator.comparingDouble(GasPrice::getPrice)).orElse(new GasPrice());
        currDate = max.getDate();
        return max.getPrice();
    }

    //**************************************************************
    //
    //  Method:       lowestYearly
    //
    //  Description:  Calculates the lowest gas price of the year passed in
    //
    //  Parameters:   String year
    //
    //  Returns:      min.getPrice()
    //
    //**************************************************************
    public double lowestYearly(String year) {
        // Generate stream, filter based on year, find the minimum based on a comparison of gas prices.
        // If nothing is found, generate a new GasPrice object with the default values and return that.
        // Prevents errors.
        GasPrice min = gasPrices.stream().filter(gasPrice -> gasPrice.getYear().matches(year)).min(Comparator.comparingDouble(GasPrice::getPrice)).orElse(new GasPrice());

        // Get the current date of the new object for display purposes later
        currDate = min.getDate();
        return min.getPrice();
    }

    //**************************************************************
    //
    //  Method:       sortHighLow
    //
    //  Description:  Sorts the gasPrices list from highest gas price to lowest
    //
    //  Parameters:   None
    //
    //  Returns:      boolean writeSuccess
    //
    //**************************************************************
    public boolean sortHighLow() {
        boolean writeSuccess = true;

        // Sorting defaults to ascending order, so it needs to be reversed to get
        // descending order. Otherwise, just sort based on gas price comparisons.
        gasPrices.sort(Comparator.comparingDouble(GasPrice::getPrice).reversed());

        // Extra checks to make sure the file is okay to be written to. Prevents
        // any rogue writing errors.
        if (fileIO.openWrite("Program8-HighLow.txt")) {
            for (GasPrice gasPrice : gasPrices) {
                // if the write fails at any point, return false and report to user
                // that it failed
                if (!fileIO.writeToFile(gasPrice.toString() + "\n")) {
                    writeSuccess = false;
                }
            }
        }
        else {
            writeSuccess = false;
        }

        // close file for bookkeeping
        fileIO.closeWrite();
        return writeSuccess;
    }

    //**************************************************************
    //
    //  Method:       sortLowHigh
    //
    //  Description:  Sorts the gasPrices list from lowest gas price to highest
    //
    //  Parameters:   None
    //
    //  Returns:      boolean writeSuccess
    //
    //**************************************************************
    public boolean sortLowHigh() {
        boolean writeSuccess = true;

        // all of this is the same as the method above
        gasPrices.sort(Comparator.comparingDouble(GasPrice::getPrice));

        if (fileIO.openWrite("Program8-LowHigh.txt")) {
            for (GasPrice gasPrice : gasPrices) {
                if (!fileIO.writeToFile(gasPrice.toString() + "\n")) {
                    writeSuccess = false;
                }
            }
        }
        else {
            writeSuccess = false;
        }

        fileIO.closeWrite();
        return writeSuccess;
    }

    //**************************************************************
    //
    //  Method:       getLastYear
    //
    //  Description:  Helper function to get upper bound of dataset
    //
    //  Parameters:   None
    //
    //  Returns:      int yearInt
    //
    //**************************************************************
    public int getLastYear() {
        int yearInt = 1;

        // Get the last gasprice object in the list and try to convert the
        // year to an int. If it fails just return 1 to prevent looping issues.
        try {
            yearInt = Integer.parseInt(gasPrices.getLast().getYear());
        }
        catch (NumberFormatException e) {
            System.err.println("Couldn't convert year to integer. Returning default value...");
        }

        return yearInt;
    }

    //**************************************************************
    //
    //  Method:       getLastYear
    //
    //  Description:  Helper function to get lower bound of dataset
    //
    //  Parameters:   None
    //
    //  Returns:      int yearInt
    //
    //**************************************************************
    public int getFirstYear() {
        int yearInt = 0;

        // Get the first gasprice object in the list and try to convert the
        // year to an int. If it fails just return 0.
        try {
            yearInt = Integer.parseInt(gasPrices.getFirst().getYear());
        }
        catch (NumberFormatException e) {
            System.err.println("Couldn't convert year to integer. Returning default value...");
        }

        return yearInt;
    }

    //**************************************************************
    //
    //  Method:       getDate
    //
    //  Description:  Helper function to get current date of last calculated gasprice object
    //
    //  Parameters:   None
    //
    //  Returns:      String currData
    //
    //**************************************************************
    public String getDate() {
        return currDate;
    }
}
