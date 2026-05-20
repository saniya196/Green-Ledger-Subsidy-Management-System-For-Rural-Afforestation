import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseInitializer - Populates GreenLedger database with demo data
 * Run this once to setup the database with realistic sample data
 */
public class DatabaseInitializer {

    private Connection con;

    public DatabaseInitializer() {
        this.con = DBConnection.getConnection();
    }

    public static void main(String[] args) {
        DatabaseInitializer initializer = new DatabaseInitializer();
        
        if (initializer.con != null) {
            try {
                System.out.println("Starting database initialization...");
                
                // Clear existing data (optional - comment out if you want to keep existing data)
                // initializer.clearExistingData();
                
                // Insert demo data
                initializer.insertDemoUsers();
                System.out.println("✓ Demo users inserted");
                
                initializer.insertDemoFarmers();
                System.out.println("✓ Demo farmers inserted");
                
                initializer.insertDemoTrees();
                System.out.println("✓ Demo trees inserted");
                
                initializer.insertDemoSubsidies();
                System.out.println("✓ Demo subsidy records inserted");
                
                initializer.insertDemoAuditLogs();
                System.out.println("✓ Demo audit logs inserted");
                
                System.out.println("\n===== DATABASE INITIALIZATION COMPLETE =====");
                System.out.println("Login credentials:");
                System.out.println("  Admin: username=admin, password=admin123");
                System.out.println("  Officer: username=officer1, password=officer123");
                System.out.println("  Staff: username=staff1, password=staff123");
                System.out.println("==========================================\n");
                
            } catch (Exception e) {
                System.out.println("Error during initialization: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to connect to database!");
        }
    }

    private void clearExistingData() throws SQLException {
        String[] tables = {"AuditLog", "Subsidy", "Tree", "Farmer", "Users"};
        for (String table : tables) {
            String sql = "TRUNCATE TABLE " + table;
            try (Statement st = con.createStatement()) {
                st.executeUpdate(sql);
            }
        }
        System.out.println("✓ Existing data cleared");
    }

    private void insertDemoUsers() throws SQLException {
        String sql = "INSERT IGNORE INTO Users (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        Object[][] users = {
            {"admin", "admin123", "System Administrator", "ADMIN"},
            {"officer1", "officer123", "Rajesh Kumar - Officer", "OFFICER"},
            {"staff1", "staff123", "Priya Singh - Staff", "STAFF"}
        };

        for (Object[] user : users) {
            ps.setString(1, (String) user[0]);
            ps.setString(2, (String) user[1]);
            ps.setString(3, (String) user[2]);
            ps.setString(4, (String) user[3]);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    private void insertDemoFarmers() throws SQLException {
        String sql = "INSERT IGNORE INTO Farmer (farmer_id, name, village, aadhaar_no, phone, email, land_area_hectares, bank_account) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        Object[][] farmers = {
            // Original 20 farmers (Enhanced with varied data)
            {101, "Rajesh Kumar Singh", "Panchpur", "123456789012", "9876543210", "rajesh.singh@email.com", 2.5, "1234567890"},
            {102, "Priya Sharma", "Nandpur", "123456789013", "9876543211", "priya.sharma@email.com", 3.0, "1234567891"},
            {103, "Mohan Lal Yadav", "Panchpur", "123456789014", "9876543212", "mohan.yadav@email.com", 1.5, "1234567892"},
            {104, "Sunita Devi", "Greenville", "123456789015", "9876543213", "sunita.devi@email.com", 4.0, "1234567893"},
            {105, "Vikram Singh", "Nandpur", "123456789016", "9876543214", "vikram.singh@email.com", 2.0, "1234567894"},
            {106, "Anjali Patel", "Panchpur", "123456789017", "9876543215", "anjali.patel@email.com", 3.5, "1234567895"},
            {107, "Rohit Kumar", "Woodland", "123456789018", "9876543216", "rohit.kumar@email.com", 2.8, "1234567896"},
            {108, "Meera Singh", "Greenville", "123456789019", "9876543217", "meera.singh@email.com", 1.8, "1234567897"},
            {109, "Deepak Verma", "Nandpur", "123456789020", "9876543218", "deepak.verma@email.com", 3.2, "1234567898"},
            {110, "Seema Gupta", "Panchpur", "123456789021", "9876543219", "seema.gupta@email.com", 2.2, "1234567899"},
            {111, "Arun Mehta", "Forestville", "123456789022", "9876543220", "arun.mehta@email.com", 4.5, "1234567900"},
            {112, "Kavya Nair", "Greenville", "123456789023", "9876543221", "kavya.nair@email.com", 2.0, "1234567901"},
            {113, "Suresh Rao", "Woodland", "123456789024", "9876543222", "suresh.rao@email.com", 3.8, "1234567902"},
            {114, "Leela Bhat", "Nandpur", "123456789025", "9876543223", "leela.bhat@email.com", 2.5, "1234567903"},
            {115, "Naveen Joshi", "Panchpur", "123456789026", "9876543224", "naveen.joshi@email.com", 1.9, "1234567904"},
            {116, "Ravi Kumar", "Greenville", "123456789027", "9876543225", "ravi.kumar@email.com", 3.3, "1234567905"},
            {117, "Pooja Malhotra", "Forestville", "123456789028", "9876543226", "pooja.malhotra@email.com", 2.7, "1234567906"},
            {118, "Arjun Singh", "Woodland", "123456789029", "9876543227", "arjun.singh@email.com", 4.1, "1234567907"},
            {119, "Divya Nath", "Panchpur", "123456789030", "9876543228", "divya.nath@email.com", 2.3, "1234567908"},
            {120, "Karan Desai", "Nandpur", "123456789031", "9876543229", "karan.desai@email.com", 3.6, "1234567909"},
            
            // Additional 30 farmers for richer demo data
            {121, "Sheetal Verma", "Panchpur", "234567890123", "9765432100", "sheetal.verma@email.com", 2.1, "1234567910"},
            {122, "Harish Patel", "Greenville", "234567890124", "9765432101", "harish.patel@email.com", 3.9, "1234567911"},
            {123, "Gita Kumari", "Woodland", "234567890125", "9765432102", "gita.kumari@email.com", 2.6, "1234567912"},
            {124, "Bhavesh Singh", "Nandpur", "234567890126", "9765432103", "bhavesh.singh@email.com", 4.2, "1234567913"},
            {125, "Rita Sharma", "Forestville", "234567890127", "9765432104", "rita.sharma@email.com", 3.1, "1234567914"},
            {126, "Ashok Yadav", "Panchpur", "234567890128", "9765432105", "ashok.yadav@email.com", 2.4, "1234567915"},
            {127, "Swati Singh", "Greenville", "234567890129", "9765432106", "swati.singh@email.com", 3.7, "1234567916"},
            {128, "Mahesh Kumar", "Woodland", "234567890130", "9765432107", "mahesh.kumar@email.com", 1.9, "1234567917"},
            {129, "Nisha Gupta", "Nandpur", "234567890131", "9765432108", "nisha.gupta@email.com", 3.5, "1234567918"},
            {130, "Rajiv Chopra", "Panchpur", "234567890132", "9765432109", "rajiv.chopra@email.com", 2.8, "1234567919"},
            {131, "Anita Reddy", "Greenville", "234567890133", "9765432110", "anita.reddy@email.com", 4.3, "1234567920"},
            {132, "Sanjay Nair", "Forestville", "234567890134", "9765432111", "sanjay.nair@email.com", 2.2, "1234567921"},
            {133, "Priyanka Singh", "Woodland", "234567890135", "9765432112", "priyanka.singh@email.com", 3.4, "1234567922"},
            {134, "Vikram Patel", "Nandpur", "234567890136", "9765432113", "vikram.patel@email.com", 2.9, "1234567923"},
            {135, "Manisha Devi", "Panchpur", "234567890137", "9765432114", "manisha.devi@email.com", 3.8, "1234567924"},
            {136, "Suresh Kumar", "Greenville", "234567890138", "9765432115", "suresh.kumar@email.com", 2.3, "1234567925"},
            {137, "Kavita Rao", "Woodland", "234567890139", "9765432116", "kavita.rao@email.com", 4.1, "1234567926"},
            {138, "Nikhil Singh", "Forestville", "234567890140", "9765432117", "nikhil.singh@email.com", 2.7, "1234567927"},
            {139, "Rani Kumari", "Panchpur", "234567890141", "9765432118", "rani.kumari@email.com", 3.2, "1234567928"},
            {140, "Prakash Verma", "Nandpur", "234567890142", "9765432119", "prakash.verma@email.com", 2.5, "1234567929"},
            {141, "Shilpa Sharma", "Greenville", "234567890143", "9765432120", "shilpa.sharma@email.com", 3.6, "1234567930"},
            {142, "Daljeet Singh", "Woodland", "234567890144", "9765432121", "daljeet.singh@email.com", 4.0, "1234567931"},
            {143, "Pooja Verma", "Nandpur", "234567890145", "9765432122", "pooja.verma@email.com", 2.4, "1234567932"},
            {144, "Vikas Yadav", "Panchpur", "234567890146", "9765432123", "vikas.yadav@email.com", 3.3, "1234567933"},
            {145, "Anjum Khan", "Forestville", "234567890147", "9765432124", "anjum.khan@email.com", 2.8, "1234567934"},
            {146, "Ramesh Gupta", "Greenville", "234567890148", "9765432125", "ramesh.gupta@email.com", 3.9, "1234567935"},
            {147, "Lalita Singh", "Woodland", "234567890149", "9765432126", "lalita.singh@email.com", 2.2, "1234567936"},
            {148, "Sumit Nair", "Nandpur", "234567890150", "9765432127", "sumit.nair@email.com", 4.4, "1234567937"},
            {149, "Priya Yadav", "Panchpur", "234567890151", "9765432128", "priya.yadav@email.com", 3.0, "1234567938"},
            {150, "Hemant Reddy", "Greenville", "234567890152", "9765432129", "hemant.reddy@email.com", 2.6, "1234567939"}
        };

        for (Object[] farmer : farmers) {
            ps.setInt(1, (Integer) farmer[0]);
            ps.setString(2, (String) farmer[1]);
            ps.setString(3, (String) farmer[2]);
            ps.setString(4, (String) farmer[3]);
            ps.setString(5, (String) farmer[4]);
            ps.setString(6, (String) farmer[5]);
            ps.setDouble(7, (Double) farmer[6]);
            ps.setString(8, (String) farmer[7]);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    private void insertDemoTrees() throws SQLException {
        String sql = "INSERT IGNORE INTO Tree (farmer_id, tree_type, plant_date, latitude, longitude, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        String[] treeTypes = {"Neem", "Mango", "Sal", "Teak", "Eucalyptus", "Ashoka", "Peepul", "Guava", 
                              "Jamun", "Sheesham", "Ber", "Tamarind", "Peepal", "Arjun", "Palash", "Deodar",
                              "Mulberry", "Chikhalwali", "Kikar", "Khair"};
        LocalDate baseDate = LocalDate.of(2023, 6, 1);

        int treeCount = 0;
        // Trees for farmers 101-150 (first 50 farmers)
        for (int farmerId = 101; farmerId <= 150; farmerId++) {
            int treesPerFarmer = 4 + (farmerId % 6);  // 4-9 trees per farmer
            
            for (int i = 0; i < treesPerFarmer; i++) {
                ps.setInt(1, farmerId);
                ps.setString(2, treeTypes[(farmerId + i + treeCount) % treeTypes.length]);
                
                // Spread plantations across different months realistically
                LocalDate plantDate = baseDate.plusDays((treeCount * 3) % 365);
                ps.setDate(3, Date.valueOf(plantDate));
                
                // Realistic GPS coordinates around rural areas
                ps.setDouble(4, 25.2 + (farmerId % 30) * 0.05 + (i * 0.01));
                ps.setDouble(5, 82.8 + (farmerId % 40) * 0.04 - (i * 0.015));
                
                // Varied status with some inactive/dead trees for realism
                String status = "ACTIVE";
                if (treeCount % 20 == 0) status = "INACTIVE";
                else if (treeCount % 25 == 0) status = "DEAD";
                else if (treeCount % 15 == 0) status = "TRANSPLANTED";
                
                ps.setString(6, status);
                ps.addBatch();
                treeCount++;
            }
        }
        ps.executeBatch();
        ps.close();
    }

    private void insertDemoSubsidies() throws SQLException {
        String sql = "INSERT IGNORE INTO Subsidy (farmer_id, tree_count, eligible_amount, disbursed_amount, status, approval_date, disbursement_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        for (int farmerId = 101; farmerId <= 150; farmerId++) {
            int treeCount = 4 + (farmerId % 6);  // 4-9 trees per farmer
            double eligibleAmount = treeCount * 10000;  // ₹10,000 per tree
            double disbursedAmount = 0;
            String status;
            Date approvalDate = null;
            Date disbursementDate = null;

            // Create realistic subsidy scenarios with varied statuses
            int statusType = farmerId % 10;
            switch (statusType) {
                case 0:
                case 1:
                    // Fully disbursed (20% of farmers)
                    status = "DISBURSED";
                    disbursedAmount = eligibleAmount;
                    approvalDate = Date.valueOf(LocalDate.now().minusDays(30 + farmerId % 20));
                    disbursementDate = Date.valueOf(LocalDate.now().minusDays(15 + farmerId % 25));
                    break;
                case 2:
                case 3:
                    // Partially disbursed (20% of farmers)
                    status = "PARTIALLY_DISBURSED";
                    disbursedAmount = eligibleAmount * (0.5 + (farmerId % 3) * 0.15);  // 50-80%
                    approvalDate = Date.valueOf(LocalDate.now().minusDays(20 + farmerId % 15));
                    disbursementDate = Date.valueOf(LocalDate.now().minusDays(10 + farmerId % 20));
                    break;
                case 4:
                case 5:
                    // Approved but not disbursed (20% of farmers)
                    status = "APPROVED";
                    disbursedAmount = 0;
                    approvalDate = Date.valueOf(LocalDate.now().minusDays(5 + farmerId % 10));
                    break;
                case 6:
                case 7:
                    // Pending approval (20% of farmers)
                    status = "PENDING";
                    disbursedAmount = 0;
                    approvalDate = null;
                    break;
                case 8:
                    // Rejected (10% of farmers)
                    status = "REJECTED";
                    disbursedAmount = 0;
                    approvalDate = Date.valueOf(LocalDate.now().minusDays(25 + farmerId % 30));
                    break;
                default:
                    // On Hold (10% of farmers)
                    status = "ON_HOLD";
                    disbursedAmount = 0;
                    approvalDate = null;
            }

            ps.setInt(1, farmerId);
            ps.setInt(2, treeCount);
            ps.setDouble(3, eligibleAmount);
            ps.setDouble(4, disbursedAmount);
            ps.setString(5, status);
            ps.setDate(6, approvalDate);
            ps.setDate(7, disbursementDate);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }

    private void insertDemoAuditLogs() throws SQLException {
        String sql = "INSERT INTO AuditLog (user_id, action, table_name, record_id, old_value, new_value) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        String[] actions = {"INSERT", "UPDATE", "DELETE"};
        String[] tables = {"Farmer", "Tree", "Subsidy", "Farmer", "Subsidy"};
        int[] userIds = {2, 2, 3, 2, 3, 2};  // Mix of officer1 (2) and staff1 (3)

        LocalDate baseDate = LocalDate.now().minusDays(90);

        // Create 100 realistic audit log entries spanning 90 days
        for (int i = 0; i < 100; i++) {
            int userIdx = i % userIds.length;
            int tableIdx = i % tables.length;
            String action = actions[i % 3];
            String table = tables[tableIdx];
            int recordId = 101 + (i % 50);
            
            String oldValue = null;
            String newValue = "";
            
            if (action.equals("INSERT")) {
                newValue = table + " record #" + recordId + " created on " + baseDate.plusDays(i);
            } else if (action.equals("UPDATE")) {
                oldValue = "Previous value for " + table + " #" + recordId;
                newValue = "Updated to new value on " + baseDate.plusDays(i);
            } else {
                oldValue = table + " record #" + recordId + " deleted";
                newValue = null;
            }

            ps.setInt(1, userIds[userIdx]);
            ps.setString(2, action);
            ps.setString(3, table);
            ps.setInt(4, recordId);
            ps.setString(5, oldValue);
            ps.setString(6, newValue);
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
    }
}
