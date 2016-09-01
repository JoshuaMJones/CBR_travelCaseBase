package com.company;

/**
 * Created by ArO on 27/08/2016.
 */
public class Case {

    protected int journeyCode;
    protected String holidayType;
    protected int price;
    protected int numPerson;
    protected String region;
    protected String transportation;
    protected int duration;
    protected String season;
    protected String accommodation;
    protected String hotel;



    public void printCase(){
        System.out.println(journeyCode);
        System.out.println(holidayType);
        System.out.println(price);
        System.out.println(numPerson);
        System.out.println(region);
        System.out.println(transportation);
        System.out.println(duration);
        System.out.println(season);
        System.out.println(accommodation);
        System.out.println(hotel);

    }


    public double journeyCodeWeight = 0.0;
    public double holidayTypeWeight = 1.0;
    public double priceWeight = 1.0;
    public double personNumberWeight = 1.0;
    public double regionWeight = 1.0;
    public double transportationWeight = 1.0;
    public double durationWeight = 1.0;
    public double seasonWeight = 1.0;
    public double accomodationWeight = 1.0;
    public double hotelWeight = 0.0;
    public double totalWeight = holidayTypeWeight + priceWeight + personNumberWeight +
            regionWeight + transportationWeight + durationWeight + seasonWeight + accomodationWeight;


    public double compareSimilarity(Case otherCase){
        double similarity = 0.0;

        similarity += journeyCodeWeight*journeyCodeComparison(otherCase);
        similarity += holidayTypeWeight*holidayTypeComparison(otherCase);
        similarity += priceWeight*priceComparison(otherCase);
        similarity += personNumberWeight*personNumberComparison(otherCase);
        similarity += regionWeight*regionComparison(otherCase);
        similarity += transportationWeight*transportationComparison(otherCase);
        similarity += durationWeight*durationComparison(otherCase);
        similarity += seasonWeight*seasonComparison(otherCase);
        similarity += accomodationWeight*accomodationComparison(otherCase);
        similarity += hotelWeight*hotelComparison(otherCase);

        return similarity/totalWeight;
    }
    //Plane, Car, Train, Coach,
    private double[][] transportSimilarities = {{1.0, 0.3, 0.5, 0.5},{0.3, 1.0, 0.6, 0.7},
            {0.5, 0.3, 1.0, 0.7},{0.3, 0.5, 0.7, 1.0}};


    private int getTransportIndex(String type){
        if(type.equals("Plane")){
            return 1;
        }
        else if(type.equals("Car")){
            return 2;
        }
        else if(type.equals("Train")){
            return 3;
        }
        else{
            return 4;
        }
    }
    private int getMonthIndex(String season){
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

    //Need to implement all of these here methods...
    //Irrelevant
    private double journeyCodeComparison(Case otherCase){
        return 0.0;
    }
    //TODO
    private double holidayTypeComparison(Case otherCase){
        //Bathing, Wandering, City
        //Active, Recreation, Skiing
        //Education, Language
        return 1.0;
    }
    //Done
    private double priceComparison(Case otherCase){
        //Min: 279
        //Max: 7161
        //THis is to say that, if the difference in prices is more than what they want, x
        // x* relevanceScale then it will be deemed unsimilar, otherwise use a cos function
        //to determine similarity
        double relevanceScale = 0.3;
        int difference = otherCase.price - this.price;
        if(difference <= 0){
            return 1.0;
        }
        double divisor = this.price * relevanceScale;
        double pi = Math.PI;
        if(difference <= divisor){
            return (Math.cos(Math.PI * difference / divisor)/2) + 0.5;
        }
        return 0;
    }
    //Done
    private double personNumberComparison(Case otherCase){
        //Min: 1
        //Max: 12
        //only give similarity if within 3 people of what they request
        int difference = Math.abs(otherCase.numPerson - this.numPerson);
        int leeWay = 3;
        if(difference <= leeWay){
            return 1 - (1.0 * difference / (leeWay + 1));
        }
        return 0.0;
    }
    //TODO
    private double regionComparison(Case otherCase){
        return 1.0;
    }
    //Done
    private double transportationComparison(Case otherCase){
        int thisIndex = getTransportIndex(this.transportation);
        int otherIndex = getTransportIndex(otherCase.transportation);
        return transportSimilarities[thisIndex][otherIndex];
    }
    //TODO perhaps make this scale with duration length?
    private double durationComparison(Case otherCase){
        //Min: 3
        //Max: 21
        int difference = Math.abs(otherCase.duration - this.duration);
        int leeWay = 5;
        if(difference <= leeWay){
            return 1 - (1.0 * difference / (leeWay + 1));
        }
        return 0.0;
    }
    //TODO perhaps fiddle with the values returned, or implement a tree?
    private double seasonComparison(Case otherCase){
        //Hard coded:
        //If they are the same month then they are 1.0 similar
        //if they are adjacent months, 0.7 similar, if they are 1 month apart, 0.4 similar
        //Otherwise they are not similar
        int thisIndex = getMonthIndex(this.season);
        int otherIndex = getMonthIndex(otherCase.season);
        int difference = Math.abs(thisIndex - otherIndex);
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
    //TODO
    private double accomodationComparison(Case otherCase){
        int thisIndex = getAccomodationIndex(this.accommodation);
        int otherIndex = getAccomodationIndex(otherCase.accommodation);
        //Insert some weird function here

        return 1.0;
    }
    //Irrelevant
    private double hotelComparison(Case otherCase){
        return 0.0;
    }
}
