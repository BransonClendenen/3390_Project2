package views;

import javax.swing.*;

public class StatisticsView {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton backButton;
    private JPanel togglePanel;
    private JPanel centerPanel;
    private JTable statsTable;
    private JPanel bottomPanel;
    private JLabel monthLabel;
    private JPanel radioWrapperPanel;
    private JPanel backWrapper;
    private JRadioButton thisMonthRadioButton;
    private JRadioButton threeMonthButton;
    private JRadioButton allTimeRadioButton;
    private ButtonGroup statsToggleGroup;

    public JPanel getMainPanel(){

        return mainPanel;
    }

                 // Controller accessors
    public JRadioButton getThisMonthButton() {
        return thisMonthRadioButton;
    }

    public JRadioButton getThreeMonthButton() {
        return threeMonthButton;
    }

    public JRadioButton getAllTimeButton() {
        return allTimeRadioButton;
    }

    public JTable getStatsTable() {
        return statsTable;
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
/*
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


*/

}
