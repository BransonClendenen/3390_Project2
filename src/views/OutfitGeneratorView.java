package views;

import javax.swing.*;
import java.awt.event.ActionListener;

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

    public JPanel getMainPanel(){
        return mainPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
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
