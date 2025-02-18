import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import BloodBank.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class BloodRequest {

    public static void refreshSearch(JButton searchButton) {
        // Simulate a button click to trigger the search action
        for (ActionListener al : searchButton.getActionListeners()) {
            al.actionPerformed(new ActionEvent(searchButton, ActionEvent.ACTION_PERFORMED, null));
        }
    }

    public static void main(String args[]) {
        try {
            // Create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resolve the object reference in the naming service
            String name = "BloodBankServer";
            BloodBankServer bloodBankServer = BloodBankServerHelper.narrow(ncRef.resolve_str(name));

            // Create the JFrame
            JFrame frame = new JFrame("Blood Bank Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800); // Increased frame size

            // Create the JPanel and set the layout
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 5, 15, 5); // Increased padding
            

         // Font size
            Font font = new Font("Arial", Font.PLAIN, 30);
            JButton checkInventoryButton = new JButton("Check Inventory");
            checkInventoryButton.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            panel.add(checkInventoryButton, gbc);
            JButton inventoryHistoryButton = new JButton("Inventory History");
            inventoryHistoryButton.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(inventoryHistoryButton, gbc);
            
            
            checkInventoryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Retrieve inventory data from the server
                        String[] inventory = bloodBankServer.getInventory();
                        // Define column names
                        String[] columnNames = {"Blood Type", "Total"};
                        // Create a table model with the column names
                        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                        // Populate the table model with inventory data
                        for (String record : inventory) {
                            String[] parts = record.split(",");
                            if (parts.length == 2) {
                                tableModel.addRow(parts);
                            }
                        }

                        // Create a table with the table model
                        JTable table = new JTable(tableModel);
                        // Set font and row height for the table
                        table.setFont(new Font("Arial", Font.PLAIN, 25));
                        table.setRowHeight(30);
                        // Set font for the table header
                        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 25));

                        // Add the table to a scroll pane
                        JScrollPane scrollPane = new JScrollPane(table);
                        // Set preferred size for the scroll pane
                        scrollPane.setPreferredSize(new Dimension(800, 400));

                        // Create a custom OK button with a larger size
                        JButton okButton = new JButton("OK");
                        okButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
                        okButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Larger font size

                        // Create a panel for the OK button
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(okButton);

                        // Create the custom dialog panel
                        JPanel customPanel = new JPanel(new BorderLayout());
                        customPanel.add(scrollPane, BorderLayout.CENTER);
                        customPanel.add(buttonPanel, BorderLayout.SOUTH);

                        // Create a JDialog to show the custom content
                        JDialog dialog = new JDialog(frame, "Blood Inventory", true);
                        dialog.getContentPane().add(customPanel);
                        dialog.pack();
                        dialog.setLocationRelativeTo(frame);

                        // Add an ActionListener to the OK button to close the dialog
                        okButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                        // Show the dialog
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // Show an error message in case of failure
                        JOptionPane.showMessageDialog(frame, "Failed to retrieve inventory data.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            
            inventoryHistoryButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Fetch inventory history
                        String[] history = bloodBankServer.getInventoryHistory(); // Assuming this method exists
                        String[] columnNames = {"Blood ID", "Blood Type", "Donor ID", "Donor Name", "Donation Date", "Receiver ID","Receiver Name", "Receive Date", "Status"};

                        // Create a table model with column names
                        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                        // Populate the table model with inventory data
                        for (String record : history) {
                            String[] parts = record.split(","); // Use limit to include empty strings
                            if (parts.length == 9) { // Correctly checking for 8 parts
                                tableModel.addRow(parts);
                            }
                        }

                        // Create a JTable with the data and column names
                        JTable table = new JTable(tableModel);
                        table.setFont(new Font("Arial", Font.PLAIN, 25));
                        table.setRowHeight(50);
                        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 30));

                        // Create a scroll pane and add the table to it
                        JScrollPane scrollPane = new JScrollPane(table);
                        scrollPane.setPreferredSize(new Dimension(2000, 800)); // Adjust size as needed

                        // Create the custom OK button with a larger size
                        JButton okButton = new JButton("OK");
                        okButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
                        okButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Larger font size

                        // Create a panel for the OK button
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(okButton);

                        // Create the custom dialog panel
                        JPanel customPanel = new JPanel(new BorderLayout());
                        customPanel.add(scrollPane, BorderLayout.CENTER);
                        customPanel.add(buttonPanel, BorderLayout.SOUTH);

                        // Create and configure the JDialog
                        JDialog dialog = new JDialog(frame, "Inventory History", true);
                        dialog.getContentPane().add(customPanel);
                        dialog.pack();
                        dialog.setLocationRelativeTo(frame);

                        // Add an ActionListener to the OK button to close the dialog
                        okButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog.dispose();
                            }
                        });

                        // Show the dialog
                        dialog.setVisible(true);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Failed to retrieve inventory history.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });






            JLabel label1 = new JLabel("Blood Type");
            label1.setFont(font);
            String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
            JComboBox<String> bloodTypeComboBox = new JComboBox<>(bloodTypes);
            bloodTypeComboBox.setFont(font);
            JButton searchButton = new JButton("Search");
            searchButton.setFont(font);

            // Add components to panel with GridBagConstraints
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.LINE_START;
            panel.add(label1, gbc);

            gbc.gridx = 1;
            gbc.gridwidth = 1;
            panel.add(bloodTypeComboBox, gbc);

            gbc.gridx = 2;
            panel.add(searchButton, gbc);

            // Create a JTable to display search results
            String[] columnNames = {"Donor Name", "Donation Date", "Expired Date", "Action"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(tableModel) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 3; // Only the "Action" column is editable
                }
            };

            table.setFont(new Font("Arial", Font.PLAIN, 30));
            table.setRowHeight(50);
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 35));

            // Add custom renderer and editor for the "Action" column
            table.getColumn("Action").setCellRenderer(new ButtonRenderer());
            table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), bloodBankServer, frame, tableModel, searchButton));

            // Add the JTable to a JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 1;
            panel.add(scrollPane, gbc);

            searchButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String selectedBloodType = (String) bloodTypeComboBox.getSelectedItem();
                    String[] searchResults = null;

                    try {
                        searchResults = bloodBankServer.bloodsearch(selectedBloodType);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    // Clear previous search results
                    tableModel.setRowCount(0);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    for (String result : searchResults) {
                        String[] parts = result.split(",");
                        String blood_id = parts[0].trim();
                        String donor_name = parts[2].trim();
                        String donation_date = parts[3].trim();

                        // Parse the donation date
                        LocalDate donationDate = LocalDate.parse(donation_date, formatter);

                        // Add 42 days to the donation date
                        LocalDate expirationDate = donationDate.plusDays(42);

                        // Format the expiration date
                        String expirationDateString = expirationDate.format(formatter);

                        Object[] row = {donor_name, donation_date, expirationDateString, blood_id};
                        tableModel.addRow(row);
                    }

                    table.revalidate();
                    table.repaint();
                }
            });

            frame.add(panel);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Custom renderer for JTable buttons
//Custom renderer for JTable buttons
class ButtonRenderer extends JButton implements TableCellRenderer {
 public ButtonRenderer() {
     setOpaque(true);
 }

 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
     if (isSelected) {
         setForeground(table.getSelectionForeground());
         setBackground(table.getSelectionBackground());
     } else {
         setForeground(table.getForeground());
         setBackground(UIManager.getColor("Button.background"));
     }
     setText("Get Form");
     setFont(new Font("Arial", Font.PLAIN, 30)); // Ensure font size is consistent
     setPreferredSize(new Dimension(150, 50)); // Set preferred size
     return this;
 }
}


// Custom editor for JTable buttons
class ButtonEditor extends DefaultCellEditor {
    private String bloodId;
    private BloodBankServer bloodBankServer;
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JButton button;
    private JButton searchButton; // Add this line to hold the reference to the search button

    public ButtonEditor(JCheckBox checkBox, BloodBankServer bloodBankServer, JFrame frame, DefaultTableModel tableModel, JButton searchButton) {
        super(checkBox);
        this.bloodBankServer = bloodBankServer;
        this.frame = frame;
        this.tableModel = tableModel;
        this.searchButton = searchButton; // Assign the search button
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                showForm();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        bloodId = (String) tableModel.getValueAt(row, 3);
        button.setFont(new Font("Arial", Font.PLAIN, 30)); // Increase the font size
        button.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        button.setText("Get Form");
        return button;
    }


    public Object getCellEditorValue() {
        return bloodId;
    }

    private void showForm() {
        JFrame formFrame = new JFrame("Receiver Details");
        formFrame.setSize(800, 800);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Arial", Font.PLAIN, 30);

        JLabel label2 = new JLabel("Receiver Name");
        label2.setFont(font);
        JTextField receiverNameField = new JTextField(30);
        receiverNameField.setFont(font);
        JLabel label3 = new JLabel("Receiver Phone");
        label3.setFont(font);
        JTextField receiverPhoneField = new JTextField(30);
        receiverPhoneField.setFont(font);
        JLabel label4 = new JLabel("Receive Date");
        label4.setFont(font);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(font);

        JLabel label5 = new JLabel("Receiver Age");
        label5.setFont(font);
        JTextField receiverAgeField = new JTextField(30);
        receiverAgeField.setFont(font);
        JLabel label6 = new JLabel("Receiver Gender");
        label6.setFont(font);
        JRadioButton maleRadioButton = new JRadioButton("Male");
        JRadioButton femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        maleRadioButton.setFont(font);
        femaleRadioButton.setFont(font);
        JLabel label7 = new JLabel("Receiver Address");
        label7.setFont(font);
        JTextField receiverAddressField = new JTextField(30);
        receiverAddressField.setFont(font);
        JLabel label8 = new JLabel("Receiver Email");
        label8.setFont(font);
        JTextField receiverEmailField = new JTextField(30);
        receiverEmailField.setFont(font);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(font);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(label2, gbc);

        gbc.gridx = 1;
        formPanel.add(receiverNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(label3, gbc);

        gbc.gridx = 1;
        formPanel.add(receiverPhoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(label4, gbc);

        gbc.gridx = 1;
        formPanel.add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(label5, gbc);

        gbc.gridx = 1;
        formPanel.add(receiverAgeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(label6, gbc);

        gbc.gridx = 1;
        formPanel.add(maleRadioButton, gbc);

        gbc.gridx = 2;
        formPanel.add(femaleRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(label7, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(receiverAddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formPanel.add(label8, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(receiverEmailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        formPanel.add(confirmButton, gbc);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String receiverName = receiverNameField.getText();
                String receiverPhone = receiverPhoneField.getText();
                String receiveDate = datePicker.getJFormattedTextField().getText();
                String receiverAge = receiverAgeField.getText();
                String receiverGender = maleRadioButton.isSelected() ? "Male" : (femaleRadioButton.isSelected() ? "Female" : "");
                String receiverAddress = receiverAddressField.getText();
                String receiverEmail = receiverEmailField.getText();

                try {
                    String result = bloodBankServer.bloodrequest(bloodId, receiverName, receiverPhone, receiveDate, receiverAge, receiverGender, receiverAddress, receiverEmail);
                    String htmlMessage = "<html><body style='font-size:20px;'>" + result + "</body></html>";
                    //JOptionPane.showMessageDialog(frame, htmlMessage);
                    JLabel messageLabel = new JLabel(htmlMessage);

                    // Create the custom OK button with a larger size
                    JButton okButton = new JButton("OK");
                    okButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
                    okButton.setFont(new Font("Arial", Font.PLAIN, 15)); // Larger font size

                    // Create a panel for the OK button
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(okButton);

                    // Create the custom JOptionPane panel
                    JPanel customPanel = new JPanel(new BorderLayout());
                    customPanel.add(messageLabel, BorderLayout.CENTER);
                    customPanel.add(buttonPanel, BorderLayout.SOUTH);

                    // Create a JDialog to show the custom JOptionPane
                    JDialog dialog = new JDialog(frame, "Message", true);
                    dialog.getContentPane().add(customPanel);
                    dialog.pack();
                    dialog.setLocationRelativeTo(frame);

                    // Add an ActionListener to the OK button to close the dialog
                    okButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                        }
                    });

                    // Show the dialog
                    dialog.setVisible(true);


                    // Clear all text fields and other inputs
                    receiverNameField.setText("");
                    receiverPhoneField.setText("");
                    datePicker.getModel().setValue(null);
                    receiverAgeField.setText("");
                    genderGroup.clearSelection();
                    receiverAddressField.setText("");
                    receiverEmailField.setText("");

                    // Close the form
                    formFrame.dispose();

                    // Refresh the search results
                    BloodRequest.refreshSearch(searchButton); // Call refreshSearch method
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        formFrame.add(formPanel);
        formFrame.setVisible(true);
    }
}

// Custom formatter for JDatePicker
/*class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "yyyy-MM-dd";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}*/
