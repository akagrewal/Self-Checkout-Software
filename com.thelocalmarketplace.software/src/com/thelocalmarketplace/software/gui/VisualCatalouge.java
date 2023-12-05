package com.thelocalmarketplace.software.gui;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualCatalouge {


    StationGUI stationGUI;


    VisualCatalouge(StationGUI rungui){
        this.stationGUI = rungui;
    }


    public JScrollPane createVisualCatalouge() {
        JPanel VisualCataloguePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        VisualCataloguePanel.setBorder(BorderFactory.createTitledBorder("VISUAL CATALOGUE "));
        JScrollPane VCpanel = new JScrollPane(VisualCataloguePanel);
        VCpanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        VCpanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton apple = createButtonWithImageAndPLU("com.thelocalmarketplace.software/src/com/thelocalmarketplace/software/pictures/apple.jpg", "1002", 300, 300);
        JButton banana = createButtonWithImageAndPLU("com.thelocalmarketplace.software/src/com/thelocalmarketplace/software/pictures/banana.jpg", "1001", 300, 300);
        apple.setText("Apple");
        banana.setText("Banana");

        VisualCataloguePanel.add(apple);
        VisualCataloguePanel.add(banana);

        // Create a separate panel for the back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            stationGUI.getLogicInstance().switchPanels("AddItemsPanel");
        });
        backButtonPanel.add(backButton);

        // Add the back button panel to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(VCpanel, BorderLayout.CENTER);
        mainPanel.add(backButtonPanel, BorderLayout.SOUTH);

        return new JScrollPane(mainPanel);
    }

    private JButton createButtonWithImageAndPLU(String imagePath, String PLU, int width, int height) {

        ImageIcon originalIcon = new ImageIcon(imagePath);

        Image originalImage = originalIcon.getImage();

        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton button = new JButton(resizedIcon);

        button.setActionCommand(PLU);

        PriceLookUpCode code = new PriceLookUpCode(PLU);

//        PLUCodedProduct product = ProductDatabases.PLU_PRODUCT_DATABASE.get(code);
//
//        String name = product.getDescription();
//
//        button.setText(name);

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);

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
