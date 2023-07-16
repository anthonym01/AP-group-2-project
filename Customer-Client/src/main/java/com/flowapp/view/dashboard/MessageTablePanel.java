package com.flowapp.view.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

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
import com.flowapp.model.Message;
import com.flowapp.view.listeners.DetailSidebarListener;
import com.flowapp.view.listeners.TableItemListener;



public class MessageTablePanel extends JTable {
    private JTable table;
    private MessageTableModel model;
    private JScrollPane scrollPane;

    private Message message;
    private int selectedRow;

    // Listeners
    private TableItemListener tableItemListener;
    private DetailSidebarListener detailSidebarListener;


    public MessageTablePanel() {
        super();

        setupWindowProperties();

        initComponents();
        addComponentsToPanel();
        registerListeners();

        focusOnLoginPanel();
    }

    public void setMessages(List<Message> messages){
        model = (MessageTableModel) table.getModel();
        model.setMessages(messages);
        model.fireTableDataChanged();

        //repaint();
        //revalidate();
    }

    public void updateMessage(Message message){
        model = (MessageTableModel) table.getModel();

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

    // Setup window properties
    private void setupWindowProperties(){
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
    }
        
    // Initialize components
    private void initComponents(){
        model = new MessageTableModel(new ArrayList<Message>());
        table = new JTable(model);

        scrollPane = new JScrollPane(table);

        // Table Settings
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);
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

                        JMenuItem updateItem = new JMenuItem("Update");
                        JMenuItem deleteItem = new JMenuItem("Delete");
                        JMenuItem resolvedItem = new JMenuItem("Resolved");
                        
                        // Set Tooltips
                        updateItem.setToolTipText("Select to update this message.");
                        deleteItem.setToolTipText("Select to delete this message.");
                        resolvedItem.setToolTipText("Select to set resolved status of this message.");

                        //Set up action listeners

                        // Updating
                        updateItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this message?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                                if(choice != JOptionPane.YES_OPTION) return;

                                // Send the message object to be displayed on the details sidebar...
                                if(detailSidebarListener != null) detailSidebarListener.allowEdit(message);
                            }
                        });

                        // Deleting
                        deleteItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this message?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                                if(choice != JOptionPane.YES_OPTION) return;

                                // Show live update on the table...
                                model = (MessageTableModel) table.getModel();
                                model.deleteMessage(message);
                                model.fireTableDataChanged();

                                // Send the updated message to the database...
                                if(tableItemListener != null) tableItemListener.delete(message);
                            }
                        });

                        // Resolved Status
                        resolvedItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this message resolved status?", "Confirm Action", JOptionPane.YES_NO_OPTION);

                                if(choice != JOptionPane.YES_OPTION) return;

                                // Set the new value opposing the existing value
                                message.setResolved(!message.isResolved());

                                // Show live update on the table...
                                model = (MessageTableModel) table.getModel();
                                model.fireTableDataChanged();

                                // Send the updated message to the database...
                                if(tableItemListener != null) tableItemListener.update(message);
                            }
                        });

                        menu.add(updateItem);
                        menu.add(deleteItem);
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
}
