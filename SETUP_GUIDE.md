# 🌱 GreenLedger - Subsidy Management System
## Complete Setup & Implementation Guide

### Project Overview
GreenLedger is a comprehensive Java Swing application designed to manage agricultural subsidies for rural afforestation programs. It tracks farmer registration, tree planting records, subsidy calculations, and fraud detection.

---

## ✅ NEW FEATURES ADDED

### 1. **Database Schema with Demo Data**
- Complete MySQL database schema with 20 demo farmers
- 100+ demo tree records across farmers
- 20 subsidy records with various statuses
- 5 demo user accounts with different roles
- Audit logging system

### 2. **Enhanced Security**
- User authentication system with role-based access
- AuthenticationManager for session management
- Support for multiple user roles (Admin, Officer, Staff)
- Logout functionality

### 3. **Improved Input Validation**
- InputValidator utility class with 15+ validation methods
- Comprehensive error messages
- Real-time field validation
- Regex-based pattern matching

### 4. **Better UI/UX**
- Enhanced MainMenu with user info panel
- Improved LoginScreen with loading feedback
- Advanced SearchFarmer with multiple filter options
- Better error notifications with emoji indicators
- System statistics panel

### 5. **Code Organization**
- DBConnection with proper error handling
- AuthenticationManager for session management
- DatabaseInitializer for easy data setup
- Utility classes for common operations

---

## 🚀 INSTALLATION & SETUP

### Prerequisites
- Java 8 or higher
- MySQL Server (5.7 or higher)
- NetBeans IDE (or any Java IDE)
- MySQL JDBC Driver (included in project)

### Step 1: Create Database

```bash
# Open MySQL command line or MySQL Workbench
mysql -u root -p

# Run the SQL script
source C:\path\to\GreenLedger_1\database_schema.sql
```

Or copy the entire contents of `database_schema.sql` and run it in MySQL Workbench.

### Step 2: Populate Demo Data

There are two ways to populate demo data:

#### Option A: Using DatabaseInitializer (Recommended)
```bash
# Compile the project
javac -cp ".:lib/*" src/*.java

# Run the DatabaseInitializer
java -cp "src:lib/*" DatabaseInitializer
```

#### Option B: Manual SQL Execution
Just execute the `database_schema.sql` file which includes all the demo data.

### Step 3: Verify Configuration

Open `DBConnection.java` and verify:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/greenledger";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "admin123"; // Change if needed
```

Update credentials if your MySQL setup is different.

### Step 4: Run the Application

```bash
# Compile
javac -cp ".:lib/*" src/*.java

# Run
java -cp "src:lib/*" LoginScreen
```

---

## 👤 LOGIN CREDENTIALS

### Demo Accounts:
| Username | Password | Role | Access |
|----------|----------|------|--------|
| admin | admin123 | ADMIN | Full system access |
| officer1 | officer123 | OFFICER | Officer functions |
| staff1 | staff123 | STAFF | Limited staff access |

---

## 📊 DEMO DATA INCLUDED

### Farmers: 20 Records
- Farmer IDs: 101-120
- Villages: Panchpur, Nandpur, Greenville, Woodland, Forestville
- Email & Bank account details included

### Trees: 100+ Records
- Tree Types: Neem, Mango, Sal, Teak, Eucalyptus, Ashoka, Peepul, Guava, Jamun, Sheesham, Ber, Tamarind
- Plant Dates: January to June 2024
- GPS Coordinates: North India region (Latitude: 25.3-25.6, Longitude: 82.5-82.9)

### Subsidies: 20 Records
- Statuses: DISBURSED, PARTIALLY_DISBURSED, APPROVED, PENDING
- Amounts: ₹30,000 to ₹70,000 based on tree count

### Users: 3 Records
- Admin, Officer, and Staff accounts

---

## 🎯 KEY IMPROVEMENTS

### 1. Authentication System
- Database-driven user authentication
- Session management with AuthenticationManager
- Role-based access control

### 2. Input Validation
```java
// InputValidator provides methods for:
- isValidFarmerId(String)
- isValidName(String)
- isValidAadhaar(String)
- isValidPhone(String)
- isValidEmail(String)
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
