using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CasedBasedReasoning
{
    public class CaseComparer
    {
        private readonly Case _inputCase;

        private const double _holidayTypeWeight = 5.0;
        private const double _priceWeight = 2.0;
        private const double _personNumberWeight = 3.0;
        private const double _regionWeight = 1.0;
        private const double _transportationWeight = 1.0;
        private const double _durationWeight = 2.0;
        private const double _seasonWeight = 3.0;
        private const double _accommodationWeight = 2.0;
        private readonly double _totalWeight;

        public CaseComparer(Case inputCase)
        {
            _inputCase = inputCase;
            _totalWeight = _holidayTypeWeight + _priceWeight + _personNumberWeight
                           + _regionWeight + _transportationWeight + _durationWeight + _seasonWeight + _accommodationWeight;
        }

        public void SetSimilarity(Case toCompare)
        {
            var similarity = 0.0;

            similarity += _holidayTypeWeight * HolidayTypeComparison(_inputCase, toCompare);
            similarity += _priceWeight * PriceComparison(_inputCase, toCompare);
            similarity += _personNumberWeight * PersonNumberComparison(_inputCase, toCompare);
            similarity += _regionWeight * RegionComparison(_inputCase, toCompare);
            similarity += _transportationWeight * TransportationComparison(_inputCase, toCompare);
            similarity += _durationWeight * DurationComparison(_inputCase, toCompare);
            similarity += _seasonWeight * SeasonComparison(_inputCase, toCompare);
            similarity += _accommodationWeight * AccommodationComparison(_inputCase, toCompare);
            var beforeRound = similarity / _totalWeight;
            toCompare.Similarity = Math.Round(beforeRound * 10000) / 10000;
        }

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
            //This is to say that, if the difference in prices is more than what they want, x
            // x* relevanceScale then it will be deemed not similar, otherwise use a cos function
            //to determine similarity
            const double relevanceScale = 0.4;
            var difference = otherCase.Price - thisCase.Price;
            if (difference <= 0)
            {
                return 1.0;
            }
            var divisor = thisCase.Price * relevanceScale;
            if (difference <= divisor)
            {
                return (Math.Cos(Math.PI * difference / divisor) / 2) + 0.5;
            }
            return 0;
        }
        
        private double PersonNumberComparison(Case thisCase, Case otherCase)
        {
            //Min: 1
            //Max: 12
            //only give similarity if within 3 people of what they request
            var difference = Math.Abs(otherCase.NumberOfPeople - thisCase.NumberOfPeople);
            const int leeWay = 3;
            if (difference <= leeWay)
            {
                return 1 - (0.5 * (1.0 * difference / (leeWay + 1)));
            }
            return 0.2;
        }
        
        private double RegionComparison(Case thisCase, Case otherCase)
        {
            //If they are the same then 1.0
            if (thisCase.Region.Equals(otherCase.Region))
            {
                return 1.0;
            }
            //Otherwise still fairly similar
            return 0.8;
        }

        //Plane, Car, Train, Coach
        private readonly double[,] _transportSimilarities = { { 1.0, 0.4, 0.6, 0.5 }, { 0.4, 1.0, 0.6, 0.5 }, { 0.6, 0.4, 1.0, 0.7 }, { 0.4, 0.6, 0.8, 1.0 } };
        private double TransportationComparison(Case thisCase, Case otherCase)
        {
            return _transportSimilarities[thisCase.TransportationIndex, otherCase.TransportationIndex];
        }
        
        private double DurationComparison(Case thisCase, Case otherCase)
        {
            //Min: 3
            //Max: 21
            //if 6 or more days different, will return 0.3, otherwise is linearly
            //scaled from 1 to 0.3
            var difference = Math.Abs(otherCase.Duration - thisCase.Duration);
            const int leeWay = 5;
            if (difference <= leeWay)
            {
                return 1 - (0.7 * (1.0 * difference / (leeWay + 1)));
            }
            return 0.3;
        }
        
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
        
        private double AccommodationComparison(Case thisCase, Case otherCase)
        {
            //Assume that higher ranked accommodation is always more preferred.
            //index will be between 0-5
            if (otherCase.AccommodationIndex >= thisCase.AccommodationIndex)
            {
                return 1.0;
            }
            var difference = Math.Abs(thisCase.AccommodationIndex - otherCase.AccommodationIndex);
            return 1 - (0.7 * (difference / 5.0));
        }
    }
}
