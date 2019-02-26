using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Windows.Input;

namespace CasedBasedReasoning
{
    public class ViewModel : INotifyPropertyChanged
    {
        public ObservableCollection<string> HolidayTypes { get; }
        public ObservableCollection<string> Regions { get; }
        public ObservableCollection<string> TransportationTypes { get; }
        public ObservableCollection<string> Seasons { get; }
        public ObservableCollection<string> AccommodationTypes { get; }

        public string SelectedHoliday { get; set; }
        public string Price { get; set; }
        public string NumberOfPeople { get; set; }
        public string Duration { get; set; }
        public string SelectedRegion { get; set; }
        public string SelectedTransportation { get; set; }
        public string SelectedAccommodation { get; set; }
        public string SelectedSeason { get; set; }
        public string NumberCasesToView { get; set; }

        private bool _caseInputGridVisible;

        public bool CaseInputGridVisible {
            get
            {
                return _caseInputGridVisible;
            } set
            {
                _caseInputGridVisible = value;
                NotifyPropertyChanged();
            }
        }

        private string _comparisonButtonText;

        public string ComparisonButtonText {
            get
            {
                return _comparisonButtonText;
            }
            set
            {
                _comparisonButtonText = value;
                NotifyPropertyChanged();
            }
        }

        private Case _inputCase;

        public Case InputCase
        {
            get
            {
                return _inputCase;
            }
            set
            {
                _inputCase = value;
                NotifyPropertyChanged();
            }
        }

        public ObservableCollection<Case> AllCases { get; set; } = new ObservableCollection<Case>();

        private readonly Cases _allCases;

        public ViewModel()
        {
            CaseInputGridVisible = true;
            ComparisonButtonText = "Find Relevant Cases";
            HolidayTypes = new ObservableCollection<string>{"Active", "Bathing", "City", "Education",
                "Language", "Recreation", "Skiing", "Wandering"};
            Regions = new ObservableCollection<string>{"AdriaticSea","Allgaeu","Algarve","Alps","Atlantic","Attica",
                "Balaton","Bavaria","BalticSea", "Belgium","BlackForest", "Bornholm",
                "Brittany","Bulgaria", "Cairo","Carinthia","Chalkidiki", "Corfu","Corsica",
                "CostaBlanca","CostaBrava", "CotedAzur","Crete","Czechia","Denmark","Egypt",
                "England","ErzGebirge", "Fano","France","Fuerteventura", "GiantMountains",
                "GranCanaria", "Harz","Holland","Ibiza","Ireland","LakeGarda", "Lolland",
                "Madeira","Malta","Mallorca","Normandy", "NorthSea", "Poland","Riviera",
                "Rhodes", "SalzbergerLand","Scotland","Slowakei","Styria","Sweden", "Teneriffe",
                "Thuringia","Tunisia","TurkishAegeanSea", "TurkishRiviera", "Tyrol", "Wales"};
            TransportationTypes = new ObservableCollection<string> { "Car", "Coach", "Plane", "Train" };
            Seasons = new ObservableCollection<string>{"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
            AccommodationTypes = new ObservableCollection<string>{"HolidayFlat", "OneStar", "TwoStars", "ThreeStars",
                "FourStars", "FiveStars" };

            Price = "0";
            NumberOfPeople = "1";
            Duration = "1";
            NumberCasesToView = "10";
            SelectedHoliday = HolidayTypes.First();
            SelectedRegion = Regions.First();
            SelectedTransportation = TransportationTypes.First();
            SelectedSeason = Seasons.First();
            SelectedAccommodation = AccommodationTypes.First();

            _allCases = new Cases();
        }

        private ICommand _comparisonButtonCommand;

        public ICommand ComparisonButtonCommand
        {
            get
            {
                return _comparisonButtonCommand ?? (_comparisonButtonCommand = new RelayCommand(_ => CanCompare(), _ => ComparisonButtonPressed()));
            }
        }

        private bool CanCompare()
        {
            return true;
        }

        private void ComparisonButtonPressed()
        {
            //Switch to results screen
            var priceValid = int.TryParse(Price, out var price);
            if (!priceValid)
            {
                price = 500;
            }
            var numPeopleValid = int.TryParse(NumberOfPeople, out var numberPeople);
            if (!numPeopleValid)
            {
                numberPeople = 1;
            }
            var durationValid = int.TryParse(Duration, out var duration);
            if (!durationValid)
            {
                duration = 3;
            }
            var numberOfCasesValid = int.TryParse(NumberCasesToView, out var numberOfCases);
            if (!numberOfCasesValid)
            {
                numberOfCases = 10;
            }
            var inputCase = new Case(0, SelectedHoliday, price, numberPeople, SelectedRegion,
                SelectedTransportation, duration, SelectedSeason, SelectedAccommodation, "");
            InputCase = inputCase;
            CaseInputGridVisible = !CaseInputGridVisible;
            ComparisonButtonText = CaseInputGridVisible ? "Find Relevant Cases" : "Input New Case";

            _allCases.CompareAllCases(inputCase);
            //Display relevant number of cases
            var displayCases = _allCases.CaseList.Take(numberOfCases);
            AllCases.Clear();
            foreach(var curCase in displayCases)
            {
                AllCases.Add(curCase);
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
