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

    //Action Listeners
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void setCheckOffButtonListener(ActionListener listener) {
        checkOffButton.addActionListener(listener);
    }
}
