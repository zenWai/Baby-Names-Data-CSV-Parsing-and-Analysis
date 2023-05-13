package com.presa;

import java.util.InputMismatchException;
import java.util.Scanner;
public class UserInterface {
    public Scanner scanner;
    public UserInterface() {
        this.scanner = new Scanner(System.in);
        start(scanner);
    }

    public void start(Scanner scanner) {
        startBabyNamesControl(scanner);

        System.out.println();
    }

    public void startBabyNamesControl(Scanner scanner) {
        System.out.println("Baby Names Interesting Information");
        System.out.println("--------------------");
        System.out.println();
        boolean notEnd = true;
        while (notEnd) {
            System.out.println("Choose an action:");
            System.out.println("[1] Choose file(s) and get all Births info");
            System.out.println("[2] Get the RANK of a baby name you want for the given year and gender.");
            System.out.println("[3] Get the NAME for the given rank, year and gender.");
            System.out.println("[4] Get the YEAR with the highest rank for the given name and gender.");
            System.out.println("[5] Get the AVERAGE RANK for the given name and gender.");
            System.out.println("[6] Get the TOTAL NUMBER OF BIRTHS of names that were ranked higher than the given name.");
            System.out.println("[x] Exit Baby Names");
            System.out.print("> ");
            String answer = scanner.nextLine();

            switch (answer) {
                case "1" -> BabyNames.totalBirths();
                case "2" -> {
                    String name = askName(scanner);
                    int year = askYear(scanner);
                    String gender = askGender(scanner);
                    int rank = BabyNames.getRank(year, name, gender);
                    if(rank == -1) {
                        System.out.printf("The name %s was not found on year %d according to gender %s.%n", name, year, gender);
                    } else {
                        System.out.printf("Rank of name %s, on year %d according to gender %s is %d.%n", name, year, gender, rank);
                    }
                }
                case "3" -> {
                    int rank = askRank(scanner);
                    int year = askYear(scanner);
                    String gender = askGender(scanner);
                    String name = BabyNames.getName(year, rank, gender);
                    if(name.equals("NO NAME FOUND")) {
                        System.out.printf("No name found for rank %d, on year %d according to gender %s.%n", rank, year, gender);
                    } else {
                        System.out.printf("Name of rank %d, on year %d according to gender %s is %s.%n", rank, year, gender, name);
                    }
                }
                case "4" -> {
                    String name = askName(scanner);
                    String gender = askGender(scanner);
                    int year = BabyNames.yearOfHighestRank(name, gender);
                    if(year == -1) {
                        System.out.printf("The name %s according to gender %s was not found.%n", name, gender);
                    } else {
                        System.out.printf("Year of highest rank on name %s, according to gender %s is %d.%n", name, gender, year);
                    }
                }
                case "5" -> {
                    String name = askName(scanner);
                    String gender = askGender(scanner);
                    double averageRank = BabyNames.getAverageRank(name,gender);
                    if(averageRank == -1) {
                        System.out.printf("The name %s according to gender %s was not found.%n", name, gender);
                    } else {
                        System.out.printf("The average rank on name %s, according to gender %s is %.1f.%n", name, gender, averageRank);
                    }
                }
                case "6" -> {
                    String name = askName(scanner);
                    String gender = askGender(scanner);
                    int year = askYear(scanner);
                    int totalBirthsHigherThanName = BabyNames.getTotalBirthsRankedHigher(year,name,gender);
                    System.out.printf("The total number of births higher than name %s for the year %d and according to gender %s is %d births.%n",
                            name, year, gender, totalBirthsHigherThanName);
                }
                case "x" -> notEnd = false;
            }
        }
    }

    private int askRank(Scanner scanner) {
        while (true) {
            System.out.println("--------------------");
            System.out.println("Select the Rank you want to look for");
            System.out.print("> ");
            try {
                int rank = scanner.nextInt();
                if ( rank >= 0 ) {
                    return rank;
                } else {
                    throw new IllegalArgumentException("Rank out of range");
                }
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number");
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a positive number");
                System.out.println();
            }
        }
    }

    public int askYear(Scanner scanner) {
        while (true) {
            System.out.println("--------------------");
            System.out.println("Possible selection of years is from: ");
            System.out.println("1880 to 2014 - enter one");
            System.out.print("> ");
            try {
                int year = scanner.nextInt();
                if ( year >= 1880 && year <= 2014 ) {
                    return year;
                } else {
                    throw new IllegalArgumentException("Year out of range");
                }
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("Invalid input. Please enter a number");
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("Year out of range, please enter a year between 1880 and 2014.");
                System.out.println();
            }
        }
    }

    public String askGender(Scanner scanner) {
        while (true) {
            System.out.println("--------------------");
            System.out.println("Male or Female");
            System.out.print("> ");
            try {
                String gender = scanner.nextLine();
                gender = gender.toUpperCase();
                switch (gender) {
                    case "M", "F" -> {
                        return gender;
                    }
                    case "MALE" -> {
                        gender = "M";
                        return gender;
                    }
                    case "FEMALE" -> {
                        gender = "F";
                        return gender;
                    }
                    default -> throw new IllegalArgumentException("Bad input for gender");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Bad input. Please enter a possible gender");
                System.out.println();
            }
        }
    }

    public String askName(Scanner scanner) {
        while (true) {
            System.out.println("--------------------");
            System.out.println("What is the name you want to look for");
            System.out.print("> ");
            try {
                String name = scanner.nextLine();
                if (name.matches("[a-zA-Z]+")) {
                    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                } else {
                    throw new IllegalArgumentException("Invalid input for name");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Please enter a name using only letters");
                System.out.println();
            }
        }
    }
}
