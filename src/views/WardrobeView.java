package views;

import javax.swing.*;


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

}
