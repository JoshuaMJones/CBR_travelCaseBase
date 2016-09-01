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

    //Need to implement all of these here methods...
    private double journeyCodeComparison(Case otherCase){
        return 1.0;
    }
    private double holidayTypeComparison(Case otherCase){
        //Bathing, Wandering, City
        //Active, Recreation, Skiing
        //Education, Language
        return 1.0;
    }

    //This is fucking terrible, please fix this up xD
    private double priceComparison(Case otherCase){
        //Min: 279
        //Max: 7161
        if(otherCase.price <= this.price){
            return 1.0;
        }
        int difference = otherCase.price - this.price;
        return 1.0 - difference/8000;
    }
    //This is also fucking terrible please fix this too...
    private double personNumberComparison(Case otherCase){
        //Min: 1
        //Max: 12
        int difference = otherCase.numPerson - this.numPerson;
        return 1.0;
    }
    private double regionComparison(Case otherCase){
        return 1.0;
    }
    private double transportationComparison(Case otherCase){
        return 1.0;
    }
    private double durationComparison(Case otherCase){
        //Min: 3
        //Max: 21
        int difference = otherCase.duration - this.duration;
        return 1.0;
    }
    private double seasonComparison(Case otherCase){
        return 1.0;
    }
    private double accomodationComparison(Case otherCase){
        return 1.0;
    }
    private double hotelComparison(Case otherCase){
        return 0.0;
    }
}
