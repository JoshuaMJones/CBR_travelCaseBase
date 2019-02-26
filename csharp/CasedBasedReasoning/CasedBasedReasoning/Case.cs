using System;

namespace CasedBasedReasoning
{
    public class Case : IComparable
    {
        private readonly int _journeyCode;
        public string HolidayType;
        public int HolidayTypeIndex;
        public int Price;
        public int NumberOfPeople;
        public string Region;
        public string Transportation;
        public int TransportationIndex;
        public int Duration;
        public string Season;
        public int SeasonIndex;
        public string Accommodation;
        public int AccommodationIndex;
        public string Hotel;
        public double Similarity;

        public Case(int journeyCode, string holidayType, int price, int numberOfPeople, string region, string transportation,
            int duration, string season, string accommodation, string hotel)
        {
            _journeyCode = journeyCode;
            HolidayType = holidayType;
            Price = price;
            NumberOfPeople = numberOfPeople;
            Region = region;
            Transportation = transportation;
            Duration = duration;
            Season = season;
            Accommodation = accommodation;
            Hotel = hotel;
            SetupIndices();
        }

        private void SetupIndices()
        {
            HolidayTypeIndex = GetHolidayIndex(HolidayType);
            TransportationIndex = GetTransportationIndex(Transportation);
            SeasonIndex = GetSeasonIndex(Season);
            AccommodationIndex = GetAccommodationIndex(Accommodation);
        }

        private int GetAccommodationIndex(string accommodation)
        {
            if (accommodation.Equals("HolidayFlat"))
            {
                return 0;
            }

            if (accommodation.Equals("OneStar"))
            {
                return 1;
            }

            if (accommodation.Equals("TwoStars"))
            {
                return 2;
            }

            if (accommodation.Equals("ThreeStars"))
            {
                return 3;
            }

            if (accommodation.Equals("FourStars"))
            {
                return 4;
            }

            return 5;
        }

        private int GetSeasonIndex(string season)
        {
            if (season.Equals("January"))
            {
                return 1;
            }

            if (season.Equals("February"))
            {
                return 2;
            }

            if (season.Equals("March"))
            {
                return 3;
            }

            if (season.Equals("April"))
            {
                return 4;
            }

            if (season.Equals("May"))
            {
                return 5;
            }

            if (season.Equals("June"))
            {
                return 6;
            }

            if (season.Equals("July"))
            {
                return 7;
            }

            if (season.Equals("August"))
            {
                return 8;
            }

            if (season.Equals("September"))
            {
                return 9;
            }

            if (season.Equals("October"))
            {
                return 10;
            }

            if (season.Equals("November"))
            {
                return 11;
            }

            return 12;
        }

        private int GetTransportationIndex(string type)
        {
            if (type.Equals("Plane"))
            {
                return 0;
            }

            if (type.Equals("Car"))
            {
                return 1;
            }

            if (type.Equals("Train"))
            {
                return 2;
            }

            return 3;
        }

        private int GetHolidayIndex(string holiday)
        {
            if (holiday.Equals("Bathing"))
            {
                return 0;
            }

            if (holiday.Equals("City"))
            {
                return 3;
            }

            if (holiday.Equals("Wandering"))
            {
                return 6;
            }//Second group

            if (holiday.Equals("Active"))
            {
                return 1;
            }

            if (holiday.Equals("Recreation"))
            {
                return 4;
            }

            if (holiday.Equals("Skiing"))
            {
                return 7;
            }//Third group

            if (holiday.Equals("Education"))
            {
                return 2;
            }
            return 5;
        }

        public override string ToString()
        {
            var output = "";
            if (_journeyCode != 0)
            {
                output += "Journey Code: " + _journeyCode + "\n";
            }
            output += "Holiday Type: " + HolidayType + "\n";
            output += "Price: " + Price + "\n";
            output += "Number of People: " + NumberOfPeople + "\n";
            output += "Region: " + Region + "\n";
            output += "Transportation: " + Transportation + "\n";
            output += "Duration: " + Duration + "\n";
            output += "Season: " + Season + "\n";
            output += "Accommodation: " + Accommodation + "\n";
            if (Hotel != null)
            {
                output += "Hotel: " + Hotel + "\n";
            }
            if (Math.Abs(Similarity) > 0.0001)
            {
                output += "Similarity: " + Similarity + "\n";
            }

            return output;
        }

        public int CompareTo(object obj)
        {
            if (!(obj is Case other))
                return 0;
            if (Math.Abs(Similarity - other.Similarity) < 0.00001)
                return 0;
            return Similarity < other.Similarity ? 1 : -1;
        }
    }
}
