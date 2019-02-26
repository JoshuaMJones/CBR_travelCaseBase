using System.Windows;

namespace CasedBasedReasoning
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        
        public MainWindow()
        {
            InitializeComponent();
            var viewModel = new ViewModel();
            if (!viewModel.SetupCorrect)
                Application.Current.Shutdown();
            DataContext = viewModel;
        }
    }
}
