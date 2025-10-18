package views;

import javax.swing.*;


public class LaundryView {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton backButton;
    private JLabel headerLabel;
    private JPanel centerPanel;
    private JList laundryList;
    private JPanel bottomPanel;
    private JButton checkOffButton;

    public JPanel getMainPanel() {

        return mainPanel;
    }


    public JList<String> getLaundryList() {

        return laundryList;
    }

    public JButton getCheckOffButton() {

        return checkOffButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

}