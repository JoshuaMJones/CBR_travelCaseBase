package com.company;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Cases {

    public ArrayList<Case> cases = new ArrayList<Case>();
    public Case[] sortedCases;
    String inputFile = "src\\com\\company\\TRAVEL.CSV";


    //Need to add in something which compares the current case against all other cases
    public void compareAllCases(Case inputCase){

        for(Case curCase : cases){
            curCase.similarity = compareSimilarity(inputCase, curCase);
        }

        sortedCases = new Case[cases.size()];
        sortedCases = cases.toArray(sortedCases);
        Arrays.sort(sortedCases);
    }



    public double holidayTypeWeight = 1.0;
    public double priceWeight = 1.0;
    public double personNumberWeight = 1.0;
    public double regionWeight = 1.0;
    public double transportationWeight = 1.0;
    public double durationWeight = 1.0;
    public double seasonWeight = 1.0;
    public double accomodationWeight = 1.0;
    public double totalWeight = holidayTypeWeight + priceWeight + personNumberWeight +
            regionWeight + transportationWeight + durationWeight + seasonWeight + accomodationWeight;

    public double compareSimilarity(Case thisCase, Case otherCase){
        double similarity = 0.0;

        similarity += holidayTypeWeight*holidayTypeComparison(thisCase, otherCase);
        similarity += priceWeight*priceComparison(thisCase, otherCase);
        similarity += personNumberWeight*personNumberComparison(thisCase, otherCase);
        similarity += regionWeight*regionComparison(thisCase, otherCase);
        similarity += transportationWeight*transportationComparison(thisCase, otherCase);
        similarity += durationWeight*durationComparison(thisCase, otherCase);
        similarity += seasonWeight*seasonComparison(thisCase, otherCase);
        similarity += accomodationWeight*accomodationComparison(thisCase, otherCase);

        return similarity/totalWeight;
    }
    //Plane, Car, Train, Coach,
    private double[][] transportSimilarities = {{1.0, 0.3, 0.5, 0.5},{0.3, 1.0, 0.6, 0.7},
            {0.5, 0.3, 1.0, 0.7},{0.3, 0.5, 0.7, 1.0}};


    private double holidayTypeComparison(Case thisCase, Case otherCase){
        //Bathing, City, Wandering
        //Active, Recreation, Skiing
        //Education, Language
        //if they are the same then return 1
        if(thisCase.holidayTypeIndex == otherCase.holidayTypeIndex){
            return 1.0;
        }
        //If they are in the same group then return 0.75
        if(thisCase.holidayTypeIndex % 3 == otherCase.holidayTypeIndex % 3){
            return 0.8;
        }
        //Otherwise not very similar
        return 0.4;
    }
    //Done
    private double priceComparison(Case thisCase, Case otherCase){
        //Min: 279
        //Max: 7161
        //THis is to say that, if the difference in prices is more than what they want, x
        // x* relevanceScale then it will be deemed unsimilar, otherwise use a cos function
        //to determine similarity
        double relevanceScale = 0.3;
        int difference = otherCase.price - thisCase.price;
        if(difference <= 0){
            return 1.0;
        }
        double divisor = thisCase.price * relevanceScale;
        double pi = Math.PI;
        if(difference <= divisor){
            return (Math.cos(Math.PI * difference / divisor)/2) + 0.5;
        }
        return 0;
    }
    //Done
    private double personNumberComparison(Case thisCase, Case otherCase){
        //Min: 1
        //Max: 12
        //only give similarity if within 3 people of what they request
        int difference = Math.abs(otherCase.numPerson - thisCase.numPerson);
        int leeWay = 3;
        if(difference <= leeWay){
            return 1 - (1.0 * difference / (leeWay + 1));
        }
        return 0.0;
    }

    private double regionComparison(Case thisCase, Case otherCase){
        //if they are the same then 1.0
        if(thisCase.region.equals(otherCase.region)){
            return 1.0;
        }
        //Otherwise still fairly similar
        return 0.8;
    }
    //Done
    private double transportationComparison(Case thisCase, Case otherCase){
        return transportSimilarities[thisCase.transportationIndex][otherCase.transportationIndex];
    }
    //TODO perhaps make this scale with duration length?
    private double durationComparison(Case thisCase, Case otherCase){
        //Min: 3
        //Max: 21
        int difference = Math.abs(otherCase.duration - thisCase.duration);
        int leeWay = 5;
        if(difference <= leeWay){
            return 1 - (1.0 * difference / (leeWay + 1));
        }
        return 0.0;
    }
    //TODO perhaps fiddle with the values returned
    private double seasonComparison(Case thisCase, Case otherCase){
        //Hard coded:
        //If they are the same month then they are 1.0 similar
        //if they are adjacent months, 0.7 similar, if they are 1 month apart, 0.4 similar
        //Otherwise they are not similar
        int difference = Math.abs(thisCase.seasonIndex - otherCase.seasonIndex);
        if(difference == 0){
            return 1.0;
        }
        if(difference == 1 || difference == 11){
            return 0.7;
        }
        if(difference == 2 || difference == 10){
            return 0.4;
        }
        return 0.1;
    }
    private double accomodationComparison(Case thisCase, Case otherCase){
        //Assume that higher ranked accomodation is always more preferred.
        //index will be between 0-5
        if(otherCase.accommodationIndex >= thisCase.accommodationIndex){
            return 1.0;
        }
        int difference = Math.abs(thisCase.accommodationIndex - otherCase.accommodationIndex);
        return 1 - (difference/5);
    }


    public void ReadCases(){
        CSVReader readThisShit;
        String[] curLine;
        try{
            readThisShit = new CSVReader(new FileReader(inputFile));
            //System.out.println("Finding the file worked");
            int caseNum = 0;
            while((curLine = readThisShit.readNext()) != null){
                Case curCase = new Case();
                //System.out.println("Instantiated case: " + caseNum);
                readThisShit.readNext();
                readThisShit.readNext();
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.journeyCode = Integer.parseInt(cleanString(curLine[2]));
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.holidayType = cleanString(curLine[2]);
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.price = Integer.parseInt(cleanString(curLine[2]));
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.numPerson = Integer.parseInt(cleanString(curLine[2]));
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.region = cleanString(curLine[2]);
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.transportation = cleanString(curLine[2]);
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.duration = Integer.parseInt(cleanString(curLine[2]));
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.season = cleanString(curLine[2]);
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.accommodation = cleanString(curLine[2]);
                curLine = readThisShit.readNext();
                //printThisLine(curLine);
                curCase.hotel = cleanString(curLine[2]);
                readThisShit.readNext();
                readThisShit.readNext();
                readThisShit.readNext();
                curCase.setupIndices();
                cases.add(curCase);
                //System.out.println("Finished Case: " + caseNum);
                caseNum++;

            }


        }catch(Exception e){

            System.out.println("Something went wrong reading in the input file");
            System.out.println(e.toString());
        }


    }

    private void printThisLine(String[] thisLine){
        for(String current : thisLine){
            System.out.println(current);
        }
    }

    private String cleanString(String input) {
        String firstStrip = input.replaceAll("[^a-zA-Z0-9]+$", "");
        String secondStrip = firstStrip.replaceAll("^[^a-zA-Z0-9]+", "");
        return secondStrip;
    }

    public void giveStats() {
        ArrayList<String> holidayTypes = new ArrayList<String>();
        ArrayList<String> regions = new ArrayList<String>();
        ArrayList<String> transportations = new ArrayList<String>();
        ArrayList<String> seasons = new ArrayList<String>();
        ArrayList<String> accTypes = new ArrayList<String>();
        int priceMin = 3000, priceMax =0, personMin = 3, personMax = 0,
                durationMin = 8, durationMax = 0;
        for(Case curCase : cases){
            addType(curCase.holidayType, holidayTypes);
            addRegion(curCase.region, regions);
            addTransportation(curCase.transportation, transportations);
            addMonth(curCase.season, seasons);
            addAccom(curCase.accommodation, accTypes);
            if(curCase.price < priceMin){
                priceMin = curCase.price;
            }
            else if(curCase.price > priceMax){
                priceMax = curCase.price;
            }
            if(curCase.numPerson < personMin){
                personMin = curCase.numPerson;
            }
            else if(curCase.numPerson > personMax){
                personMax = curCase.numPerson;
            }
            if(curCase.duration < durationMin){
                durationMin = curCase.duration;
            }
            else if(curCase.duration > durationMax){
                durationMax = curCase.duration;
            }

        }

        System.out.println("Holiday Types:");
        for(String current : holidayTypes){
            System.out.print(current + ", ");
        }
        System.out.println("\n");

        System.out.println("regions:");
        int count = 0;
        for(String current : regions){
            System.out.print(current + ", ");
            if(count % 10 == 0){
                System.out.println();
            }
            count++;
        }
        System.out.println("\n");
        System.out.println("Transportations:");
        for(String current : transportations){
            System.out.print(current + ", ");
        }
        System.out.println("\n");
        System.out.println("Seasons:");
        for(String current : seasons){
            System.out.print(current + ", ");
        }
        System.out.println("\n");
        System.out.println("Accomodation types:");
        for(String current : accTypes){
            System.out.print(current + ", ");
        }
        System.out.println("\n");
        System.out.println("Price:");
        System.out.println("Min: " + priceMin);
        System.out.println("Max: " + priceMax);
        System.out.println();
        System.out.println("Number of People:");
        System.out.println("Min: " + personMin);
        System.out.println("Max: " + personMax);
        System.out.println();
        System.out.println("Duration:");
        System.out.println("Min: " + durationMin);
        System.out.println("Max: " + durationMax);
        System.out.println();
    }

    private void addAccom(String acc, ArrayList<String> list) {
        for(String curAcc : list){
            if(acc.equals(curAcc)){
                return;
            }
        }

        list.add(acc);
    }
    private void addMonth(String thisMonth, ArrayList<String> list) {
        for(String month : list){
            if(thisMonth.equals(month)){
                return;
            }
        }

        list.add(thisMonth);
    }
    private void addTransportation(String transportation, ArrayList<String> list) {
        for(String trans : list){
            if(transportation.equals(trans)){
                return;
            }
        }

        list.add(transportation);
    }
    public void addRegion(String thisRegion, ArrayList<String> list){
        for(String region : list){
            if(thisRegion.equals(region)){
                return;
            }
        }

        list.add(thisRegion);
    }
    public void addType(String thisType, ArrayList<String> list){

        for(String type : list){
            if(thisType.equals(type)){
                return;
            }
        }

        list.add(thisType);
    }
}
/*
Holiday Types:
Bathing, Active, Education, City, Recreation, Wandering, Language, Skiing,

regions:
Egypt,
Cairo, Belgium, Bulgaria, Bornholm, Fano, Lolland, Allgaeu, Alps, Bavaria, ErzGebirge,
Harz, NorthSea, BalticSea, BlackForest, Thuringia, Atlantic, CotedAzur, Corsica, Normandy, Brittany,
Attica, Chalkidiki, Corfu, Crete, Rhodes, England, Ireland, Scotland, Wales, Holland,
AdriaticSea, LakeGarda, Riviera, Tyrol, Malta, Carinthia, SalzbergerLand, Styria, Algarve, Madeira,
Sweden, CostaBlanca, CostaBrava, Fuerteventura, GranCanaria, Ibiza, Mallorca, Teneriffe, GiantMountains, TurkishAegeanSea,
TurkishRiviera, Tunisia, Balaton, Denmark, Poland, Slowakei, Czechia, France,

Transportations:
Plane, Car, Train, Coach,

Seasons:
April, May, June, July, September, October, August, November, December, February, March, January,

Accomodation types:
TwoStars, ThreeStars, FourStars, FiveStars, HolidayFlat, OneStar,

 */