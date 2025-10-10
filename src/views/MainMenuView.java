package views;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame{
    private JPanel mainPanel;
    private JButton wardrobeButton;
    private JButton laundryButton;
    private JButton plannerButton;
    private JButton statisticsButton;
    private JButton outfitGeneratorButton;
    private JTextArea warningArea;
    private JPanel wrapperPanel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    //constructors
    public MainMenuView(){
        this.add(mainPanel);
        this.setSize(400, 400);
        this.setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Action listeners
    void setWardrobeButtonListener(ActionListener listener){
        wardrobeButton.addActionListener(listener);
    }
    void setLaundryButtonListener(ActionListener listener){
        laundryButton.addActionListener(listener);
    }
    void setPlannerButtonListener(ActionListener listener){
        plannerButton.addActionListener(listener);
    }
    void setStatisticsButtonListener(ActionListener listener){
        statisticsButton.addActionListener(listener);
    }
    void setOutfitGeneratorButtonListener(ActionListener listener){
        outfitGeneratorButton.addActionListener(listener);
    }
}
