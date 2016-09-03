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

    public MyGUI(){
        JFrame guiFrame = new JFrame();  //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI"); guiFrame.setSize(300,250);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Options for the JComboBox
        String[] holidayTypeOptions = {"Active", "Bathing", "City", "Education",
                "Language", "Recreation", "Skiing", "Wandering"};

        //Options for the JList
        String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage" , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom" , "Pepper", "Radish", "Shallot", "Spinach", "Swede" , "Turnip"};

        //The first JPanel contains a JLabel and JCombobox
        final JPanel comboPanel = new JPanel();
        JLabel comboLbl = new JLabel("Holiday Types:");
        JComboBox fruits = new JComboBox(holidayTypeOptions);
        comboPanel.add(comboLbl);
        comboPanel.add(fruits);

        //Create the second JPanel. Add a JLabel and JList and
        // make use the JPanel is not visible.
        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel listLbl = new JLabel("Vegetables:");
        JList vegs = new JList(vegOptions);
        vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listPanel.add(listLbl); listPanel.add(vegs);

        JButton vegFruitBut = new JButton( "Find Relevant Cases");

        //The ActionListener class is used to handle the
        // event that happens when the user clicks the button.
        // As there is not a lot that needs to happen we can
        // define an anonymous inner class to make the code simpler.
        vegFruitBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //When the fruit of veg button is pressed
                // the setVisible value of the listPanel and
                // comboPanel is switched from true to
                // value or vice versa.
                listPanel.setVisible(!listPanel.isVisible());
                comboPanel.setVisible(!comboPanel.isVisible());
            }
        });

        //The JFrame uses the BorderLayout layout manager.
        // Put the two JPanels and JButton in different areas.
        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(vegFruitBut,BorderLayout.SOUTH);

        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }
}
/*
Holiday Types:
Bathing, Active, Education, City, Recreation, Wandering, Language, Skiing,

regions:
Egypt,
Cairo, Belgium, Bulgaria, Bornholm, Fano, Lolland, Allgaeu, Alps, Bavaria, ErzGebirge,
Harz, NorthSea, BalticSea, BlackForest, Thuringia, Atlantic, CotedAzur, Corsica, Normandy, Brittany,
Attica, Chalkidiki, Corfu, Crete, Rhodes, England, Ireland, Scotland, Wales, Holland,
AdriaticSea, LakeGarda, Riviera, Tyrol, Malta, Carinthia, SalzbergerLand, Styria, Algarve, Madeira,
Sweden, CostaBlanca, CostaBrava, Fuerteventura, GranCanaria, Ibiza, Mallorca, Teneriffe, GiantMountains, TurkishAegeanSea,
TurkishRiviera, Tunisia, Balaton, Denmark, Poland, Slowakei, Czechia, France,

Transportations:
Plane, Car, Train, Coach,

Seasons:
April, May, June, July, September, October, August, November, December, February, March, January,

Accomodation types:
TwoStars, ThreeStars, FourStars, FiveStars, HolidayFlat, OneStar,

 */