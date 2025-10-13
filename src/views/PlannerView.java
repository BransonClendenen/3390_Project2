package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PlannerView {
    private JButton backButton;
    private JLabel headerLabel;
    private JTextField dateField;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JTable plannerTable;
    private JPanel rightPanel;
    private JList wardrobeList;
    private JPanel categoryPanel;
    private JButton shirtButton;
    private JButton pantsButton;
    private JButton shoesButton;
    private JPanel bottomPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel mainPanel;

    public JPanel getMainPanel(){
        return mainPanel;
    }

/*
    //Action Listeners
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    public void setShirtButtonListener(ActionListener listener) {
        shirtButton.addActionListener(listener);
    }
    public void setPantsButtonListener(ActionListener listener) {
        pantsButton.addActionListener(listener);
    }
    public void setShoesButtonsListener (ActionListener listener){
        shoesButton.addActionListener(listener);
    }
    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    public void setDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    //Get Date Field
    public String getDateField() {
        return dateField.getText();
    }*/
}
