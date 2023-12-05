/** SENG300 Group Project
 * (Adapted from Project Iteration 2 - Group 5)
 *
 * Iteration 3 - Group 3
 * @author Jaimie Marchuk - 30112841
 * @author Wyatt Deichert - 30174611
 * @author Jane Magai - 30180119
 * @author Enzo Mutiso - 30182555
 * @author Mauricio Murillo - 30180713
 * @author Ahmed Ibrahim Mohamed Seifeldin Hassan - 30174024
 * @author Aryaman Sandhu - 30017164
 * @author Nikki Kim - 30189188
 * @author Jayden Ma - 30184996
 * @author Braden Beler - 30084941
 * @author Danish Sharma - 30172600
 * @author Angelina Rochon - 30087177
 * @author Amira Wishah - 30182579
 * @author Walija Ihsan - 30172565
 * @author Hannah Pohl - 30173027
 * @author Akashdeep Grewal - 30179657
 * @author Rhett Bramfield - 30170520
 * @author Arthur Huan - 30197354
 * @author Jaden Myers - 30152504
 * @author Jincheng Li - 30172907
 * @author Anandita Mahika - 30097559
 */


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
