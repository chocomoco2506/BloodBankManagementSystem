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
import java.util.Properties;
import javax.swing.*;

public class BloodDonate {
    private static JFrame frame;
    private static JPanel panel;
    private static CardLayout cardLayout;

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
            frame = new JFrame("Blood Donation");
            frame.setSize(800, 600);  // Increased size for better readability
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the main panel with CardLayout
            panel = new JPanel();
            cardLayout = new CardLayout();
            panel.setLayout(cardLayout);

            Font font = new Font("Arial", Font.PLAIN, 30); // Larger font size
            //Font font = new Font("Arial", Font.PLAIN, 30); // Larger font size
            Font titleFont = new Font("Arial", Font.BOLD, 50); // H1-like font size


            // NRC Panel
            JPanel nrcPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Add the title label at the top of the NRC panel
            JLabel titleLabel = new JLabel("Blood Donation System", JLabel.CENTER);
            titleLabel.setFont(titleFont);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 4;  // Span across all columns
            nrcPanel.add(titleLabel, gbc);

            // NRC Label
            JLabel nrcLabel = new JLabel("Please Fill Your NRC");
            nrcLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 1; // Move to the next row after the title
            gbc.gridwidth = 4;
            nrcPanel.add(nrcLabel, gbc);

            gbc.gridwidth = 1;  // Reset gridwidth after label
            gbc.gridy = 2;  // Move to next row

            JComboBox<String> nrcPart1ComboBox = new JComboBox<>(new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"
            });
            nrcPart1ComboBox.setFont(font);
            gbc.gridx = 0;
            nrcPanel.add(nrcPart1ComboBox, gbc);

            JComboBox<String> nrcPart2ComboBox = new JComboBox<>(new String[]{
                "AHGAPA", "AHMANA", "AHMATA", "BAKALA", "DADAYA", "DANAPHA", "HAKAKA", "HATHATA",
                "KAKAHTA", "KAKANA", "KAKHANA", "KALANA", "KAPANA", "LAMANA", "LAPATA", "MAAHNA",
                "MAAHPA", "MAMAKA", "MAMANA", "NGAPATA", "NGASANA", "NGATHAKHA", "NGAYKA", "NYATANA",
                "PASALA", "PATANA", "PATHAAH", "PATHANA", "PATHAYA", "PHAPANA", "THAPANA", "WAKHAMA",
                "YAKANA", "YATHAYA", "ZALANA"
            });
            nrcPart2ComboBox.setFont(font);
            gbc.gridx = 1;
            nrcPanel.add(nrcPart2ComboBox, gbc);

            JComboBox<String> nrcPart3ComboBox = new JComboBox<>(new String[]{
                "N", "E", "P", "S", "T", "Y"
            });
            nrcPart3ComboBox.setFont(font);
            gbc.gridx = 2;
            nrcPanel.add(nrcPart3ComboBox, gbc);

            // Move text field to the next row
            JTextField nrcTextField = new JTextField();
            nrcTextField.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 3;  // Span across three columns
            nrcPanel.add(nrcTextField, gbc);

            JButton nrcConfirmButton = new JButton("Confirm");
            nrcConfirmButton.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 4;
            nrcPanel.add(nrcConfirmButton, gbc);

            // Donor Detail Panel
            JPanel donorPanel = new JPanel(new GridBagLayout());

            // Create the "Show History" button
            JButton donationHistoryButton = new JButton("Show Donation History");
            donationHistoryButton.setFont(font);
            donationHistoryButton.setFont(font);
            donationHistoryButton.setPreferredSize(new Dimension(80, 30)); // Adjust button size

            //frame.setLayout(new FlowLayout());
         // Add "Back" button to the donor panel
            

            // ActionListener for Back Button
            

            // Add "Show History" button to the donor panel
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0; // Position above donor details
            gbc.gridwidth = 4; // Span across all columns
            donorPanel.add(donationHistoryButton, gbc);

            // Add "Registration Form" label below "Show History" button
            JLabel registrationFormLabel = new JLabel("Registration Form");
            registrationFormLabel.setFont(font);
            gbc.gridy = 1; // Move to the next row
            gbc.gridwidth = 4; // Span across all columns
            gbc.anchor = GridBagConstraints.CENTER; // Center alignment
            donorPanel.add(registrationFormLabel, gbc);

            // Adjust the position of other components
            gbc.gridwidth = 1; // Reset gridwidth to 1
            gbc.gridy = 2; // Move donor details down by one row
            JLabel donorNameLabel = new JLabel("Donor Name:");
            donorNameLabel.setFont(font);
            gbc.gridx = 0;
            donorPanel.add(donorNameLabel, gbc);

            JTextField donorNameField = new JTextField();
            donorNameField.setFont(font);
            gbc.gridx = 1;
            gbc.gridwidth = 3; // Span across three columns
            donorPanel.add(donorNameField, gbc);

            JLabel donorPhoneLabel = new JLabel("Donor Phone:");
            donorPhoneLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            donorPanel.add(donorPhoneLabel, gbc);

            JTextField donorPhoneField = new JTextField();
            donorPhoneField.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            donorPanel.add(donorPhoneField, gbc);

            JLabel donorAgeLabel = new JLabel("Donor Age:");
            donorAgeLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            donorPanel.add(donorAgeLabel, gbc);

            JTextField donorAgeField = new JTextField();
            donorAgeField.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 4;
            gbc.gridwidth = 3;
            donorPanel.add(donorAgeField, gbc);

            JLabel donorGenderLabel = new JLabel("Donor Gender:");
            donorGenderLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            donorPanel.add(donorGenderLabel, gbc);

            JRadioButton maleButton = new JRadioButton("Male");
            JRadioButton femaleButton = new JRadioButton("Female");
            maleButton.setFont(font);
            femaleButton.setFont(font);
            ButtonGroup genderGroup = new ButtonGroup();
            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
            JPanel genderPanel = new JPanel();
            genderPanel.add(maleButton);
            genderPanel.add(femaleButton);
            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 3;
            donorPanel.add(genderPanel, gbc);

            JLabel donorAddressLabel = new JLabel("Donor Address:");
            donorAddressLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            donorPanel.add(donorAddressLabel, gbc);

            JTextField donorAddressField = new JTextField();
            donorAddressField.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 6;
            gbc.gridwidth = 3;
            donorPanel.add(donorAddressField, gbc);

            JLabel donorEmailLabel = new JLabel("Donor Email:");
            donorEmailLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            donorPanel.add(donorEmailLabel, gbc);

            JTextField donorEmailField = new JTextField();
            donorEmailField.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 7;
            gbc.gridwidth = 3;
            donorPanel.add(donorEmailField, gbc);

            JLabel bloodTypeLabel = new JLabel("Blood Type:");
            bloodTypeLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.gridwidth = 1;
            donorPanel.add(bloodTypeLabel, gbc);

            JComboBox<String> bloodTypeComboBox = new JComboBox<>(new String[]{
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
            });
            bloodTypeComboBox.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 8;
            gbc.gridwidth = 3;
            donorPanel.add(bloodTypeComboBox, gbc);

            JLabel donationDateLabel = new JLabel("Donation Date:");
            donationDateLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 9;
            gbc.gridwidth = 1;
            donorPanel.add(donationDateLabel, gbc);

            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            datePicker.setFont(font);
            gbc.gridx = 1;
            gbc.gridy = 9;
            gbc.gridwidth = 3;
            donorPanel.add(datePicker, gbc);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 10;
            gbc.gridwidth = 4;
            donorPanel.add(confirmButton, gbc);


            // ActionListener for NRC Confirm Button
            nrcConfirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String donorNRC = nrcPart1ComboBox.getSelectedItem() + "/" + nrcPart2ComboBox.getSelectedItem() + "(" + nrcPart3ComboBox.getSelectedItem() + ")" + nrcTextField.getText();
                    	
                    try {
                        // Check NRC with server
                        String[] donorInfo = bloodBankServer.nrc_check(donorNRC);
                        //donationHistoryButton.addActionListener(new ActionListener() {
                        	donationHistoryButton.addActionListener(new ActionListener() {
                        	    public void actionPerformed(ActionEvent e) {
                        	        try {
                        	            // Fetch donation history from the server
                        	            // String donorNRC = "your_donor_nrc"; // Replace with actual donor NRC
                        	            String donationHistory = bloodBankServer.getDonationHistory(donorNRC);

                        	            // Create a JTextArea with the fetched donation history
                        	            JTextArea textArea = new JTextArea();
                        	            textArea.setText("Donation History:\n" + donationHistory);
                        	            textArea.setFont(new Font("Arial", Font.PLAIN, 25)); // Larger font size
                        	            textArea.setEditable(false);
                        	            textArea.setLineWrap(true);
                        	            textArea.setWrapStyleWord(true);

                        	            // Add the JTextArea to a JScrollPane
                        	            JScrollPane scrollPane = new JScrollPane(textArea);
                        	            scrollPane.setPreferredSize(new Dimension(400, 300));

                        	            // Create the custom OK button with a larger size
                        	            JButton okButton = new JButton("OK");
                        	            okButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
                        	            okButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Larger font size

                        	            // Create a panel for the OK button
                        	            JPanel buttonPanel = new JPanel();
                        	            buttonPanel.add(okButton);

                        	            // Create the custom JOptionPane panel
                        	            JPanel customPanel = new JPanel(new BorderLayout());
                        	            customPanel.add(scrollPane, BorderLayout.CENTER);
                        	            customPanel.add(buttonPanel, BorderLayout.SOUTH);

                        	            // Create a JDialog to show the custom JOptionPane
                        	            JDialog dialog = new JDialog(frame, "Donation History", true);
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
                        	            JOptionPane.showMessageDialog(frame, "Failed to retrieve donation history.", "Error", JOptionPane.ERROR_MESSAGE);
                        	        }
                        	    }
                        	});

                        if (donorInfo.length > 0) {
                            donorNameField.setText(donorInfo[0]);
                            donorPhoneField.setText(donorInfo[1]);
                            donorAgeField.setText(donorInfo[2]);
                            if ("Male".equals(donorInfo[3])) {
                                maleButton.setSelected(true);
                            } else if ("Female".equals(donorInfo[3])) {
                                femaleButton.setSelected(true);
                            }
                            donorAddressField.setText(donorInfo[4]);
                            donorEmailField.setText(donorInfo[5]);
                            bloodTypeComboBox.setSelectedItem(donorInfo[7]);
                            //String donationHistory = bloodBankServer.getDonationHistory(donorNRC);
                            //donationHistoryLabel.setText("Donation History: \n" + donationHistory);
                            

                            
                        }
                        
                        
                        cardLayout.show(panel, "DonorDetail");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // ActionListener for Donor Confirm Button
            confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        String donorName = donorNameField.getText();
                        String donorPhone = donorPhoneField.getText();
                        String donorAge = donorAgeField.getText();
                        String donorGender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
                        String donorAddress = donorAddressField.getText();
                        String donorEmail = donorEmailField.getText();
                        String bloodType = bloodTypeComboBox.getSelectedItem().toString();
                        String donationDate = datePicker.getJFormattedTextField().getText();
                        String donorNRC = nrcPart1ComboBox.getSelectedItem() + "/" + nrcPart2ComboBox.getSelectedItem() + "(" + nrcPart3ComboBox.getSelectedItem() + ")" + nrcTextField.getText();
                        String result = bloodBankServer.blooddonate(bloodType,donorName, donorPhone,donationDate,donorAge, donorGender, donorAddress, donorEmail,donorNRC);
                        //System.out.println(donorNRC+donorName+donorPhone+donorAge+donorGender+donorAddress);
                     // Create the HTML formatted message
                        String htmlMessage = "<html><body style='font-size:20px;'>" + result + "</body></html>";

                        // Create a JLabel to display the HTML message
                        JLabel messageLabel = new JLabel(htmlMessage);

                        // Create the custom OK button with a larger size
                        JButton okButton = new JButton("OK");
                        okButton.setPreferredSize(new Dimension(100, 50)); // Set desired size
                        okButton.setFont(new Font("Arial", Font.PLAIN, 20)); // Larger font size

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



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Add panels to the card layout
            panel.add(nrcPanel, "NRC");
            panel.add(donorPanel, "DonorDetail");

            // Show the NRC panel first
            cardLayout.show(panel, "NRC");

            // Add main panel to the frame
            frame.add(panel);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
