package com.company;

import com.opencsv.CSVReader;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        Cases allCases = new Cases();
        allCases.ReadCases();

        //allCases.giveStats();
        //Need to input the actual case that we want to compare
        //Also need to figure out how we can make a case?
        Case inputCase = new Case();

        //Need to loops through and check against the case base and keep track of the best etc
        allCases.compareAllCases(inputCase);
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

    private void test1(){
        
    }

}