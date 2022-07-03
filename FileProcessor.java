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
//     Handles file operations for the program
//
//***************************************************************

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.IllegalFormatException;
import java.util.FormatterClosedException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Formatter;

public class FileProcessor {

    private Scanner input;
    private Formatter output;

    //**************************************************************
    //
    //  Method:       openFile
    //
    //  Description:  Attempts to open file with gas data
    //
    //  Parameters:   String filename
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void openFile(String filename) {
        try {
            input = new Scanner(Paths.get(filename));
        }
        catch (IOException ioException) {
            System.err.println("Error opening file. Please verify that it is a file and try again.");
        }
    }

    //**************************************************************
    //
    //  Method:       processFile
    //
    //  Description:  Processes the gasdata file and fills list with GasPrice objects
    //
    //  Parameters:   LinkedList<GasPrice> gasPrices
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void processFile(LinkedList<GasPrice> gasPrices) {

        // Note: Since objects are passed by reference I took
        // advantage of that by not returning anything and just
        // filling the passed in list with the objects
        while (input.hasNextLine()) {
            String line = input.nextLine();

            double gasPrice = 0.0;

            // Regex to split based on hyphens (-) and colons (:)
            String[] pieces = line.split("[:\\-]");

            // attempt to parse double from file
            try {
                gasPrice = Double.parseDouble(pieces[3]);
            }
            // shouldn't happen unless the file is formatted incorrectly
            catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.err.println("Error occurred.");
                e.printStackTrace();
            }

            // try to add new object to list
            try {
                gasPrices.add(new GasPrice(pieces[0], pieces[1], pieces[2], gasPrice));
            }
            // Once again, shouldn't happen if the file is formatted correctly
            catch (NullPointerException | IndexOutOfBoundsException e) {
                System.err.println("Error occurred. Printing stack trace...");
                e.printStackTrace();
            }
        }
        // close the file since we're done with it
        closeFile();
    }

    //**************************************************************
    //
    //  Method:       openWrite
    //
    //  Description:  Opens a file for writing to
    //
    //  Parameters:   String openFile
    //
    //  Returns:      boolean success
    //
    //**************************************************************
    public boolean openWrite(String openFile) {
        // Extra checking to prevent the program from proceeding
        // if the file can't be properly opened
        boolean success = false;

        // Tries to open file for writing
        try {
            output = new Formatter("./" + openFile);
            success = true;
        }
        // shouldn't ever happen since it just creates a new file but
        // just in case
        catch (FileNotFoundException e) {
            System.err.println("Unable to locate file. Printing stack trace...");
            e.printStackTrace();
        }

        return success;
    }

    //**************************************************************
    //
    //  Method:       writeToFile
    //
    //  Description:  Attempts to write the passed in string to opened file
    //
    //  Parameters:   String str
    //
    //  Returns:      boolean success
    //
    //**************************************************************
    public boolean writeToFile(String str) {
        // Extra checking to prevent the program from proceeding
        // if the file can't be properly written to
        boolean success = false;

        try {
            output.format(str);
            success = true;
        }
        catch (IllegalFormatException | FormatterClosedException e) {
            System.err.println("Failed to write line to file. Skipping...");
        }
        return success;
    }

    //**************************************************************
    //
    //  Method:       uncheckedWrite
    //
    //  Description:  Writes the passed in string to opened file
    //
    //  Parameters:   String str
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void uncheckedWrite(String str) {
        // No verification here since I didn't want to have to check every time.
        // Only used when writing the full program output.
        try {
            output.format(str);
        }
        // Once again, this shouldn't happen, but just in case.
        catch (IllegalFormatException | FormatterClosedException e) {
            System.err.println("Failed to write line to file. Skipping...");
        }
    }

    //**************************************************************
    //
    //  Method:       closeWrite
    //
    //  Description:  Closes the opened write file
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void closeWrite() {
        output.close();
    }

    //**************************************************************
    //
    //  Method:       closeFile
    //
    //  Description:  Closes the gasprices file
    //
    //  Parameters:   None
    //
    //  Returns:      N/A
    //
    //**************************************************************
    public void closeFile() {
        input.close();
    }
}
