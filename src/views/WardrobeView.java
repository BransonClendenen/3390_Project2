package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WardrobeView extends JFrame {
    private JPanel centerPanel;
    private JLabel headerLabel;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton backButton;
    private JList itemList;
    private JTextField addItemField;
    private JPanel bottomBar;
    private JPanel leftColumn;
    private JPanel rightColumn;
    private JPanel centerColumn;
    private JButton shirtsButton;
    private JButton pantsButton;
    private JButton shoesButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton laundryButton;

    public JPanel getMainPanel() {
        return mainPanel;

    }

    public JList<String> getItemList() {
        return itemList;
    }

    public JButton getShirtsButton() {
        return shirtsButton;
    }

    public JButton getPantsButton() {
        return pantsButton;
    }

    public JButton getShoesButton() {
        return shoesButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getLaundryButton() {
        return laundryButton;
    }

    public JTextField getInputField() {
        return addItemField;
    }

    public JButton getBackButton() {
        return backButton;
    }

/*
    public WardrobeView() {
        this.add(mainPanel);
        this.setSize(400, 400);
        this.setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //TODO: itemList needs to be set to model when its created
        //something like:
        // studentData = new DefaultListModel<>();
        // studentList.setModel(studentData);
    }

    //public helper functions
    public void clearForm(){
        addItemField.setText("");
    }

    //text field getters
    public String getAddItem(){
        return addItemField.getText();
    }

    //action listeners
    public void setShirtButtonListener(ActionListener listener){
        shirtButton.addActionListener(listener);
    }
    public void setPantsButtonListener(ActionListener listener){
        pantsButton.addActionListener(listener);
    }
    public void setShoesButtonListener(ActionListener listener){
        shoesButton.addActionListener(listener);
    }
    public void setAddButtonListener(ActionListener listener){
        addButton.addActionListener(listener);
    }
    public void setEditButtonListener(ActionListener listener){
        editButton.addActionListener(listener);
    }
    public void setDeleteButtonListener(ActionListener listener){
        deleteButton.addActionListener(listener);
    }
    public void setLaundryButtonListener(ActionListener listener){
        laundryButton.addActionListener(listener);
    }
    public void setBackButtonListener(ActionListener listener){
        backButton.addActionListener(listener);
    }*/
}
