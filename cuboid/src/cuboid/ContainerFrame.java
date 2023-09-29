package cuboid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

// ContainerFrame class for CE203 Assignment to use and modify if needed
// Date: 12/10/2021
// Author: F. Doctor

// A skeleton JFrame class has been provided which you would need to modifiy to include the other GUI components
// and functionality specified in the assignment specification
public class ContainerFrame extends JFrame {

    private JTextField widthField;
    private JTextField lengthField;
    private JTextField colorField;
    private JTextField idField;
    // field to hold current displayed panel so we can swap to new panels when needed
    private JPanel currentPanel = null;
    //adding cuboid containers
    private List<CuboidContainer> cuboidContainers = new ArrayList<>();

    public void createComponents() {

        ButtonsPanel buttonsPanel = new ButtonsPanel();
        Button addCuboid = new Button("Add Cuboid");
        addCuboid.setEnabled(false);
        //adding add to cuboids clicked listener so we can add new cuboid to the existing list
        addCuboid.addActionListener(actionEvent -> this.onAddClicked());
        buttonsPanel.addButton(addCuboid);

        Button searchCuboid = new Button("Search Cuboid");
        buttonsPanel.addButton(searchCuboid);
        searchCuboid.setEnabled(false);
        //adding draw clicked listener to act when the draw cuboid button is clicked
        searchCuboid.addActionListener(actionEvent -> this.onDrawClicked());

        //instance of a panel to hold all input fields
        InputPanel inputPanel = new InputPanel();
        widthField = new JTextField(20);
        inputPanel.addInputField("Width", widthField);
        lengthField = new JTextField(20);
        inputPanel.addInputField("Length", lengthField);
        colorField = new JTextField(20);
        inputPanel.addInputField("Color", colorField);

        final int idSize = 6;
        idField = new JTextField(idSize);

        //add a listener to prevent user from entering more than 6 characters and also enable the search button when the id  is 6 characters
        idField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                if (idField.getText().length() >= idSize && !(evt.getKeyChar() == KeyEvent.VK_DELETE || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                    evt.consume();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchCuboid.setEnabled(idField.getText().length() == 6);
                addCuboid.setEnabled(!widthField.getText().isEmpty()
                        && !lengthField.getText().isEmpty()
                        && !colorField.getText().isEmpty()
                        && !idField.getText().isEmpty());

            }
        });

        widthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                addCuboid.setEnabled(!widthField.getText().isEmpty()
                        && !lengthField.getText().isEmpty()
                        && !colorField.getText().isEmpty()
                        && !idField.getText().isEmpty());
            }
        });

        lengthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                addCuboid.setEnabled(!widthField.getText().isEmpty()
                        && !lengthField.getText().isEmpty()
                        && !colorField.getText().isEmpty()
                        && !idField.getText().isEmpty());
            }
        });

        colorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                addCuboid.setEnabled(!widthField.getText().isEmpty()
                        && !lengthField.getText().isEmpty()
                        && !colorField.getText().isEmpty()
                        && !idField.getText().isEmpty());
            }
        });


        inputPanel.addInputField("Id", idField);
        add(inputPanel.getLayout(), BorderLayout.NORTH);
        add(buttonsPanel.getLayout(), BorderLayout.SOUTH);

        setTitle("Cuboid Display");
        //set display to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Close action.
    }

    /**
     * this method add a new cuboid to the cuboids list. it also does error checking and informs the user accordingly
     */
    private void onAddClicked() {
        String widthText = widthField.getText();
        String lengthText = lengthField.getText();
        String idText = idField.getText();
        int width, length, id = 0;
        if (idText.charAt(0) == '0') {
            JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. ID cannot start with 0");
            return;
        }

        if (idText.length() < 6) {
            JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. ID must be exactly 6 numbers");
            return;
        }

        try {
            id = Integer.parseInt(idText);
            final int cId = id;
            if (id < 0) {
                JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. Id cannot be a negative number");
                return;
            }

            final Optional<CuboidContainer> panel = cuboidContainers.stream().filter(cuboidContainer -> cuboidContainer.id == cId).findFirst();
            if (panel.isPresent()) {
                JOptionPane.showMessageDialog(null, "Cuboid " + id + " already exists because it has not been added to the cuboids yet");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. Id must be a number");
            return;
        }


        try {
            width = Integer.parseInt(widthText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. Width must be a number");
            return;
        }

        try {
            length = Integer.parseInt(lengthText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The Cuboid " + idText + " was not added to the list as a valid id was not provided. Length must be a number");
            return;
        }


        cuboidContainers.add(new CuboidContainer(id, width, length, StringToColor.getColor(colorField.getText())));
        Collections.sort(cuboidContainers);
        System.out.println("\n----------------------> Cuboid ids <--------------------");
        cuboidContainers.forEach(cuboidContainer -> System.out.println(cuboidContainer.id));
        System.out.println("\n--------------------------------------------------------");

        System.out.println("\n----------------------> Cuboid  <--------------------");
        cuboidContainers.forEach(cuboidContainer -> System.out.println(cuboidContainer.toString()));
        System.out.println("\n--------------------------------------------------------");
        JOptionPane.showMessageDialog(null, "Cuboid " + idText + " has been added to the list.");
        System.out.println("Cuboid " + idText + " has been added to the list.");
        widthField.setText("");
        lengthField.setText("");
        colorField.setText("");
        idField.setText("");
    }

    /**
     * method for drawing a cuboid in the panel by its id
     */
    private void onDrawClicked() {
        String idText = idField.getText();

        try {
            final int id = Integer.parseInt(idText);
            final Optional<CuboidContainer> panel = cuboidContainers.stream().filter(cuboidContainer -> cuboidContainer.id == id).findFirst();
            if (panel.isPresent()) {
                if (currentPanel != null) {
                    remove(currentPanel);
                }
                this.currentPanel = new ContainerPanel(panel.get());
                add(currentPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Cuboid with id " + id + " does not exists");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "id can only be a number");
        }
    }


    public static void main(String[] args) {

        ContainerFrame cFrame = new ContainerFrame();
        cFrame.createComponents();
    }

}
