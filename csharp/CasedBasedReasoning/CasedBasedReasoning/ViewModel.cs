using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;
using System.Windows.Input;

namespace CasedBasedReasoning
{
    public class ViewModel : INotifyPropertyChanged
    {
        public ObservableCollection<string> HolidayTypes { get; private set; }
        public ObservableCollection<string> Regions { get; private set; }
        public ObservableCollection<string> TransportationTypes { get; private set; }
        public ObservableCollection<string> Seasons { get; private set; }
        public ObservableCollection<string> AccommodationTypes { get; private set; }

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

        private Cases _allCases;

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
            _allCases.ReadCases();
        }

        private ICommand _comparisonButtonCommand;
        
        public ICommand ComparisonButtonCommand
        {
            get
            {
                if(_comparisonButtonCommand == null)
                {
                    _comparisonButtonCommand = new RelayCommand(p => CanCompare(), p => ComparisonButtonPressed());
                }
                return _comparisonButtonCommand;
            }
        }

        private bool CanCompare()
        {
            return true;
        }
        
        private void ComparisonButtonPressed()
        {
            //Switch to results screen
            var priceValid = Int32.TryParse(Price, out int price);
            if (!priceValid)
            {
                price = 500;
            }
            var numPeopleValid = Int32.TryParse(NumberOfPeople, out int numberPeople);
            if (!numPeopleValid)
            {
                numberPeople = 1;
            }
            var durationValid = Int32.TryParse(Duration, out int duration);
            if (!durationValid)
            {
                duration = 3;
            }
            var numberOfCasesValid = Int32.TryParse(NumberCasesToView, out int numberOfCases);
            if (!numberOfCasesValid)
            {
                numberOfCases = 10;
            }
            var inputCase = new Case() {
                HolidayType = SelectedHoliday,
                Region = SelectedRegion,
                Transportation = SelectedTransportation,
                Season = SelectedSeason,
                Accommodation = SelectedAccommodation,
                Price = price,
                NumPerson = numberPeople,
                Duration = duration
            };
            inputCase.SetupIndices();
            InputCase = inputCase;
            CaseInputGridVisible = !CaseInputGridVisible;
            if (CaseInputGridVisible)
                ComparisonButtonText = "Find Relevant Cases";
            else
                ComparisonButtonText = "Input New Case";

            _allCases.CompareAllCases(inputCase);
            //Display relevant number of cases
            var displayCases = _allCases.CaseList.Take(numberOfCases);
            AllCases.Clear();
            foreach(Case curCase in displayCases)
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
