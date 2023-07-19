package com.flowapp.view.forms;

import com.flowapp.driver.Utils;
// import com.flowapp.view.listeners.AssignStaffListener;
import com.flowapp.model.Employee;
import com.flowapp.view.listeners.AssignMessageFormListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;


public class AssignMessageForm extends JDialog {
    private JComboBox<Employee> technichiansComboBox;
    private JButton saveBtn, cancelBtn;
    private JLabel label;

    private Employee selectedEmployee;

    // private AssignStaffListener assignStaffListener;
    private AssignMessageFormListener assignMessageFormListener;


    public AssignMessageForm(JFrame frame, String title){
        super(frame, title);

        setWindowProperties();
        initializeComponents();
        addComponentsToWindow();
        registerListeners();
    }

    // Setters
    public void setAssignMessageFormListener(AssignMessageFormListener assignMessageFormListener) {
        this.assignMessageFormListener = assignMessageFormListener;
    }

    public void setTechnicians(List<Employee> employees){
        technichiansComboBox.removeAllItems();

        // Re-populate the combo box
        for(Employee employee: employees){
            technichiansComboBox.addItem(employee);
        }
    }

    // Initializing the components
    private void initializeComponents(){
        label = new JLabel("", SwingConstants.CENTER);
        label.setToolTipText("No selection has been made as yet...");
        label.setFont(Utils.getFont(Font.BOLD, 25));

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");

        saveBtn.setEnabled(false);

        saveBtn.setToolTipText("Click button to assign message.");
        cancelBtn.setToolTipText("Click button to close window.");

        Dimension dimension = new Dimension(95, 27);
        saveBtn.setPreferredSize(dimension);
        cancelBtn.setPreferredSize(dimension);

        // Combobox
        technichiansComboBox = new JComboBox<>();
        technichiansComboBox.setToolTipText("Select a technician to assign this message to.");
    }

    // Adding the components to the panels
    private void addComponentsToWindow(){
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(technichiansComboBox);

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.add(label);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(saveBtn);
        southPanel.add(cancelBtn);

        add(centerPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        //pack();
    }

    // Setting the window properties
    private void setWindowProperties(){
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(350, 250));
        setMinimumSize(new Dimension(350, 250));
        setResizable(false);

        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Setting up listeners
    private void registerListeners(){
        // Advisor Dropdown List
        technichiansComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
               selectedEmployee = (Employee) technichiansComboBox.getSelectedItem();

                if(selectedEmployee == null) return;

                if(!selectedEmployee.getIDNumber().equalsIgnoreCase("--------------------------------")){
                    saveBtn.setEnabled(true);

                    String name = selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName();
                    label.setText(name);
                    label.setToolTipText("You have selected " + name + " for this message...");
                }
                else{
                    saveBtn.setEnabled(false);
                    label.setText("");
                    label.setToolTipText("No selection has been made as yet...");
                }
            }
        });

        // Cancel Button
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                saveBtn.setFocusable(false);
                cancelBtn.setFocusable(false);

                setVisible(false);
            }
        });
        
        // Save Button
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                saveBtn.setFocusable(false);
                cancelBtn.setFocusable(false);
                setAlwaysOnTop(false);

                if(assignMessageFormListener != null){
                    // Hide form after assignment made
                    setVisible(false);

                    String name = (selectedEmployee != null ? selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName() : "-");
                    String text = "Are you sure you want to assign this message to " + name +" ?";

                    int choice = JOptionPane.showConfirmDialog(null, text, "Confirm Action", JOptionPane.YES_NO_OPTION);

                    if(choice != JOptionPane.YES_OPTION) return;
                    
                    // Capture the selected employee [technician] then pass it to the save() where it will be added to the selected message...
                    assignMessageFormListener.save(selectedEmployee);
                }

                setAlwaysOnTop(true);
            }
        });
        
        // Add a window listener to clear the form when the window is closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // clearBtn.doClick();
                dispose(); // To quit the application after clean up...
                System.gc(); // Call garbage collector
            }
        });
    }
}
