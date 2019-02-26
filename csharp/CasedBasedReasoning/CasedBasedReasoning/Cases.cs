using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using CsvHelper;

namespace CasedBasedReasoning
{
    class Cases
    {
        public List<Case> CaseList = new List<Case>();
        public Case[] sortedCases;
        string inputFile = "\\TRAVEL.CSV";

        public Cases()
        {
            totalWeight = holidayTypeWeight + priceWeight + personNumberWeight +
                regionWeight + transportationWeight + durationWeight + seasonWeight + accomodationWeight;
            
            string relPath = Directory.GetCurrentDirectory();
            //Console.WriteLine("this is the currentPath: " + relPath);
            inputFile = relPath + inputFile;
        }


        public void CompareAllCases(Case inputCase)
        {

            foreach(Case curCase in CaseList)
            {
                curCase.Similarity = CompareSimilarity(inputCase, curCase);
            }
            
            sortedCases = CaseList.ToArray();
            Array.Sort(sortedCases);
        }



        public double holidayTypeWeight = 5.0;
        public double priceWeight = 2.0;
        public double personNumberWeight = 3.0;
        public double regionWeight = 1.0;
        public double transportationWeight = 1.0;
        public double durationWeight = 2.0;
        public double seasonWeight = 3.0;
        public double accomodationWeight = 2.0;
        public double totalWeight;

        public double CompareSimilarity(Case thisCase, Case otherCase)
        {
            double similarity = 0.0;

            similarity += holidayTypeWeight * HolidayTypeComparison(thisCase, otherCase);
            similarity += priceWeight * PriceComparison(thisCase, otherCase);
            similarity += personNumberWeight * PersonNumberComparison(thisCase, otherCase);
            similarity += regionWeight * RegionComparison(thisCase, otherCase);
            similarity += transportationWeight * TransportationComparison(thisCase, otherCase);
            similarity += durationWeight * DurationComparison(thisCase, otherCase);
            similarity += seasonWeight * SeasonComparison(thisCase, otherCase);
            similarity += accomodationWeight * AccomodationComparison(thisCase, otherCase);
            double beforeRound = similarity / totalWeight;
            return (double)Math.Round(beforeRound * 10000) / 10000;
        }

        public void CompareSimilarityTest(Case thisCase, Case otherCase)
        {
            double holidaySim = holidayTypeWeight * HolidayTypeComparison(thisCase, otherCase);
            double priceSim = priceWeight * PriceComparison(thisCase, otherCase);
            double personNumSim = personNumberWeight * PersonNumberComparison(thisCase, otherCase);
            double regionSim = regionWeight * RegionComparison(thisCase, otherCase);
            double transportationSim = transportationWeight * TransportationComparison(thisCase, otherCase);
            double durationSim = durationWeight * DurationComparison(thisCase, otherCase);
            double seasonSim = seasonWeight * SeasonComparison(thisCase, otherCase);
            double accommodationSim = accomodationWeight * AccomodationComparison(thisCase, otherCase);
            Console.WriteLine("Case: " + otherCase.JourneyCode);
            Console.WriteLine("Holiday: " + otherCase.HolidayType + " _ " + holidaySim);
            Console.WriteLine("Price: " + otherCase.Price + " _ " + priceSim);
            Console.WriteLine("PeopleNum: " + otherCase.NumPerson + " _ " + personNumSim);
            Console.WriteLine("Region: " + otherCase.Region + " _ " + regionSim);
            Console.WriteLine("Transportation: " + otherCase.Transportation + " _ " + transportationSim);
            Console.WriteLine("Duration: " + otherCase.Duration + " _ " + durationSim);
            Console.WriteLine("Season: " + otherCase.Season + " _ " + seasonSim);
            Console.WriteLine("Accommodation: " + otherCase.Accommodation + " _ " + accommodationSim);
            double total = holidaySim + priceSim + personNumSim + regionSim + transportationSim +
                    durationSim + seasonSim + accommodationSim;
            double totalW = total / totalWeight;
            Console.WriteLine("Total: " + totalW);

            Console.WriteLine("\n\n");
        }
        //Plane, Car, Train, Coach,
        private double[,] TransportSimilarities = new double[,] { { 1.0, 0.4, 0.6, 0.5 }, { 0.4, 1.0, 0.6, 0.5 }, { 0.6, 0.4, 1.0, 0.7 }, { 0.4, 0.6, 0.8, 1.0 } };

        //Done
        private double HolidayTypeComparison(Case thisCase, Case otherCase)
        {
            //Bathing, City, Wandering
            //Active, Recreation, Skiing
            //Education, Language
            //if they are the same then return 1
            if (thisCase.HolidayTypeIndex == otherCase.HolidayTypeIndex)
            {
                return 1.0;
            }
            //If they are in the same group then return 0.75
            if (thisCase.HolidayTypeIndex % 3 == otherCase.HolidayTypeIndex % 3)
            {
                return 0.8;
            }
            //Otherwise not very similar
            return 0.4;
        }
        //Done
        private double PriceComparison(Case thisCase, Case otherCase)
        {
            //Min: 279
            //Max: 7161
            //THis is to say that, if the difference in prices is more than what they want, x
            // x* relevanceScale then it will be deemed unsimilar, otherwise use a cos function
            //to determine similarity
            double relevanceScale = 0.4;
            int difference = otherCase.Price - thisCase.Price;
            if (difference <= 0)
            {
                return 1.0;
            }
            double divisor = thisCase.Price * relevanceScale;
            double pi = Math.PI;
            if (difference <= divisor)
            {
                return (Math.Cos(Math.PI * difference / divisor) / 2) + 0.5;
            }
            return 0;
        }
        //Done
        private double PersonNumberComparison(Case thisCase, Case otherCase)
        {
            //Min: 1
            //Max: 12
            //only give similarity if within 3 people of what they request
            int difference = Math.Abs(otherCase.NumPerson - thisCase.NumPerson);
            int leeWay = 3;
            if (difference <= leeWay)
            {
                return 1 - 0.5 * (1.0 * difference / (leeWay + 1));
            }
            return 0.2;
        }
        //Done
        private double RegionComparison(Case thisCase, Case otherCase)
        {
            //if they are the same then 1.0
            if (thisCase.Region.Equals(otherCase.Region))
            {
                return 1.0;
            }
            //Otherwise still fairly similar
            return 0.8;
        }
        //Done
        private double TransportationComparison(Case thisCase, Case otherCase)
        {
            return TransportSimilarities[thisCase.TransportationIndex, otherCase.TransportationIndex];
        }
        //Done
        private double DurationComparison(Case thisCase, Case otherCase)
        {
            //Min: 3
            //Max: 21
            //if 6 or more days different, will return 0.3, otherwise is linearly
            //scaled from 1 to 0.3
            int difference = Math.Abs(otherCase.Duration - thisCase.Duration);
            int leeWay = 5;
            if (difference <= leeWay)
            {
                return 1 - 0.7 * (1.0 * difference / (leeWay + 1));
            }
            return 0.3;
        }
        //Done
        private double SeasonComparison(Case thisCase, Case otherCase)
        {
            //Hard coded:
            //If they are the same month then they are 1.0 similar
            //if they are adjacent months, 0.8 similar, if they are 1 month apart, 0.6 similar
            //Otherwise they are not similar
            int difference = Math.Abs(thisCase.SeasonIndex - otherCase.SeasonIndex);
            if (difference == 0)
            {
                return 1.0;
            }
            if (difference == 1 || difference == 11)
            {
                return 0.8;
            }
            if (difference == 2 || difference == 10)
            {
                return 0.6;
            }
            return 0.4;
        }
        //Done
        private double AccomodationComparison(Case thisCase, Case otherCase)
        {
            //Assume that higher ranked accomodation is always more preferred.
            //index will be between 0-5
            if (otherCase.AccommodationIndex >= thisCase.AccommodationIndex)
            {
                return 1.0;
            }
            int difference = Math.Abs(thisCase.AccommodationIndex - otherCase.AccommodationIndex);
            return 1 - 0.7 * (difference / 5);
        }

        //These are for reading in the cases from file
        public void ReadCases()
        {
            try
            {
                TextReader textReader = new StreamReader(inputFile);
                var csvReader = new CsvReader(textReader);
                csvReader.Configuration.HasHeaderRecord = false;
                var rowTypeDefinition = new
                {
                    Empty = string.Empty,
                    FieldName = string.Empty,
                    Value = string.Empty
                };
                var rows = csvReader.GetRecords(rowTypeDefinition).ToArray();
                //Console.WriteLine("Finding the file worked");
                int caseNum = 0;
                var rowIndex = 0;
                while (rowIndex <= rows.Length)
                {
                    Case curCase = new Case();
                    //Console.WriteLine("Instantiated case: " + caseNum);
                    rowIndex += 3;
                    var curRow = rows[rowIndex];
                    //printThisLine(curLine);
                    curCase.JourneyCode = Int32.Parse(Cleanstring(curRow.Value));
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.HolidayType = Cleanstring(curRow.Value);
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Price = Int32.Parse(Cleanstring(curRow.Value));
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.NumPerson = Int32.Parse(Cleanstring(curRow.Value));
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Region = Cleanstring(curRow.Value);
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Transportation = Cleanstring(curRow.Value);
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Duration = Int32.Parse(Cleanstring(curRow.Value));
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Season = Cleanstring(curRow.Value);
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Accommodation = Cleanstring(curRow.Value);
                    rowIndex++;
                    //printThisLine(curLine);
                    curRow = rows[rowIndex];
                    curCase.Hotel = Cleanstring(curRow.Value);
                    rowIndex += 4;
                    curCase.SetupIndices();
                    CaseList.Add(curCase);
                    //Console.WriteLine("Finished Case: " + caseNum);
                    caseNum++;

                }


            }
            catch (Exception e)
            {

                Console.WriteLine("Something went wrong reading in the input file");
                Console.WriteLine(e.ToString());
            }


        }

        private void PrintThisLine(string[] thisLine)
        {
            foreach (string current in thisLine)
            {
                Console.WriteLine(current);
            }
        }

        private string Cleanstring(string input)
        {
            string firstStrip = Regex.Replace(input,"[^a-zA-Z0-9]+$", "");
            string secondStrip = Regex.Replace(firstStrip, "^[^a-zA-Z0-9]+", "");
            return secondStrip;
        }

        //This is all just used for seeing what values are in the case base
        public void GiveStats()
        {
            List<string> holidayTypes = new List<string>();
            List<string> regions = new List<string>();
            List<string> transportations = new List<string>();
            List<string> seasons = new List<string>();
            List<string> accTypes = new List<string>();
            int priceMin = 3000, priceMax = 0, personMin = 3, personMax = 0,
                    durationMin = 8, durationMax = 0;
            foreach (Case curCase in CaseList)
            {
                AddType(curCase.HolidayType, holidayTypes);
                AddRegion(curCase.Region, regions);
                AddTransportation(curCase.Transportation, transportations);
                AddMonth(curCase.Season, seasons);
                AddAccom(curCase.Accommodation, accTypes);
                if (curCase.Price < priceMin)
                {
                    priceMin = curCase.Price;
                }
                else if (curCase.Price > priceMax)
                {
                    priceMax = curCase.Price;
                }
                if (curCase.NumPerson < personMin)
                {
                    personMin = curCase.NumPerson;
                }
                else if (curCase.NumPerson > personMax)
                {
                    personMax = curCase.NumPerson;
                }
                if (curCase.Duration < durationMin)
                {
                    durationMin = curCase.Duration;
                }
                else if (curCase.Duration > durationMax)
                {
                    durationMax = curCase.Duration;
                }

            }

            Console.WriteLine("Holiday Types:");
            foreach (string current in holidayTypes)
            {
                Console.Write(current + ", ");
            }
            Console.WriteLine("\n");

            Console.WriteLine("regions:");
            int count = 0;
            foreach (string current in regions)
            {
                Console.Write(current + ", ");
                if (count % 10 == 0)
                {
                    Console.WriteLine();
                }
                count++;
            }
            Console.WriteLine("\n");
            Console.WriteLine("Transportations:");
            foreach (string current in transportations)
            {
                Console.Write(current + ", ");
            }
            Console.WriteLine("\n");
            Console.WriteLine("Seasons:");
            foreach (string current in seasons)
            {
                Console.Write(current + ", ");
            }
            Console.WriteLine("\n");
            Console.WriteLine("Accomodation types:");
            foreach (string current in accTypes)
            {
                Console.Write(current + ", ");
            }
            Console.WriteLine("\n");
            Console.WriteLine("Price:");
            Console.WriteLine("Min: " + priceMin);
            Console.WriteLine("Max: " + priceMax);
            Console.WriteLine();
            Console.WriteLine("Number of People:");
            Console.WriteLine("Min: " + personMin);
            Console.WriteLine("Max: " + personMax);
            Console.WriteLine();
            Console.WriteLine("Duration:");
            Console.WriteLine("Min: " + durationMin);
            Console.WriteLine("Max: " + durationMax);
            Console.WriteLine();
        }

        private void AddAccom(string acc, List<string> list)
        {
            foreach (string curAcc in list)
            {
                if (acc.Equals(curAcc))
                {
                    return;
                }
            }

            list.Add(acc);
        }
        private void AddMonth(string thisMonth, List<string> list)
        {
            foreach (string month in list)
            {
                if (thisMonth.Equals(month))
                {
                    return;
                }
            }

            list.Add(thisMonth);
        }
        private void AddTransportation(string transportation, List<string> list)
        {
            foreach (string trans in list)
            {
                if (transportation.Equals(trans))
                {
                    return;
                }
            }

            list.Add(transportation);
        }
        public void AddRegion(string thisRegion, List<string> list)
        {
            foreach (string region in list)
            {
                if (thisRegion.Equals(region))
                {
                    return;
                }
            }

            list.Add(thisRegion);
        }
        public void AddType(string thisType, List<string> list)
        {

            foreach (string type in list)
            {
                if (thisType.Equals(type))
                {
                    return;
                }
            }

            list.Add(thisType);
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
}
