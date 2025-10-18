package views;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlannerView {
    private JButton backButton;
    private JLabel headerLabel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JTable plannerTable;
    private JPanel rightPanel;

    private JList<String> wardrobeList;    //removes the “unchecked cast” warning when
                                           // controller calls getWardrobeList().

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



    public JTable getPlannerTable() {
        return plannerTable;
    }
    public JButton getAddButton() {
        return addButton;
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }



    public JList<String> getWardrobeList() {
        return wardrobeList;
    }

    public JButton getShirtsButton() { return shirtsButton; }
    public JButton getPantsButton() { return pantsButton; }
    public JButton getShoesButton() { return shoesButton; }

        public JSpinner getDateSelector() {
        return dateSelector;
    }



    public String getSelectedItem() {
        return getWardrobeList().getSelectedValue();
    }


    public String getSelectedDate() {
        Object value = getDateSelector().getValue();
        if (value instanceof java.util.Date) {
            SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy");
            return fmt.format((java.util.Date) value);
        }
        return "";
    }


    private void createUIComponents() {
        dateSelector = new JSpinner(
                new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH)
        );

        JSpinner.DateEditor editor =
                new JSpinner.DateEditor(dateSelector, "MM-dd-yyyy");
        dateSelector.setEditor(editor);
        dateSelector.setPreferredSize(new Dimension(90, 25));
    }


}


