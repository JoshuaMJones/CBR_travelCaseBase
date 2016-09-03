package com.company;

/**
 * Created by ArO on 27/08/2016.
 */
public class Case implements Comparable<Case>{

    protected int journeyCode;
    protected String holidayType;
    protected int holidayTypeIndex;
    protected int price;
    protected int numPerson;
    protected String region;
    protected String transportation;
    protected int transportationIndex;
    protected int duration;
    protected String season;
    protected int seasonIndex;
    protected String accommodation;
    protected int accommodationIndex;
    protected String hotel;
    protected double similarity;

    public void setupIndices(){
        this.holidayTypeIndex = getHolidayIndex(this.holidayType);
        this.transportationIndex = getTransportIndex(this.transportation);
        this.seasonIndex = getSeasonIndex(this.season);
        this.accommodationIndex = getAccomodationIndex(this.accommodation);
    }


    private int getTransportIndex(String type){
        if(type.equals("Plane")){
            return 0;
        }
        else if(type.equals("Car")){
            return 1;
        }
        else if(type.equals("Train")){
            return 2;
        }
        else{
            return 3;
        }
    }
    private int getSeasonIndex(String season){
        if(season.equals("January")){
            return 1;
        }else if(season.equals("February")){
            return 2;
        }
        else if(season.equals("March")){
            return 3;
        }
        else if(season.equals("April")){
            return 4;
        }
        else if(season.equals("May")){
            return 5;
        }
        else if(season.equals("June")){
            return 6;
        }
        else if(season.equals("July")){
            return 7;
        }
        else if(season.equals("August")){
            return 8;
        }
        else if(season.equals("September")){
            return 9;
        }
        else if(season.equals("October")){
            return 10;
        }
        else if(season.equals("November")){
            return 11;
        }
        else{
            return 12;
        }
    }
    private int getAccomodationIndex(String accomodation){
        if(accomodation.equals("HolidayFlat")){
            return 0;
        }
        else if(accomodation.equals("OneStar")){
            return 1;
        }
        else if(accomodation.equals("TwoStars")){
            return 2;
        }
        else if(accomodation.equals("ThreeStars")){
            return 3;
        }
        else if(accomodation.equals(("FourStars"))){
            return 4;
        }
        else{
            return 5;
        }
    }
    private int getHolidayIndex(String holiday){
        //First group
        if(holiday.equals("Bathing")){
            return 0;
        }else if(holiday.equals("City")){
            return 3;
        }else if(holiday.equals("Wandering")){
            return 6;
        }//Second group
        else if(holiday.equals("Active")){
            return 1;
        }else if(holiday.equals("Recreation")){
            return 4;
        }else if(holiday.equals("Skiing")){
            return 7;
        }//Third group
        else if(holiday.equals("Education")){
            return 2;
        }
        return 5;
    }

    public void printCase(){
        System.out.println("Case: " + journeyCode);
        System.out.println(holidayType);
        System.out.println(price);
        System.out.println(numPerson);
        System.out.println(region);
        System.out.println(transportation);
        System.out.println(duration);
        System.out.println(season);
        System.out.println(accommodation);
        System.out.println(hotel);
        System.out.println("similarity: " + similarity + "\n");

    }

    @Override
    public int compareTo(Case other) {
        if(this.similarity == other.similarity){
            return 0;
        }
        return this.similarity < other.similarity ? 1 : -1;
    }

    @Override
    public String toString(){
        String output = "";
        if(journeyCode != 0){
            output += "Journey Code: " + journeyCode + "\n";
        }
        output += "Holiday Type: " + holidayType + "\n";
        output += "Price: " + price + "\n";
        output += "Number of People: " + numPerson + "\n";
        output += "Region: " + region + "\n";
        output += "Transportation: " + transportation + "\n";
        output += "Duration: " + duration + "\n";
        output += "Season: " + season + "\n";
        output += "Accomodation: " + accommodation + "\n";
        if(hotel != null){
            output +="Hotel: " + hotel + "\n";
        }
        if(similarity != 0){
            output += "Similarity: " + similarity + "\n";
        }

        return output;
    }
}
