package cuboid;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel{
    JPanel layout;

    public ButtonsPanel(){
        this.layout = new JPanel();
    }

    // add a new button to the panel
    public void addButton(Button button){
        this.layout.add(button);
    }

    // get the panel
    public JPanel getLayout() {
        return layout;
    }
}
