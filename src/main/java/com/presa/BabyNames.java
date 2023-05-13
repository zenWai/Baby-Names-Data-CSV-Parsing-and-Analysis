package com.presa;

import lib.csv.CSVRecord;
import lib.duke.DirectoryResource;
import lib.duke.FileResource;

import java.io.File;

/**
 * The BabyNames class provides various methods to analyze baby name data.
 * It provides functionality to calculate total births, get a rank of a baby name,
 * get a name associated with a given rank, find the year with the highest rank for a given name,
 * calculate the average rank for a name over all years and get the total number of births for names
 * ranked higher than a given name.
 * <p>
 * The class uses CSV files for data, which are expected to have fields for name, gender, and the number of births.
 * The CSV files are read from a directory specified by the DIRECTORYPATH constant and are named by year (e.g., yob1880.csv).
 * <p>
 * The rank of a name is defined as its order of popularity, with the most popular name having a rank of 1.
 * The average rank is calculated across all years for a given name and gender.
 * The year of highest rank is the year when the name was most popular (had the lowest rank).
 * The total number of births ranked higher is the sum of the number of births for names more popular than a given name.
 * <p>
 * Note that methods that return a rank or year return -1 if the name is not found.
 * The getName method returns "NO NAME FOUND" if the name is not found.
 * <p>
 * @author zenWAi
 */
public
class BabyNames {
    private static final String DIRECTORYPATH = "data/babynamesCSVfiles/us_babynames_by_year/yob";

    /**
     * Calculates and prints out the total number of births, as well as the total number of boys, girls,
       boy names and girl names in a chosen CSV file or files of baby name data.
     *
     */
    public static void totalBirths() {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int counterBoys = 0;
        int counterGirls = 0;
        DirectoryResource dr = new DirectoryResource();
        for ( File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            for ( CSVRecord rec : fr.getCSVParser(false) ) {
                int numBorn = Integer.parseInt(rec.get(2));
                totalBirths += numBorn;

                if ( rec.get(1).equals("M") ) {
                    totalBoys += numBorn;
                    counterBoys++;
                } else if ( rec.get(1).equals("F") ) {
                    totalGirls += numBorn;
                    counterGirls++;
                }

            }
        }
        if(totalBirths != 0) {
            System.out.println("Total number of Births = " + totalBirths);
            System.out.println("Total number of Girls = " + totalGirls);
            System.out.println("Total number of Boys = " + totalBoys);
            System.out.println("Total number of girl names " + counterGirls);
            System.out.println("Total number of boy names " + counterBoys);
        }
    }

    /**
     * Gets the rank of a baby name in a given year and gender from a CSV file.
       Rank is defined as the order of popularity, with the most popular name having a rank of 1.
     *
     * @param year   the year to search for the name
     * @param name   the name to get the rank for.
     * @param gender the gender of the name to search for ("M" for male, "F" for female)
     * @return the rank of the name in the given year and gender, or -1 if the name is not found.
     */
    public static
    int getRank(int year, String name, String gender) {
        int counter = 0;
        FileResource fr = new FileResource(DIRECTORYPATH + year + ".csv");
        for ( CSVRecord rec : fr.getCSVParser(false) ) {

            if ( rec.get(1).equals(gender) ) {
                counter += 1;
                if ( rec.get(0).equals(name) ) {
                    return counter;
                }
            }

        }
        return -1;
    }

    /**
     * Returns the name associated with the given rank, year, and gender based on the number of births.
     * Returns "NO NAME FOUND" if the rank is not found.
     *
     * @param year   the year to search for
     * @param rank   the rank to search for
     * @param gender the gender to search for
     * @return the name associated with the given rank, year, and gender, or "NO NAME" if the rank is not found
     */
    public static
    String getName(int year, int rank, String gender) {
        int counter = 0;
        String nameAtRank = "NO NAME FOUND";
        FileResource fr = new FileResource(DIRECTORYPATH +
                                            year + ".csv");
        for ( CSVRecord rec : fr.getCSVParser(false) ) {

            if ( rec.get(1).equals(gender) ) {
                counter += 1;
                if ( counter == rank ) {
                    nameAtRank = rec.get(0);
                }
            }

        }
        return nameAtRank;
    }

    /**
     * Returns the year with the highest rank for the given name and gender, or -1 if not found.
     *
     * @param name   the name to search for
     * @param gender the gender of the name to search for ("M" for male, "F" for female)
     * @return the year with the highest rank for the given name and gender, or -1 if not found
     */
    public static
    int yearOfHighestRank(String name, String gender) {
        int INITIALYEAR = 1880;
        int ENDYEAR = 2014;
        int higherRank = -1;
        int higherYearRank = -1;
        for ( int year = INITIALYEAR; year <= ENDYEAR; year++ ) {
            int currentRank = getRank(year, name, gender);

            if ( (higherRank == -1) && (currentRank != -1) ) {
                higherRank = currentRank;
                higherYearRank = year;
            } else if ( (higherRank > currentRank) && (currentRank != -1) ) {
                higherRank = currentRank;
                higherYearRank = year;
            }
        }
        return higherYearRank;
    }

    /**
     * Returns the average rank of the given name and gender over all available years, or -1 if the name is not ranked in any year.
     *
     * @param name   the name to search for
     * @param gender the gender of the name to search for ("M" for male, "F" for female)
     * @return the average rank of the given name and gender over all available years, or -1 if the name is not ranked in any year
     */
    public static
    double getAverageRank(String name, String gender) {
        int counter = 0;
        int INITIALYEAR = 1880;
        int ENDYEAR = 2014;
        double currentRank;
        double sumRank = 0;
        for ( int year=INITIALYEAR; year<=ENDYEAR; year++) {
            currentRank = getRank(year, name, gender);
            if ( currentRank != -1 ) {
                counter++;
                sumRank = sumRank + currentRank;
            }
        }
        if ( sumRank != 0 ) {
            return sumRank / counter;
        }
        return -1;
    }

    /**
     * Returns the total number of births of names that were ranked higher than the input name
       for the given year, gender and name.
     *
     * @param year   The year of the birth records to be searched.
     * @param name   The name whose ranking is to be determined.
     * @param gender The gender of the name whose ranking is to be determined.
     * @return The total number of births of names that were ranked higher than the input name.
     */
    public static int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int sumBirths = 0;
        FileResource fr = new FileResource(DIRECTORYPATH + year + ".csv");
        for ( CSVRecord rec : fr.getCSVParser(false) ) {
            if ( rec.get(1).equals(gender) ) {
                if ( rec.get(0).equals(name) ) {
                    break;
                } else {
                    sumBirths = sumBirths + Integer.parseInt(rec.get(2));
                }
            }
        }
        return sumBirths;
    }
}
