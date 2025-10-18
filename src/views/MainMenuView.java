package views;

import javax.swing.*;


public class MainMenuView extends JFrame{
    private JPanel mainPanel;
    private JButton wardrobeButton;
    private JButton laundryButton;
    private JButton plannerButton;
    private JButton statisticsButton;
    private JButton outfitGeneratorButton;
    private JTextArea warningArea;
    private JPanel wrapperPanel;
    private JPanel wrapperPanell;

    public JPanel getMainPanel() {

        return mainPanel;
    }
    public JButton getWardrobeButton() {
        return wardrobeButton;
    }
    public JButton getLaundryButton() {
        return laundryButton;
    }
    public JButton getPlannerButton() {
        return plannerButton;
    }
    public JButton getStatisticsButton() {
        return statisticsButton;
    }
    public JTextArea getWarningArea() {
        return warningArea;
    }

    public JButton getOutfitGeneratorButton()
    {
        return outfitGeneratorButton;

    }

}
