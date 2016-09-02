package com.company;

import com.opencsv.CSVReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        Cases allCases = new Cases();
        allCases.ReadCases();
        test1(allCases);
        //allCases.giveStats();
        //Need to input the actual case that we want to compare
        //Also need to figure out how we can make a case?

        //Case inputCase = new Case();

        //Need to loops through and check against the case base and keep track of the best etc

        //allCases.compareAllCases(inputCase);

        //
        //Change
        //The
        //Case
        //Values
        //So
        //That
        //They
        //Are
        //Preprocessed
        //Into
        //Numbers
        //

        /*int testNum = 1;
        for(Cases.Case curCase : allCases.cases){
            System.out.println(testNum);
            curCase.printCase();
        }*/

    }

    private static void test1(Cases allCases){
        Case input = new Case();
        input.journeyCode = 1;
        input.holidayType = "Bathing";
        input.price = 2498;
        input.numPerson = 2;
        input.region = "Egypt";
        input.transportation = "Plane";
        input.duration = 14;
        input.season = "April";
        input.accommodation = "TwoStars";
        input.hotel = "Hotel White House, Egypt";
        input.setupIndices();

        allCases.compareAllCases(input);
        /*
        System.out.println("\n" + "Done with all the comparison" + "\n");
        for(int i = 0; i < 10; i++){
            allCases.sortedCases[i].printCase();
        }*/

        System.out.println("All individual similarities");
        for(int i=0; i < 10; i++){
            allCases.compareSimilarityTest(input, allCases.sortedCases[i]);
        }

    }

}