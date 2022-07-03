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
//     Runs the other classes and displays information about
//     the results to the console
//
//***************************************************************

public class Program8 {

    private FileProcessor outputFile;

    public Program8() {
        outputFile = new FileProcessor();
    }

    //**************************************************************
    //
    //  Method:       main
    //
    //  Description:  The main method of the program
    //
    //  Parameters:   String array
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public static void main(String[] args) {

        Program8 obj = new Program8();

        GasCalculator gasCalc = new GasCalculator();

        gasCalc.loadPrices("GasPrices.txt");

        obj.calcTester(gasCalc);
    }

    //**************************************************************
    //
    //  Method:       calcTester
    //
    //  Description:  Tests the calculations in GasCalculator and
    //                processes the output
    //
    //  Parameters:   GasCalculator gasCalc
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void calcTester(GasCalculator gasCalc) {
        // Get first year and last year of file
        // accounts for files of different sizes of data
        int startYear = gasCalc.getFirstYear();
        int endYear = gasCalc.getLastYear();

        developerInfo();

        // Make sure the file can be opened
        if (outputFile.openWrite("Project8-output.txt")) {
            // Make sure the file can be written to
            if (outputFile.writeToFile(String.format("%s%n%s%n%s%n", "Name:    Cory Munselle", "Course:  COSC 4301 Modern Programming", "Program: Eight \n"))) {
                writeAndPrint("-------------Average Gas Prices per Year-------------\n");
                // Loop through the years, getting the yearly average of each
                for (int i = startYear; i <= endYear; i++) {
                    writeAndPrint(String.format("%s%d%s%.3f%n", "The average gas price in ", i, " is: $", gasCalc.avgYearly(String.valueOf(i))));
                }

                writeAndPrint("\n");
                writeAndPrint("-------------Average Gas Prices per Month-------------\n");
                // Loop through months and years, getting average of each month in each year
                for (int i = startYear; i <= endYear; i++) {
                    // I figured hard coding this is fine since there's always going to be 12 months
                    for (int j = 1; j <= 12; j++) {
                        if (gasCalc.avgMonthly(String.format("%02d", j), String.format("%02d", i)) > 0.0) {
                            writeAndPrint(String.format("%s%02d%s%d%s%.3f%n", "The average gas price in ", j, " of ", i, " is: $", gasCalc.avgMonthly(String.format("%02d", j), String.format("%02d", i))));
                        }
                    }
                }

                writeAndPrint("\n");
                writeAndPrint("-------------Highest and Lowest Prices per Year-------------\n");
                // Loop through the years, getting the highest and lowest prices of each year
                for (int i = startYear; i <= endYear; i++) {
                    writeAndPrint(String.format("%s%d%n", "Year: ", i));
                    writeAndPrint(String.format("%s%.3f%s%s%n%s%.3f%s%s%n%n", "Highest gas price: $", gasCalc.highestYearly(String.valueOf(i)), " Date: ", gasCalc.getDate(), "Lowest gas price: $", gasCalc.lowestYearly(String.valueOf(i)), " Date: ", gasCalc.getDate()));
                }

                writeAndPrint("\n");
                writeAndPrint("Attempting to write sorted Gas Prices from Lowest to Highest...\n");

                // Sort gas prices from lowest to highest and write to file
                if (gasCalc.sortLowHigh()) {
                    writeAndPrint("Successfully written to 'Program8-LowHigh.txt'.\n");
                }
                // if it failed to return true the file wasn't successfully written
                else {
                    writeAndPrint("Failed to write.\n");
                }

                writeAndPrint("\n");
                writeAndPrint("Attempting to write sorted Gas Prices from Highest to Lowest...\n");
                // Sort gas prices from highest to lowest and write to file
                if (gasCalc.sortHighLow()) {
                    writeAndPrint("Successfully written to 'Program8-HighLow.txt'.\n");
                }
                // if it failed to return true the file wasn't successfully written
                else {
                    writeAndPrint("Failed to write.\n");
                }
            }
        }
        // close the file for bookkeeping
        outputFile.closeWrite();
    }

    //**************************************************************
    //
    //  Method:       writeAndPrint
    //
    //  Description:  Helper function to both print to console and write
    //                to file with a single passed in string
    //
    //  Parameters:   String str
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void writeAndPrint(String str) {
        outputFile.uncheckedWrite(str);
        System.out.print(str);
    }

    //***************************************************************
    //
    //  Method:       developerInfo
    //
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void developerInfo()
    {
        System.out.println("Name:    Cory Munselle");
        System.out.println("Course:  COSC 4301 Modern Programming");
        System.out.println("Program: Eight \n");

    } // End of developerInfo
}
