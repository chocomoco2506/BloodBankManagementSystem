import BloodBank.BloodBankServerPOA;
import java.sql.*;
import java.util.ArrayList;
import org.omg.CORBA.ORB;

public class BloodBankServerImpl extends BloodBankServerPOA {
    private static final Connection PreparedStatement = null;
	private ORB orb;
    private Connection con;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    public BloodBankServerImpl() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank", "root", "123456");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getInventory() {
        ArrayList<String> inventoryList = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String query = "SELECT bloodtype, total FROM Inventory ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String bloodType = rs.getString("bloodtype");
                int total = rs.getInt("total");
                inventoryList.add( bloodType + "," + total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryList.toArray(new String[0]);
    }
    public String[] getInventoryHistory() {
        ArrayList<String> inventoryHistory = new ArrayList<>();
        try {
            // Connection conn = DriverManager.getConnection("jdbc:your_database_url", "username", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
            	    "SELECT b.bloodid, b.bloodtype, b.donorid, d.donorname, b.donation_date, b.receiverid, r.receivername, b.receive_date, b.status " +
            	    "FROM blood b " +
            	    "JOIN donor d ON b.donorid = d.donorid " +
            	    "LEFT JOIN receiver r ON b.receiverid = r.receiverid"
            	);


            while (rs.next()) {
                String bloodid = rs.getString("bloodid") != null ? rs.getString("bloodid") : "";
                String bloodtype = rs.getString("bloodtype") != null ? rs.getString("bloodtype") : "";
                String donorid = rs.getString("donorid") != null ? rs.getString("donorid") : "";
                String donorname = rs.getString("donorname") != null ? rs.getString("donorname") : "";
                String donation_date = rs.getString("donation_date") != null ? rs.getString("donation_date") : "";
                String receiverid = rs.getString("receiverid") != null ? rs.getString("receiverid") : "NULL";
                String receivername = rs.getString("receivername") != null ? rs.getString("receivername") : "NULL";
                String receive_date = rs.getString("receive_date") != null ? rs.getString("receive_date") : "NULL";
                String status = rs.getString("status") != null ? rs.getString("status") : "";

                String record = String.join(",", bloodid, bloodtype, donorid, donorname, donation_date, receiverid,receivername,receive_date, status);
                inventoryHistory.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryHistory.toArray(new String[0]);
    }







    public String getDonationHistory(String donorNRC) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT donation_date FROM Donor JOIN blood ON Donor.donorid = blood.donorid WHERE nrc = ?");
            ps.setString(1, donorNRC.trim());
            ResultSet rs = ps.executeQuery();
            StringBuilder history = new StringBuilder();
            int totalDonations = 0;

            while (rs.next()) {
                totalDonations++;
                history.append(totalDonations).append(". ").append(rs.getString("donation_date")).append("\n");
            }

            if (totalDonations == 0) {
                return "No donation history found.";
            } else {
                return "Your total donation time is " + totalDonations + "(\n" + history.toString() + ")";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "<html>Donation failed.</html>";
        }
    }


    public String[] bloodsearch(String bloodtype) {
        ArrayList<String> results = new ArrayList<>();
        
        try {
            // Step 1: Update the status of the blood donations based on the donation date
            String sql = "UPDATE Blood " +
                         "SET status = CASE " +
                         "WHEN DATEDIFF(CURRENT_DATE, donation_date) > 42 AND receiverid IS NULL THEN 'expired' " +
                         "ELSE 'new' END";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

            // Step 2: Update the inventory count based on the new status of blood donations
         // Step 2: Update the inventory count based on the current status of blood donations
            String sql2 = "UPDATE Inventory i " +
                          "LEFT JOIN ( " +
                          "    SELECT bloodtype, COUNT(*) AS count " +
                          "    FROM Blood " +
                          "    WHERE receiverid IS NULL AND status = 'new' " +
                          "    GROUP BY bloodtype " +
                          ") b ON i.bloodtype = b.bloodtype " +
                          "SET i.total = IFNULL(b.count, 0)";

            stmt.executeUpdate(sql2);


            // Step 3: Search for available blood that matches the given blood type
            PreparedStatement ps = con.prepareStatement(
                "SELECT b.bloodid, b.donorid, d.donorname, b.donation_date, b.status " +
                "FROM Blood b " +
                "JOIN Donor d ON b.donorid = d.donorid " +
                "WHERE b.bloodtype = ? AND b.receiverid IS NULL AND b.status != 'expired'");
            ps.setString(1, bloodtype);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                results.add(rs.getInt("bloodid") + " , " + rs.getInt("donorid") + " , " + rs.getString("donorname") + ", " + rs.getString("donation_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results.toArray(new String[0]);
    }





    public String bloodrequest(String blood_id, String receiver_name, String receiver_phone, String receive_date,
                               String receiver_age, String receiver_gender, String receiver_address, String receiver_email) {
        try {
            int bloodId = Integer.parseInt(blood_id.trim());

            // Insert new receiver information
            receiver_name = receiver_name.trim();
            receiver_phone = receiver_phone.trim();
            receive_date = receive_date.trim();
            receiver_age = receiver_age.trim();
            receiver_gender = receiver_gender.trim();
            receiver_address = receiver_address.trim();
            receiver_email = receiver_email.trim();
            //receiver_nrc = receiver_nrc.trim();
            PreparedStatement insertReceiver = con.prepareStatement(
                "INSERT INTO Receiver (receivername, phone, age, gender, address, email) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            insertReceiver.setString(1, receiver_name);
            insertReceiver.setString(2, receiver_phone);
            insertReceiver.setString(3, receiver_age);
            insertReceiver.setString(4, receiver_gender);
            insertReceiver.setString(5, receiver_address);
            insertReceiver.setString(6, receiver_email);
            //insertReceiver.setString(7, receiver_nrc);
            insertReceiver.executeUpdate();

            // Get generated receiverid
            ResultSet rs = insertReceiver.getGeneratedKeys();
            rs.next();
            int receiverId = rs.getInt(1);

            // Update blood with receiver information
            PreparedStatement ps = con.prepareStatement(
                "UPDATE Blood SET receiverid = ?, receive_date = ? WHERE bloodid = ?");
            ps.setInt(1, receiverId);
            ps.setString(2, receive_date);
            ps.setInt(3, bloodId);
            ps.executeUpdate();

            // Get the blood type for the requested blood
            ps = con.prepareStatement("SELECT bloodtype FROM Blood WHERE bloodid = ?");
            ps.setInt(1, bloodId);
            rs = ps.executeQuery();
            rs.next();
            String bloodtype = rs.getString("bloodtype");

            // Decrement the inventory count for the requested blood type
            ps = con.prepareStatement("UPDATE Inventory SET total = total - 1 WHERE bloodtype = ?");
            ps.setString(1, bloodtype);
            ps.executeUpdate();

            return "Your request is successful for blood ID " + bloodId;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Error: Invalid blood ID format.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process the request.";
        }
    }

    public String blooddonate(String bloodtype, String donor_name, String donor_phone, String donation_date,
            String donor_age, String donor_gender, String donor_address, String donor_email,
            String donor_nrc) {
    	try {
		// Check if the donor already exists
		PreparedStatement checkDonor = con.prepareStatement("SELECT donorid FROM Donor WHERE nrc = ?");
		checkDonor.setString(1, donor_nrc.trim());
		ResultSet rs = checkDonor.executeQuery();
		
		int donorId;
		if (rs.next()) {
		// Donor exists, use their donorid
		donorId = rs.getInt("donorid");
		} else {
		// Donor does not exist, insert new donor and get the generated donorid
		PreparedStatement insertDonor = con.prepareStatement(
		  "INSERT INTO Donor (donorname, phone, age, gender, address, email, nrc, bloodtype) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
		  Statement.RETURN_GENERATED_KEYS);
		insertDonor.setString(1, donor_name.trim());
		insertDonor.setString(2, donor_phone.trim());
		insertDonor.setString(3, donor_age.trim());
		insertDonor.setString(4, donor_gender.trim());
		insertDonor.setString(5, donor_address.trim());
		insertDonor.setString(6, donor_email.trim());
		insertDonor.setString(7, donor_nrc.trim());
		insertDonor.setString(8, bloodtype.trim());
		insertDonor.executeUpdate();
		
		ResultSet generatedKeys = insertDonor.getGeneratedKeys();
		if (generatedKeys.next()) {
		  donorId = generatedKeys.getInt(1);
		} else {
		  throw new SQLException("Failed to obtain donor ID.");
		}
		}
		
		// Insert new blood donation record
		PreparedStatement insertBlood = con.prepareStatement(
		"INSERT INTO blood (bloodtype, donorid, donation_date,status) VALUES (?, ?, ?,?)");
		insertBlood.setString(1, bloodtype.trim());
		insertBlood.setInt(2, donorId);
		insertBlood.setString(3, donation_date.trim());
		insertBlood.setString(4, "new");
		insertBlood.executeUpdate();
		
		// Increment the inventory count for the donated blood type
		PreparedStatement updateInventory = con.prepareStatement("UPDATE Inventory SET total = total + 1 WHERE bloodtype = ?");
		updateInventory.setString(1, bloodtype.trim());
		updateInventory.executeUpdate();
		/*String sql = "UPDATE Blood " +
                "SET status = CASE " +
                "WHEN DATEDIFF(CURRENT_DATE, donation_date) > 42 AND receiverid IS NULL THEN 'expired' " +
                "ELSE 'new' END";


    // Create a Statement object
    Statement stmt = con.createStatement();

    // Execute the update statement
    stmt.executeUpdate(sql);
    
    String sql2 = "UPDATE Inventory i " +
            "JOIN ( " +
            "    SELECT bloodtype, COUNT(*) AS count " +
            "    FROM blood " +
            "    WHERE receiverid IS NULL AND status = 'new' " +
            "    GROUP BY bloodtype " +
            ") b ON i.bloodtype = b.bloodtype " +
            "SET i.total = b.count";



// Create a Statement object
Statement stmt1 = con.createStatement();

// Execute the update statement
stmt1.executeUpdate(sql2);*/
		return "Thank you for giving blood ";
		
		} catch (SQLException e) {
		e.printStackTrace();
		return "Donation failed.";
		}
		}

    

    public String[] nrc_check(String donor_nrc) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT donorname, phone, age, gender, address, email, nrc,bloodtype FROM Donor WHERE nrc = ?");
            ps.setString(1, donor_nrc.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new String[]{
                    rs.getString("donorname"),
                    rs.getString("phone"),
                    rs.getString("age"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("nrc"),
                    rs.getString("bloodtype"),
                    
                };
            } else {
                return new String[0];
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
