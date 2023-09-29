package cuboid;

import javax.swing.*;

public class InputPanel {
    JPanel layout;

    public InputPanel() {
        this.layout = new JPanel();

    }

    // add a new input to the panel with it's associated label
    public void addInputField(String label, JTextField field) {
        JPanel columnPanel = new JPanel();
        columnPanel.add(new JLabel(label));
        columnPanel.add(field);
        layout.add(columnPanel);
    }

    //ge the panel
    public JPanel getLayout() {
        return layout;
    }
}
