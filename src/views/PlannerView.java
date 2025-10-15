package views;

import javax.swing.*;
import java.awt.*;

public class PlannerView {
    private JButton backButton;
    private JLabel headerLabel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JTable plannerTable;
    private JPanel rightPanel;
    private JList wardrobeList;
    private JPanel categoryPanel;
    private JButton shirtsButton;
    private JButton pantsButton;
    private JButton shoesButton;
    private JPanel bottomPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JPanel mainPanel;
    private JSpinner dateSelector;
    private JPanel headerDatePanel;

    public JPanel getMainPanel(){

        return mainPanel;
    }


    public JTable getPlannerTable() {
        return plannerTable;
    }
    public JButton getAddButton() {
        return addButton;
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JList<String> getWardrobeList() {
        return wardrobeList;
    }

        public JSpinner getDateSelector() {
        return dateSelector;
    }

    public JButton getBackButton() {
        return backButton;
    }


    public String getSelectedItem() {
        return getWardrobeList().getSelectedValue();
    }

    public String getSelectedDate() {
        Object value = getDateSelector().getValue();
        return (value != null) ? value.toString() : "";
    }


    private void createUIComponents() {
        dateSelector = new JSpinner(new SpinnerDateModel());

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSelector, " yyyy - MM -dd ");
        dateSelector.setEditor(editor);
        dateSelector.setPreferredSize(new Dimension(100, 25));
    }


    //public String getSelectedDate() {
      //  return dateSelector.getValue().toString();
    //}

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
