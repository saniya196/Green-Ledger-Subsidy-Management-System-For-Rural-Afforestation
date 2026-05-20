# Setup Guide - Detailed Instructions

Complete setup instructions for GreenLedger.

## What You Need

- Java 8 or higher
- MySQL 5.7 or higher
- MySQL JDBC driver (already in lib/ folder)
- About 30 minutes to set everything up

## Step 1: Create the Database

Open MySQL command line or MySQL Workbench and run:

```bash
mysql -u root -p < database_schema.sql
```

Or copy the entire `database_schema.sql` file content and execute it in MySQL Workbench.

This creates:
- All 6 tables (Users, Farmer, Tree, Subsidy, AuditLog, FraudDetection)
- 11 database triggers
- 20 demo farmers with realistic data
- 100+ tree records with GPS coordinates

## Step 2: Update Database Credentials

Open `DBConnection.java` in the src folder and check these lines:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/greenledger";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "admin123";
```

If your MySQL is set up differently (different username/password), update these values.

## Step 3: Compile the Project

Navigate to the src folder and run:

```bash
cd src
javac -encoding UTF-8 -cp ".:../lib/mysql-connector-java-8.0.33.jar" *.java
```

The UTF-8 encoding is important because the code has emoji characters.

If compilation fails:
- Make sure you're in the src folder
- Check that mysql-connector-java-8.0.33.jar exists in ../lib/
- Make sure Java is in your PATH

## Step 4: Run the Application

```bash
java -cp ".:../lib/mysql-connector-java-8.0.33.jar" LoginScreen
```

The login window should appear.

## Demo Accounts

Three test accounts are already in the database:

| Username | Password | What they can do |
|----------|----------|-------------------|
| admin | admin123 | Everything - full access |
| officer1 | officer123 | Manage farmers, view audit logs |
| staff1 | staff123 | Search and view data only |

## Demo Data Included

### Farmers: 20 Records (IDs 101-120)
- Name: Rajesh Kumar Singh, Priya Sharma, Mohan Lal, etc.
- Villages: Panchpur, Nandpur, Greenville, Woodland, Forestville
- Real email addresses and phone numbers

### Trees: 100+ Records
- 12 different tree species (Neem, Mango, Sal, Teak, etc.)
- Each farmer has 5-7 trees
- Real GPS coordinates from North India region
- Various statuses (Active, Inactive, Dead, etc.)

### Subsidies: 20 Records
- Different statuses (PENDING, APPROVED, DISBURSED, REJECTED, ON_HOLD)
- Amounts calculated based on tree count
- Calculation: 5-15 trees = ₹5000, 16-30 = ₹15000, 31+ = ₹30000

### Users: 3 Accounts
- Admin account for full system access
- Officer account for field management
- Staff account for basic operations

## What to Test First

Try these things to verify everything works:

1. **Login** - Use admin/admin123
2. **Search** - Look for farmer 101 (Rajesh Kumar)
3. **View Trees** - See all trees for that farmer
4. **Subsidy** - Calculate subsidy for farmer 101
5. **Fraud Report** - Check what fraud detection found
6. **Audit Log** - See history of changes

## Key Files Explained

| File | What it does |
|------|-------------|
| DBConnection.java | Manages database connections |
| AuthenticationManager.java | Handles login and user sessions |
| InputValidator.java | Checks all user input before saving |
| DatabaseInitializer.java | Loads demo data (run once) |
| LoginScreen.java | First window you see |
| MainMenu.java | Main dashboard after login |
| FarmerForm.java | Add new farmers |
| TreeForm.java | Add new trees |
| SearchFarmer.java | Search with filters |
| SubsidyCalculator.java | Calculate subsidies |
| FraudReport.java | View fraud detection |
| AuditLogScreen.java | View all changes |
| ViewData.java | View all records and export to CSV |

## Database Schema

### Users Table
- Stores login accounts
- Stores user role (ADMIN, OFFICER, STAFF)

### Farmer Table
- Farmer ID, name, village
- Aadhaar number, phone, email
- Land area in hectares

### Tree Table
- Tree ID, farmer ID
- Tree type (species)
- Plant date, status
- GPS coordinates (latitude, longitude)

### Subsidy Table
- Subsidy ID, farmer ID
- Status (PENDING, APPROVED, DISBURSED, etc.)
- Amount in rupees
- Tree count for that farmer

### AuditLog Table
- Tracks every INSERT, UPDATE, DELETE
- Records who made the change and when
- Stores old and new values

### FraudDetection Table
- Stores fraud alerts
- Types: Duplicate trees, excessive planting, invalid coordinates
- Severity level (LOW, MEDIUM, HIGH)
- Date when detected

## Database Triggers (11 Total)

### Audit Triggers (6)
- When tree is added → log it
- When tree is updated → log it
- When tree is deleted → log it
- When farmer is added → log it
- When farmer is updated → log it
- When subsidy is added → log it

### Fraud Detection (3)
- Duplicate tree check → flags same farmer + same tree type
- Excessive planting → flags farmer with 10+ trees
- Invalid coordinates → flags GPS outside expected range

### Subsidy Updates (2)
- When tree added → recalculate subsidy amount
- When tree deleted → recalculate subsidy amount

## Troubleshooting

### Problem: "Database connection failed"
**Solution:**
- Check if MySQL is running on your machine
- Verify username and password in DBConnection.java match your MySQL setup
- Make sure the greenledger database exists
- Try manually connecting to MySQL to test

### Problem: "Compilation error - unmappable character"
**Solution:**
- This means UTF-8 encoding is missing
- Use: `javac -encoding UTF-8 -cp ...`
- Make sure you have the correct command

### Problem: "Cannot find symbol: class DBConnection"
**Solution:**
- Make sure you're in the src folder when compiling
- Run: `cd src` first
- Then run javac command

### Problem: "No data showing in tables"
**Solution:**
- Verify database_schema.sql was executed completely
- Check MySQL for greenledger database and 6 tables
- Make sure no error messages appeared during SQL execution
- Try connecting to MySQL directly to verify data is there

### Problem: "Login fails with correct credentials"
**Solution:**
- Check that Users table has data
- Verify the demo accounts exist in database
- Try manually querying: `SELECT * FROM Users;`
- Make sure DBConnection credentials match your MySQL setup

### Problem: "Class not found" errors
**Solution:**
- Verify mysql-connector-java-8.0.33.jar is in lib/ folder
- Check the -cp path includes the correct path to jar
- Make sure you're not missing the jar extension

## Performance Notes

- The app runs locally on your machine
- Database is on localhost:3306 by default
- 20 farmers + 100+ trees is enough for testing
- For production, consider:
  - Increasing data size
  - Adding database indexes
  - Implementing connection pooling

## Security Notes

For actual use:
- Change default passwords for demo accounts
- Store database credentials in config file, not in code
- Use password hashing instead of plain text
- Implement session timeout
- Add SSL connection to database
- Change database username/password from root/admin123

## Next Steps

1. Follow steps 1-4 above
2. Login and explore with demo data
3. Try adding new farmers and trees
4. Check fraud report and audit logs
5. Export data to CSV
6. Read the code and modify as needed
- isValidLatitude(String)
- isValidLongitude(String)
- And more...
```

### 3. Enhanced Search
- Search by Farmer ID
- Search by Farmer Name
- Search by Village
- Search by Tree Type
- Displays farmer info alongside tree records

### 4. Better Error Handling
- Connection pooling in DBConnection
- Proper exception handling with logging
- User-friendly error messages
- Error recovery mechanisms

### 5. Improved Farmer Form
- Uses InputValidator for all validations
- Better error messages with indicators (✓, ✗)
- Automatic focus management
- Clear visual feedback

---

## 📁 FILE STRUCTURE

```
GreenLedger_1/
├── src/
│   ├── DatabaseInitializer.java      (NEW - Populates demo data)
│   ├── InputValidator.java            (NEW - Input validation utility)
│   ├── AuthenticationManager.java      (NEW - User authentication)
│   ├── DBConnection.java              (ENHANCED - Better error handling)
│   ├── LoginScreen.java               (ENHANCED - Uses AuthenticationManager)
│   ├── MainMenu.java                  (ENHANCED - Logout & user info)
│   ├── SearchFarmer.java              (ENHANCED - Advanced filters)
│   ├── FarmerForm.java                (ENHANCED - Uses InputValidator)
│   ├── TreeForm.java
│   ├── SubsidyCalculator.java
│   ├── ViewData.java
│   ├── UpdateDelete.java
│   ├── FraudReport.java
│   ├── AuditLogScreen.java
│   └── AboutScreen.java
├── database_schema.sql                (NEW - Complete DB schema + demo data)
├── SETUP_GUIDE.md                     (NEW - This file)
└── lib/
    └── mysql-connector-java-*.jar
```

---

## 🧪 TESTING THE APPLICATION

### Test Scenarios:

1. **Login Test**
   - Use: admin / admin123
   - Should see MainMenu with current user info

2. **Farmer Search Test**
   - Search by Farmer ID: 101
   - Should show 5 tree records for Rajesh Kumar Singh

3. **Validation Test**
   - Try adding farmer with invalid ID (e.g., "abc")
   - Should show error message

4. **Fraud Detection Test**
   - Go to Fraud Report
   - Should show analysis of suspicious records

5. **Subsidy Calculation Test**
   - Enter Farmer ID: 104
   - Should calculate based on tree count

---

## 🔐 Security Notes

### Important:
1. **Production Use**: Implement password hashing (use bcrypt)
2. **Database Credentials**: Store in configuration file, not hardcoded
3. **SQL Injection**: Use PreparedStatements (already implemented)
4. **Session Timeout**: Implement automatic logout after inactivity
5. **Audit Logging**: All operations are logged in AuditLog table

---

## 🐛 TROUBLESHOOTING

### Database Connection Error
```
Problem: "Database connection failed"
Solution: 
1. Check if MySQL is running
2. Verify credentials in DBConnection.java
3. Ensure 'greenledger' database exists
4. Check MySQL port (default: 3306)
```

### Authentication Fails
```
Problem: "Invalid username or password"
Solution:
1. Use exact credentials from above table
2. Check database has Users table
3. Verify DatabaseInitializer was run
```

### Validation Error
```
Problem: "Aadhaar must be exactly 12 digits"
Solution:
1. Enter full 12-digit Aadhaar number
2. Check InputValidator.java for all rules
3. Use demo data to test
```

---

## 📚 API REFERENCE

### DBConnection
```java
Connection con = DBConnection.getConnection();  // Get connection
DBConnection.closeConnection(con);              // Close connection
boolean status = DBConnection.testConnection(); // Test connectivity
```

### InputValidator
```java
InputValidator.isValidFarmerId(id);
InputValidator.isValidAadhaar(aadhaar);
InputValidator.isValidPhone(phone);
String error = InputValidator.getErrorMessage(field, value);
```

### AuthenticationManager
```java
AuthenticationManager.authenticate(username, password);
AuthenticationManager.User user = AuthenticationManager.getCurrentUser();
AuthenticationManager.logout();
boolean isAdmin = AuthenticationManager.isAdmin();
```

---

## 📈 Future Enhancements

1. Advanced Reporting with Charts
2. PDF Export Functionality
3. Email Notifications
4. Dashboard with Analytics
5. Mobile App Integration
6. API for Third-party Integration
7. Two-Factor Authentication
8. Advanced Audit Trail Analytics

---

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review database schema
3. Verify demo data was loaded correctly
4. Check application logs in console

---

## 📝 Version History

**Version 1.0.0 (Current)**
- Initial release with complete demo data
- Enhanced security and validation
- Improved UI/UX
- Advanced search capabilities

---

## ✨ Enjoy using GreenLedger! 🌱

This application demonstrates best practices in Java Swing development with a complete demonstration of a real-world agricultural management system.
