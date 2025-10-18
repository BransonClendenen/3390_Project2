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
    private JRadioButton thisMonthRadioButton;
    private JRadioButton threeMonthButton;
    private JRadioButton allTimeRadioButton;
    private JPanel wrapperPanel;
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
    public JButton getBackButton() {
        return backButton;
    }


    public JLabel getMonthLabel() {
        return monthLabel;
    }



    private void createUIComponents() {

    }
}
