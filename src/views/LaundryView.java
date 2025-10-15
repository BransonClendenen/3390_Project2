package views;

import javax.swing.*;
import java.awt.event.ActionListener;

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


    /*
    //Action Listeners
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void setCheckOffButtonListener(ActionListener listener) {
        checkOffButton.addActionListener(listener);
    }

*/
}