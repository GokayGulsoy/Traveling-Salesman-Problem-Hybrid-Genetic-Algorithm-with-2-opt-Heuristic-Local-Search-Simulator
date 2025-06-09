package tsp.genetic.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import tsp.genetic.entitites.Point;

/**
 * class to parse csv files to create coordinates to be used in the
 * genetic algorithm
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class FileIO {

    /**
     * method to parse csv file and fill coordinate ArrayList
     * of ContextManager object with Point objects using x and y
     * coordinates of locations given as ordered pairs
     * 
     * @param csvFilePath path to csv file containing coordinate for each point
     * @param coordinates ArrayList to be provided by ContextManager object
     *                    to be filled with Point objects
     */
    public static void parseCSVFileAndCreateCoordinates(String csvFilePath, ArrayList<Point> coordinates) {
        try {
            File csvFile = new File(csvFilePath);
            Scanner fileReader = new Scanner(csvFile);

            int locLabel = 1;
            while (fileReader.hasNextLine()) {
                String coordinateLine = fileReader.nextLine();
                String[] coordinate = coordinateLine.split(",");

                double xCoordinate = Double.parseDouble(coordinate[0]);
                double yCoordinate = Double.parseDouble(coordinate[1]);
                Point point = new Point(xCoordinate, yCoordinate, locLabel);
                locLabel++;

                // add point to coordinates
                coordinates.add(point);
            }

            fileReader.close();
        }

        catch (FileNotFoundException e) {
            System.err.println("Either csv file to read is not found or incorrect path is provided !!");
            e.printStackTrace();
        }

    }

}
