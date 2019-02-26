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
            HolidayTypes = new ObservableCollection<string>(TravelCaseBaseValues.HolidayTypes);
            Regions = new ObservableCollection<string>(TravelCaseBaseValues.Regions);
            TransportationTypes = new ObservableCollection<string>(TravelCaseBaseValues.TransportationTypes);
            Seasons = new ObservableCollection<string>(TravelCaseBaseValues.Seasons);
            AccommodationTypes = new ObservableCollection<string>(TravelCaseBaseValues.AccommodationTypes);

            Price = $"{TravelCaseBaseValues.DefaultPrice}";
            NumberOfPeople = $"{TravelCaseBaseValues.DefaultNumberOfPeople}";
            Duration = $"{TravelCaseBaseValues.DefaultDuration}";
            NumberCasesToView = $"{TravelCaseBaseValues.DefaultCasesToView}";
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
                return _comparisonButtonCommand ??
                       (_comparisonButtonCommand = new RelayCommand(_ => CanCompare(),
                           _ => ComparisonButtonPressed()));
            }
        }

        private bool CanCompare()
        {
            return true;
        }

        private void ComparisonButtonPressed()
        {
            InputCase = GetInputCase();
            CaseInputGridVisible = !CaseInputGridVisible;
            //Switch to results screen
            ComparisonButtonText = CaseInputGridVisible ? "Find Relevant Cases" : "Input New Case";
            _allCases.CompareCasesToInput(InputCase);
            UpdateDisplayCases();
        }

        private void UpdateDisplayCases()
        {
            var numberOfCasesValid = int.TryParse(NumberCasesToView, out var numberOfCases);
            if (!numberOfCasesValid)
            {
                numberOfCases = TravelCaseBaseValues.DefaultCasesToView;
            }
            var displayCases = _allCases.SortedCases.Take(numberOfCases);
            AllCases.Clear();
            foreach (var curCase in displayCases)
            {
                AllCases.Add(curCase);
            }
        }

        private Case GetInputCase()
        {
            var priceValid = int.TryParse(Price, out var price);
            if (!priceValid)
            {
                price = TravelCaseBaseValues.DefaultPrice;
            }
            var numPeopleValid = int.TryParse(NumberOfPeople, out var numberPeople);
            if (!numPeopleValid)
            {
                numberPeople = TravelCaseBaseValues.DefaultNumberOfPeople;
            }
            var durationValid = int.TryParse(Duration, out var duration);
            if (!durationValid)
            {
                duration = TravelCaseBaseValues.DefaultDuration;
            }
            return new Case(0, SelectedHoliday, price, numberPeople, SelectedRegion,
                SelectedTransportation, duration, SelectedSeason, SelectedAccommodation, "");
        }

        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
