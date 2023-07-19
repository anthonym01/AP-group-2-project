package com.flowapp.view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.flowapp.logging.Log;
import com.flowapp.model.Employee;
import com.flowapp.model.Message;
import com.flowapp.model.Response;
import com.flowapp.view.forms.AssignMessageForm;
import com.flowapp.view.listeners.AssignMessageFormListener;
import com.flowapp.view.listeners.DetailSidebarListener;
import com.flowapp.view.listeners.TableItemListener;



public class EmpMessageTablePanel extends JTable {
    private JTable table;
    private EmpMessageTableModel model;
    private JScrollPane scrollPane;

    private Message message;
    private int selectedRow;

    private String role;
    private Employee loggedInEmployee;

    // Listeners
    private TableItemListener tableItemListener;
    private DetailSidebarListener detailSidebarListener;

    // Forms
    private AssignMessageForm assignMessageForm;


    public EmpMessageTablePanel(String role) {
        super();

        this.role = role;

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
    }

    public void setMessages(List<Message> messages){
        model = (EmpMessageTableModel) table.getModel();
        model.setMessages(messages);
        model.fireTableDataChanged();

        //repaint();
        //revalidate();
    }

    public void setTechnicians(List<Employee> employees){
        assignMessageForm.setTechnicians(employees);
    }

    public void updateMessage(Message message){
        model = (EmpMessageTableModel) table.getModel();

        model.updateMessage(message);
        model.fireTableDataChanged();

        reHighlightLastSelectedRow();
    }

    private void focusOnLoginPanel(){
        setFocusable(true);
        grabFocus();
    }

    // Setters
    public void setTableItemListener(TableItemListener tableItemListener) {
        this.tableItemListener = tableItemListener;
    }

    public void setDetailSidebarListener(DetailSidebarListener detailSidebarListener) {
        this.detailSidebarListener = detailSidebarListener;
    }

    public void setEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        model = new EmpMessageTableModel(new ArrayList<Message>());
        table = new JTable(model);

        scrollPane = new JScrollPane(table);

        // Table Settings
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);

        assignMessageForm = new AssignMessageForm(null, "Assign Message To Technician");

        // Setup the assignment form listener to capture whenever the save button is clicked from the form's side...
        assignMessageForm.setAssignMessageFormListener(new AssignMessageFormListener() {
            @Override
            public void save(Employee employee) {
                // Set the selected technician to the current selected message...
                message.setEmployee(employee);

                // Show live update on the table...
                model = (EmpMessageTableModel) table.getModel();
                model.fireTableDataChanged();

                // Send the message object to be displayed on the details sidebar...
                if(detailSidebarListener != null) detailSidebarListener.readOnly(message);

                // Send the updated message to the database...
                if(tableItemListener != null) tableItemListener.assignMessageTo(message, employee);
            }
        });
    }

    // Add components to panel
    private void addComponentsToPanel(){
        
        add(scrollPane, BorderLayout.CENTER);
    }

    // Register listeners
    private void registerListeners(){
        // Selection Listener
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int viewRow = table.getSelectedRow();

                // To prevent duplicate selections
                if (!e.getValueIsAdjusting() && viewRow != -1) {
                    try{
                        // Retrieve the lead object from the hidden cell in the selected row...
                        // Since the column sorting is on... use this to retrieve a selected object correct -> table.convertRowIndexToModel(viewRow)
                        message = model.getMessageAt(table.convertRowIndexToModel(viewRow));

                        // System.out.println("Selected Message: " + message + "\n\n");
                        // Send the message object to be displayed on the details sidebar...
                        if(detailSidebarListener != null) detailSidebarListener.readOnly(message);
                    }
                    catch (ClassCastException | IndexOutOfBoundsException ex){
                        Log.error("Error inside of the MessageTablePanel [addListSelectionListener] -> " + ex.getMessage());
                    }
                }
            }
        });

        // Mouse Lister
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Set up mouse right-click
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    selectedRow = row;

                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);

                        // Creating a popup menu for the table... when user right click, it will be displayed
                        JPopupMenu menu = new JPopupMenu();

                        JMenuItem assignToItem = new JMenuItem("Assign To");
                        JMenuItem deleteItem = new JMenuItem("Delete");
                        JMenuItem resolvedItem = new JMenuItem("Resolved");
                        JMenuItem respondItem = new JMenuItem("Respond");
                        
                        // Set Tooltips
                        assignToItem.setToolTipText("Select to assign this message to a technician.");
                        deleteItem.setToolTipText("Select to delete this message.");
                        resolvedItem.setToolTipText("Select to set resolved status of this message.");
                        respondItem.setToolTipText("Select to add a response to this message.");

                        //Set up action listeners -> dashboard specific...
                        if(role.equalsIgnoreCase("technician")){
                            // Respond
                            respondItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    showResponseForm();
                                }
                            });

                            menu.add(respondItem);
                        }
                        else if(role.equalsIgnoreCase("representative")){
                            // Assign to technician
                            assignToItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Show the assignment form
                                    if(!assignMessageForm.isVisible()) assignMessageForm.setVisible(true);
                                }
                            });

                            // Deleting
                            deleteItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this message?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                                    if(choice != JOptionPane.YES_OPTION) return;

                                    // Show live update on the table...
                                    model = (EmpMessageTableModel) table.getModel();
                                    model.deleteMessage(message);
                                    model.fireTableDataChanged();

                                    // Send the updated message to the database...
                                    if(tableItemListener != null) tableItemListener.delete(message);
                                    if(detailSidebarListener != null) detailSidebarListener.clearSidebar();
                                }
                            });

                            // Respond
                            respondItem.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    int choice = JOptionPane.showConfirmDialog(null, "To respond to this message, you will be assigned. Are you sure you want to respond?", "", JOptionPane.YES_NO_OPTION);

                                    if(choice == JOptionPane.YES_OPTION) showResponseForm();
                                }
                            });

                            menu.add(assignToItem);
                            menu.add(deleteItem);
                            menu.add(respondItem);
                        }
                        
                        // Resolved Status
                        resolvedItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this message resolved status?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                                if(choice != JOptionPane.YES_OPTION) return;

                                // Set the new value opposing the existing value
                                message.setResolved(!message.isResolved());

                                // Show live update on the table...
                                model = (EmpMessageTableModel) table.getModel();
                                model.fireTableDataChanged();

                                // Send the updated message to the database...
                                if(tableItemListener != null) tableItemListener.update(message);
                            }
                        });

                        menu.add(resolvedItem);

                        menu.show(e.getComponent(), e.getPoint().x, e.getPoint().y);
                    }
                }
            }
        });
    }

    // Reselect the current selected row after the highlight vanished after calling -> model.fireTableDataChanged()
    public void reHighlightLastSelectedRow(){
        table.setRowSelectionInterval(selectedRow, selectedRow);
    }

    private void showResponseForm(){
        String text = JOptionPane.showInputDialog(null, "Enter your response: ", "Response Form", JOptionPane.DEFAULT_OPTION);

        if(text == null) return;

        if(text.length() < 1 || text.equalsIgnoreCase("") || text.isBlank() || text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Field cannot be empty for submission... Please enter a response!", "Cannot Submit", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new response
        Response response = new Response();
        response.setContent(text);
        response.setMessage(message);
        response.setResponseDate(new Date());

        String userID = null;

        if(role.equalsIgnoreCase("technician")) {
            userID = (message.getEmployee() != null ? message.getEmployee().getIDNumber() : null);
        }
        else if(role.equalsIgnoreCase("representative")) {
            userID = (loggedInEmployee != null ? loggedInEmployee.getIDNumber() : null);
            message.setEmployee(loggedInEmployee);
        }
 
        response.setUserID(userID);
        
        // Add the new response to the selected message
        Set<Response> responses = message.getResponses();
        responses.add(response);
        
        // Show live update on the table...
        model = (EmpMessageTableModel) table.getModel();
        model.updateMessage(message);
        model.fireTableDataChanged();

        // Send the message object to be displayed on the details sidebar...
        if(detailSidebarListener != null) detailSidebarListener.readOnly(message);

        // Send the updated message to the database...
        if(tableItemListener != null) tableItemListener.addResponse(message, response);
    }
}
