package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ArO on 3/09/2016.
 */
public class MyGUI {
    private TextArea caseDisplay;

    public MyGUI(Cases caseBase){
        JFrame guiFrame = new JFrame();  //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Case Based Reasoner - Josh Jones");
        int frameWidth = 400, frameHeight = 400, ourCaseHeight = 130;
        guiFrame.setSize(frameWidth,frameHeight);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Options for the Combo Boxes
        String[] holidayTypeOptions = {"Active", "Bathing", "City", "Education",
                "Language", "Recreation", "Skiing", "Wandering"};

        String[] regions = {"Egypt", "Cairo", "Belgium", "Bulgaria", "Bornholm", "Fano", "Lolland", "Allgaeu",
                "Alps", "Bavaria", "ErzGebirge", "Harz", "NorthSea", "BalticSea", "BlackForest",
                "Thuringia", "Atlantic", "CotedAzur", "Corsica", "Normandy", "Brittany",
                "Attica", "Chalkidiki", "Corfu", "Crete", "Rhodes", "England", "Ireland",
                "Scotland", "Wales", "Holland", "AdriaticSea", "LakeGarda", "Riviera", "Tyrol",
                "Malta", "Carinthia", "SalzbergerLand", "Styria", "Algarve", "Madeira", "Sweden",
                "CostaBlanca", "CostaBrava", "Fuerteventura", "GranCanaria", "Ibiza", "Mallorca",
                "Teneriffe", "GiantMountains", "TurkishAegeanSea", "TurkishRiviera", "Tunisia",
                "Balaton", "Denmark", "Poland", "Slowakei", "Czechia", "France"};

        String[] transportation = {"Car", "Coach", "Plane", "Train"};

        String[] seasons = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};

        String[] accommodation = {"HolidayFlat", "OneStar", "TwoStars", "ThreeStars",
                "FourStars", "FiveStars" };


        final JPanel casePanel = new JPanel(new GridLayout(9,2,0,10));
        JLabel holidayLabel = new JLabel("Holiday Types:");
        JComboBox holidayBox = new JComboBox(holidayTypeOptions);
        JLabel regionLabel = new JLabel("Regions:");
        JComboBox regionBox = new JComboBox(regions);
        JLabel transportationLabel = new JLabel("Transporation types:");
        JComboBox transportationBox = new JComboBox(transportation);
        JLabel seasonLabel = new JLabel("Seasons:");
        JComboBox seasonBox = new JComboBox(seasons);
        JLabel accommodationLabel = new JLabel("Accommodation:");
        JComboBox accommodationBox = new JComboBox(accommodation);

        JLabel priceLabel = new JLabel("Price:");
        TextField priceField = new TextField(5);
        JLabel personNumLabel = new JLabel("Number of People:");
        TextField personField = new TextField(2);
        JLabel durationLabel = new JLabel("Duration:");
        TextField durationField = new TextField(3);

        JLabel numCasesLabel = new JLabel("How many cases you want to view:");
        TextField numCasesField = new TextField(4);


        casePanel.add(holidayLabel);
        casePanel.add(holidayBox);
        casePanel.add(priceLabel);
        casePanel.add(priceField);
        casePanel.add(personNumLabel);
        casePanel.add(personField);
        casePanel.add(durationLabel);
        casePanel.add(durationField);
        casePanel.add(regionLabel);
        casePanel.add(regionBox);
        casePanel.add(transportationLabel);
        casePanel.add(transportationBox);
        casePanel.add(seasonLabel);
        casePanel.add(seasonBox);
        casePanel.add(accommodationLabel);
        casePanel.add(accommodationBox);
        casePanel.add(numCasesLabel);
        casePanel.add(numCasesField);


        final JPanel relevantPanel = new JPanel();
        relevantPanel.setLayout(new BoxLayout(relevantPanel, BoxLayout.PAGE_AXIS));
        relevantPanel.setVisible(false);
        JLabel caseLbl = new JLabel("Your Case:");
        //caseLbl.setPreferredSize(new Dimension(frameWidth, 20));
        JTextArea thisCaseArea = new JTextArea();
        thisCaseArea.setPreferredSize(new Dimension(frameWidth-10,ourCaseHeight));
        //JScrollPane thisCasePane = new JScrollPane(thisCaseArea);
        relevantPanel.add(caseLbl);
        //relevantPanel.add(thisCasePane);
        relevantPanel.add(thisCaseArea);

        JLabel listLbl = new JLabel("Relevant Cases:");
        JTextArea relevantCasesArea = new JTextArea();
        JScrollPane relevantCasesPane = new JScrollPane(relevantCasesArea);
        relevantPanel.add(listLbl);
        relevantPanel.add(relevantCasesPane);

        thisCaseArea.setEditable(false);
        relevantCasesArea.setEditable(false);

        JButton caseFindBut = new JButton("Find Relevant Cases");

        //The ActionListener class is used to handle the
        // event that happens when the user clicks the button.
        // As there is not a lot that needs to happen we can
        // define an anonymous inner class to make the code simpler.
        caseFindBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                relevantPanel.setVisible(!relevantPanel.isVisible());
                casePanel.setVisible(!casePanel.isVisible());
                if(relevantPanel.isVisible()){
                    caseFindBut.setText("Enter New Case");

                    //set up the input case here
                    int curPrice = 1;
                    int curNumPerson = 1;
                    int curDuration = 1;
                    int numberOfCases = 10;
                    try{
                        curPrice = Integer.parseInt(priceField.getText().toString());
                    }catch(Exception e){

                    }
                    try{
                        curNumPerson = Integer.parseInt(personField.getText().toString());
                    }catch(Exception e){

                    }
                    try{
                        curDuration = Integer.parseInt(durationField.getText().toString());
                    }catch(Exception e){

                    }
                    try{
                        numberOfCases = Integer.parseInt(numCasesField.getText().toString());
                    }catch(Exception e){

                    }

                    Case inputCase = new Case();
                    inputCase.holidayType = holidayBox.getSelectedItem().toString();
                    inputCase.region = regionBox.getSelectedItem().toString();
                    inputCase.transportation = transportationBox.getSelectedItem().toString();
                    inputCase.season = seasonBox.getSelectedItem().toString();
                    inputCase.accommodation = accommodationBox.getSelectedItem().toString();

                    inputCase.price = curPrice;
                    inputCase.numPerson = curNumPerson;
                    inputCase.duration = curDuration;
                    inputCase.setupIndices();

                    //Do the case comparison
                    caseBase.compareAllCases(inputCase);

                    String otherCases = "";
                    for(int i=0; i < numberOfCases; i++){
                        otherCases += "Case " + (i+1) + ":\n" +
                                caseBase.sortedCases[i].toString() + "\n";
                    }

                    thisCaseArea.setText(inputCase.toString());
                    relevantCasesArea.setText(otherCases);

                }else{

                    caseFindBut.setText("Find Relevant Cases");
                }

            }
        });

        //The JFrame uses the BorderLayout layout manager.
        // Put the two JPanels and JButton in different areas.
        guiFrame.add(casePanel, BorderLayout.NORTH);
        guiFrame.add(relevantPanel, BorderLayout.CENTER);
        guiFrame.add(caseFindBut,BorderLayout.SOUTH);

        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }
}
/*
Holiday Types:
Bathing, Active, Education, City, Recreation, Wandering, Language, Skiing,

regions:
"Egypt", "Cairo", "Belgium", "Bulgaria", "Bornholm", "Fano", "Lolland", "Allgaeu",
 "Alps", "Bavaria", "ErzGebirge", "Harz", "NorthSea", "BalticSea", "BlackForest",
 "Thuringia", "Atlantic", "CotedAzur", "Corsica", "Normandy", "Brittany",
"Attica", "Chalkidiki", "Corfu", "Crete", "Rhodes", "England", "Ireland",
"Scotland", Wales", "Holland", "AdriaticSea", "LakeGarda", "Riviera", "Tyrol",
"Malta", "Carinthia", "SalzbergerLand", "Styria", "Algarve", "Madeira", "Sweden",
"CostaBlanca", "CostaBrava", "Fuerteventura", "GranCanaria", "Ibiza", "Mallorca",
 "Teneriffe", "GiantMountains", "TurkishAegeanSea", "TurkishRiviera", "Tunisia",
  "Balaton", "Denmark", "Poland", "Slowakei", "Czechia", "France"

Transportations:
"Plane", "Car", "Train", "Coach"

Seasons:
"January", "February", "March", "April", "May", "June", "July", "August", "September",
"October", "November", "December"

Accomodation types:
"TwoStars", "ThreeStars", "FourStars", "FiveStars", "HolidayFlat", "OneStar"

 */