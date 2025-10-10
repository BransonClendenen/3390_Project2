package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class StatisticsView {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton backButton;
    private JPanel togglePanel;
    private JRadioButton thisMonthButton;
    private JRadioButton threeMonthButton;
    private JPanel centerPanel;
    private JTable statsTable;
    private JPanel bottomPanel;
    private JLabel monthLabel;
    private JRadioButton allTimeButton;
    private JPanel radioWrapperPanel;
    private JPanel backWrapper;
    private ButtonGroup statsToggleGroup;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    //Month action listeners
    public void setBackButtonListener(ActionListener listener){
        backButton.addActionListener(listener);
    }
    public void setMonthButtonListener(ActionListener listener){
        thisMonthButton.addActionListener(listener);
    }
    public void setThreeMonthButtonListener(ActionListener listener){
        threeMonthButton.addActionListener(listener);
    }
    public void setAllTimeButtonButtonListener(ActionListener listener){
        allTimeButton.addActionListener(listener);
    }




}
