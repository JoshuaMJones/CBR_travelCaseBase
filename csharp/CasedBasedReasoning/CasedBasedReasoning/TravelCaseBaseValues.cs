namespace CasedBasedReasoning
{
    public static class TravelCaseBaseValues
    {
        public static string[] HolidayTypes = {"Active", "Bathing", "City", "Education",
            "Language", "Recreation", "Skiing", "Wandering"};
        public static string[] Regions = {"AdriaticSea","Allgaeu","Algarve","Alps","Atlantic","Attica",
            "Balaton","Bavaria","BalticSea", "Belgium","BlackForest", "Bornholm",
            "Brittany","Bulgaria", "Cairo","Carinthia","Chalkidiki", "Corfu","Corsica",
            "CostaBlanca","CostaBrava", "CotedAzur","Crete","Czechia","Denmark","Egypt",
            "England","ErzGebirge", "Fano","France","Fuerteventura", "GiantMountains",
            "GranCanaria", "Harz","Holland","Ibiza","Ireland","LakeGarda", "Lolland",
            "Madeira","Malta","Mallorca","Normandy", "NorthSea", "Poland","Riviera",
            "Rhodes", "SalzbergerLand","Scotland","Slowakei","Styria","Sweden", "Teneriffe",
            "Thuringia","Tunisia","TurkishAegeanSea", "TurkishRiviera", "Tyrol", "Wales"};
        public static string[] TransportationTypes = { "Car", "Coach", "Plane", "Train" };
        public static string[] Seasons = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
        public static string[] AccommodationTypes = {"HolidayFlat", "OneStar", "TwoStars", "ThreeStars",
            "FourStars", "FiveStars" };

        public static int DefaultPrice = 500;
        public static int DefaultDuration = 3;
        public static int DefaultNumberOfPeople = 1;
        public static int DefaultCasesToView = 10;
    }
}
