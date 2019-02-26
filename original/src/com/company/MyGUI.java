package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGUI {
    private TextArea caseDisplay;

    public MyGUI(Cases caseBase){
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Case Based Reasoner - Josh Jones");
        int frameWidth = 400, frameHeight = 450, ourCaseHeight = 130;
        guiFrame.setSize(frameWidth,frameHeight);


        guiFrame.setLocationRelativeTo(null);


        String[] holidayTypeOptions = {"Active", "Bathing", "City", "Education",
                "Language", "Recreation", "Skiing", "Wandering"};

        String[] regions = {"AdriaticSea","Allgaeu","Algarve","Alps","Atlantic","Attica",
                "Balaton","Bavaria","BalticSea", "Belgium","BlackForest", "Bornholm",
                "Brittany","Bulgaria", "Cairo","Carinthia","Chalkidiki", "Corfu","Corsica",
                "CostaBlanca","CostaBrava", "CotedAzur","Crete","Czechia","Denmark","Egypt",
                "England","ErzGebirge", "Fano","France","Fuerteventura", "GiantMountains",
                "GranCanaria", "Harz","Holland","Ibiza","Ireland","LakeGarda", "Lolland",
                "Madeira","Malta","Mallorca","Normandy", "NorthSea", "Poland","Riviera",
                "Rhodes", "SalzbergerLand","Scotland","Slowakei","Styria","Sweden", "Teneriffe",
                "Thuringia","Tunisia","TurkishAegeanSea", "TurkishRiviera", "Tyrol", "Wales"};

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


        caseFindBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                relevantPanel.setVisible(!relevantPanel.isVisible());
                casePanel.setVisible(!casePanel.isVisible());
                if(relevantPanel.isVisible()){
                    caseFindBut.setText("Enter New Case");

                    //set up the input case here
                    int curPrice, curNumPerson, curDuration, numberOfCases;
                    try{
                        curPrice = Integer.parseInt(priceField.getText().toString());
                        if(curPrice < 0){
                            throw new Exception("bad price number");
                        }
                    }catch(Exception e){
                        curPrice = 500;
                    }
                    try{
                        curNumPerson = Integer.parseInt(personField.getText().toString());
                        if(curNumPerson < 0){
                            throw new Exception("bad person number");
                        }
                    }catch(Exception e){
                        curNumPerson = 1;
                    }
                    try{
                        curDuration = Integer.parseInt(durationField.getText().toString());
                        if(curDuration < 0){
                            throw new Exception("bad duration number");
                        }
                    }catch(Exception e){
                        curDuration = 3;
                    }
                    try{
                        numberOfCases = Integer.parseInt(numCasesField.getText().toString());
                        if(numberOfCases < 0 || numberOfCases > 1024){
                            throw new Exception("bad case number");
                        }
                    }catch(Exception e){
                        numberOfCases = 10;
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
                    relevantCasesArea.setCaretPosition(0);

                }else{

                    caseFindBut.setText("Find Relevant Cases");
                }

            }
        });


        guiFrame.add(casePanel, BorderLayout.NORTH);
        guiFrame.add(relevantPanel, BorderLayout.CENTER);
        guiFrame.add(caseFindBut,BorderLayout.SOUTH);


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