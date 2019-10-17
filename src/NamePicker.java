// Name:  Philip Ferreira
// VUnetID: ferreipp
// Email: philip.p.ferreira@vanderbilt.edu
// Class: CS 1101 - Vanderbilt University
// Section: 001 (MWF 11:10-12:00)
// Honor statement: I have not given or received unauthorized aid on this assignment.
// Date: ************
// Description: ************

import java.util.*;
import java.io.*;

public class NamePicker {

    //some class constants
    public static final String HEADER = "IG Name,Entries ,,Total ";
    public static final long WAIT_TIME = 1000;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        String headerLine = "";
        Scanner keyboard = new Scanner(System.in);

        //creates File and Scanner objects for given Filename
        File giveawayNames = getFileName(keyboard); // method for getting file name
        Scanner fileReader = new Scanner(giveawayNames);

        //checks if file fits given template
        if (!fileReader.hasNext() || !fileReader.nextLine().equals(HEADER)) {

            throw new FileNotFoundException("This file does not fit the required template");
        }

        //tells user the file has been accepted
        Thread.sleep(WAIT_TIME);
        System.out.print("File accepted");
        Thread.sleep(WAIT_TIME);
        System.out.print("\n\n");


        //gets the array size from "total" entry
        String firstLine = fileReader.nextLine();
        int arraySize = getArraySize(firstLine);

        //creates name array
        String[] nameArray = new String[arraySize];

        //method for populating entire array
        arrayPopulator(firstLine, nameArray, fileReader);

        //fun way to announce winner (delays the process)
        System.out.print("And the winner is");
        for (int i = 0; i < 3; ++i) {

            Thread.sleep(WAIT_TIME);
            System.out.print(".");
        }
        Thread.sleep(WAIT_TIME);
        System.out.print("\n");

        //method to pick random name
        System.out.println(pickRandName(nameArray));
        System.out.print("\n\n\n\n");
    }

    /**
     * getArraySize -
     * Returns size of array to create from "total" entry in file
     *
     * @param firstLine - first line of info in file
     * @return -- int, array size
     */
    public static int getArraySize(String firstLine) {


        //tokenizer to read first line
        StringTokenizer lineReader = new StringTokenizer(firstLine, ",");

        //burns irrelevant entries
        lineReader.nextToken();
        lineReader.nextToken();

        return Integer.parseInt(lineReader.nextToken());
    }

    /**
     * arrayPopulator -
     * populates the entire array with the names of contestants based on their entries.
     * Updates array implicitly
     *
     * @param firstLine  - first line of info. Needed to get total entry so needs to be
     *                   treated differently
     * @param names      - name array to populate, updated implicitly
     * @param fileReader - Scanner pointing to file
     */
    public static void arrayPopulator(String firstLine, String[] names, Scanner fileReader) {

        //some initializations and declarations
        int nameIndex = 0;
        int timesToRepeat;
        String nextLine;
        String currentName;

        //creates tokenizer for first line
        StringTokenizer firstLineReader = new StringTokenizer(firstLine, ",");

        //method for populating from firstline
        nameIndex = firstLinePopulator(firstLineReader, names, nameIndex);

        //while array still has entries
        while (nameIndex < names.length) {

            //create tokenizer for next line
            nextLine = fileReader.nextLine();
            StringTokenizer lineReader = new StringTokenizer(nextLine, ",");

            //gets current name and times to input it
            currentName = lineReader.nextToken();
            timesToRepeat = Integer.parseInt(lineReader.nextToken());

            //loop for populating array
            for (int i = 0; i < timesToRepeat; ++i) {

                names[nameIndex] = currentName;
                ++nameIndex;
            }

        }

    }

    /**
     * firstLinePopulator -
     * Helper method. Populates array from first line that has already been created in main
     *
     * @param firstLine -- String, First line of info in file
     * @param names     -- name array
     * @param nameIdx   - which element in array to populate
     * @return -- keep track of nameIdx in caller method
     */
    private static int firstLinePopulator(StringTokenizer firstLine, String[] names,
                                          int nameIdx) {

        //gets name and times to repeat name
        String name = firstLine.nextToken();
        int repeat = Integer.parseInt(firstLine.nextToken());

        //populates elements of array
        for (int i = 0; i < repeat; ++i) {

            names[nameIdx] = name;
            ++nameIdx;
        }
        return nameIdx;
    }

    /**
     * pickRandName -
     * Takes name array and picks a random name from it
     *
     * @param names -- name array
     * @return -- String, random name
     */
    public static String pickRandName(String[] names) {

        //create random number generator
        Random rand = new Random();

        return names[rand.nextInt(names.length)];

    }

    /**
     * getFileName -
     * Takes user input to find the file to read
     *
     * @param input -- Scanner, reads user input
     * @return -- File, file to read
     */
    public static File getFileName(Scanner input) {

        //prompt and creates initial object
        System.out.println("Please enter the file name or drag and drop it into this" +
                " " +
                "window:");
        String filePath = input.nextLine();
        //method for formatted user inputted path
        File fileToReturn = new File(formatFileName(filePath));

        //while the file does not exist, ask again
        while (!fileToReturn.exists()) {

            //re-prompt
            System.out.println("Sorry. That file does not exist. Please enter the file " +
                    "to read:");
            filePath = input.nextLine();

            //method for formatting path
            fileToReturn = new File(formatFileName(filePath));
        }

        //return the file
        return fileToReturn;
    }

    private static String formatFileName(String oldFileName) {

        return oldFileName.replace("\\", "").trim();
    }


}
