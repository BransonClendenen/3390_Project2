package views;

import javax.swing.*;
import java.awt.*;

public class OutfitGeneratorView {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton backButton;
    private JLabel headerLabel;
    private JPanel centerPanel;
    private JTable outfitTable;
    private JPanel buttonPanel;
    private JButton generateButton;
    private JButton finalizeButton;
    private JPanel contentWrapper;
    private JPanel backWrapper;
    private JPanel wrapperPanel;
    private JScrollPane scrollPane;

/*
    public OutfitGeneratorView() {
        //temporary debug border to make sure the scroll pane is visible
        if (scrollPane != null) {
            scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        }
    }
*/

    public JButton getGenerateButton() { return generateButton; }
    public JButton getFinalizeButton() { return finalizeButton; }
    public JButton getBackButton() { return backButton; }

    public JPanel getMainPanel() { return mainPanel; }
    public JTable getOutfitTable() {

        return outfitTable;
    }




    private void createUIComponents() {



        // TODO: place custom component creation code here
    }
/*

   public OutfitGeneratorView() {
        // temporary visual helpers
        if (mainPanel != null)  mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        if (contentWrapper != null)  contentWrapper.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        if (centerPanel != null)  centerPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        if (buttonPanel != null)  buttonPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3));
        if (outfitTable != null) {
            outfitTable.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            outfitTable.setBackground(Color.WHITE);
            outfitTable.setForeground(Color.BLACK);
        }
    }

*/
/*
    //Action Listeners
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void setGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
    public void setFinalizeButtonListener(ActionListener listener) {
        finalizeButton.addActionListener(listener);
    }   */
}
