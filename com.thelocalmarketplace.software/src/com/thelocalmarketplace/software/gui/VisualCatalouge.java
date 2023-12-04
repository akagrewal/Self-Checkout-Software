package com.thelocalmarketplace.software.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualCatalouge {

    GUILogic guiLogicInstance;

    StationGUI stationGUI;


    VisualCatalouge(StationGUI rungui){
        this.stationGUI = rungui;
    }


    public JScrollPane createVisualCatalouge() {
        JPanel VisualCataloguePanel = new JPanel(new FlowLayout());
        VisualCataloguePanel.setBorder(BorderFactory.createTitledBorder("VISUAL CATALOGUE "));
        JScrollPane VCpanel = new JScrollPane(VisualCataloguePanel);
        VCpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        VCpanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        JButton apple = createButtonWithImageAndPLU("com.thelocalmarketplace.software/src/com/thelocalmarketplace/software/pictures/apple.jpg", "1002", 300, 300);
        JButton banana = createButtonWithImageAndPLU("com.thelocalmarketplace.software/src/com/thelocalmarketplace/software/pictures/banana.jpg", "1001", 300, 300);

        VisualCataloguePanel.add(apple);
        VisualCataloguePanel.add(banana);

        return VCpanel;
    }

    private JButton createButtonWithImageAndPLU(String imagePath, String PLU, int width, int height) {

        ImageIcon originalIcon = new ImageIcon(imagePath);

        Image originalImage = originalIcon.getImage();

        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton button = new JButton(resizedIcon);

        button.setActionCommand(PLU);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String PLU = e.getActionCommand();
                stationGUI.getLogicInstance().addProductPLU(PLU);
                stationGUI.getLogicInstance().switchPanels("AddItemsPanel");


            }
        });
        return button;
    }

}
