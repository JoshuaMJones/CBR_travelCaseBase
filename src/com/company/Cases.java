package com.company;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;

public class Cases {

    public ArrayList<Case> cases = new ArrayList<Case>();
    String inputFile = "src\\com\\company\\TRAVEL.CSV";


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