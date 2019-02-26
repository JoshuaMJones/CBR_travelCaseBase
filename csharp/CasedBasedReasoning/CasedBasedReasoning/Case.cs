using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CasedBasedReasoning
{
    public class Case : IComparable
    {
        public int JourneyCode;
        public string HolidayType;
        public int HolidayTypeIndex;
        public int Price;
        public int NumPerson;
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

        public void SetupIndices()
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
            else if (accommodation.Equals("OneStar"))
            {
                return 1;
            }
            else if (accommodation.Equals("TwoStars"))
            {
                return 2;
            }
            else if (accommodation.Equals("ThreeStars"))
            {
                return 3;
            }
            else if (accommodation.Equals(("FourStars")))
            {
                return 4;
            }
            else
            {
                return 5;
            }
        }

        private int GetSeasonIndex(string season)
        {
            if (season.Equals("January"))
            {
                return 1;
            }
            else if (season.Equals("February"))
            {
                return 2;
            }
            else if (season.Equals("March"))
            {
                return 3;
            }
            else if (season.Equals("April"))
            {
                return 4;
            }
            else if (season.Equals("May"))
            {
                return 5;
            }
            else if (season.Equals("June"))
            {
                return 6;
            }
            else if (season.Equals("July"))
            {
                return 7;
            }
            else if (season.Equals("August"))
            {
                return 8;
            }
            else if (season.Equals("September"))
            {
                return 9;
            }
            else if (season.Equals("October"))
            {
                return 10;
            }
            else if (season.Equals("November"))
            {
                return 11;
            }
            else
            {
                return 12;
            }
        }

        private int GetTransportationIndex(string type)
        {
            if (type.Equals("Plane"))
            {
                return 0;
            }
            else if (type.Equals("Car"))
            {
                return 1;
            }
            else if (type.Equals("Train"))
            {
                return 2;
            }
            else
            {
                return 3;
            }
        }

        private int GetHolidayIndex(string holiday)
        {
            if (holiday.Equals("Bathing"))
            {
                return 0;
            }
            else if (holiday.Equals("City"))
            {
                return 3;
            }
            else if (holiday.Equals("Wandering"))
            {
                return 6;
            }//Second group
            else if (holiday.Equals("Active"))
            {
                return 1;
            }
            else if (holiday.Equals("Recreation"))
            {
                return 4;
            }
            else if (holiday.Equals("Skiing"))
            {
                return 7;
            }//Third group
            else if (holiday.Equals("Education"))
            {
                return 2;
            }
            return 5;
        }

        public void printCase()
        {
            Console.WriteLine("Case: " + JourneyCode);
            Console.WriteLine(HolidayType);
            Console.WriteLine(Price);
            Console.WriteLine(NumPerson);
            Console.WriteLine(Region);
            Console.WriteLine(Transportation);
            Console.WriteLine(Duration);
            Console.WriteLine(Season);
            Console.WriteLine(Accommodation);
            Console.WriteLine(Hotel);
            Console.WriteLine("similarity: " + Similarity + "\n");

        }

        public int CompareTo(Case other)
        {
            if (Similarity == other.Similarity)
            {
                return 0;
            }
            return Similarity < other.Similarity ? 1 : -1;
        }

        public override string ToString()
        {
            string output = "";
            if (JourneyCode != 0)
            {
                output += "Journey Code: " + JourneyCode + "\n";
            }
            output += "Holiday Type: " + HolidayType + "\n";
            output += "Price: " + Price + "\n";
            output += "Number of People: " + NumPerson + "\n";
            output += "Region: " + Region + "\n";
            output += "Transportation: " + Transportation + "\n";
            output += "Duration: " + Duration + "\n";
            output += "Season: " + Season + "\n";
            output += "Accomodation: " + Accommodation + "\n";
            if (Hotel != null)
            {
                output += "Hotel: " + Hotel + "\n";
            }
            if (Similarity != 0)
            {
                output += "Similarity: " + Similarity + "\n";
            }

            return output;
        }

        public int CompareTo(object obj)
        {
            var other = obj as Case;
            if (other == null)
                return 0;
            if (Similarity == other.Similarity)
            {
                return 0;
            }
            return Similarity < other.Similarity ? 1 : -1;
        }
    }
}
