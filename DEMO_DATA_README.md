# 🌱 GreenLedger Demo Data Enhancement

## Overview
Enhanced demo database with comprehensive realistic data for testing and demonstration of the GreenLedger subsidy management system.

## 📊 Data Statistics

### Farmers: **150 Records**
- Spread across 5 villages: Panchpur, Nandpur, Greenville, Woodland, Forestville
- Land holdings: 1.5 - 4.5 hectares
- Varied demographics with authentic Indian names
- Complete contact information (phone, email, Aadhaar, bank accounts)

### Trees: **350+ Records**
- Average 4-9 trees per farmer
- 20 different tree species:
  - Timber: Teak, Sal, Sheesham, Deodar
  - Fruit: Mango, Guava, Jamun, Mulberry
  - Sacred/Medicinal: Neem, Ashoka, Peepul, Arjun
  - Others: Eucalyptus, Ber, Tamarind, Kikar, Khair, Palash, Chikhalwali, Peepal
- Plantation dates spread across realistic periods (Jun 2023 - May 2024)
- GPS coordinates mapped to rural areas
- Tree status variations: ACTIVE, INACTIVE, DEAD, TRANSPLANTED

### Subsidies: **150 Records**
- Realistic disbursement statuses:
  - **DISBURSED (20%)**: Fully paid out farmers
  - **PARTIALLY_DISBURSED (20%)**: 50-80% disbursement pending
  - **APPROVED (20%)**: Waiting for disbursement
  - **PENDING (20%)**: Under review
  - **REJECTED (10%)**: Disqualified/fraudulent cases
  - **ON_HOLD (10%)**: Awaiting additional documentation
- Eligible amounts: ₹40,000 - ₹90,000 per farmer (₹10,000/tree)
- Varied disbursement dates over 90-day period
- Realistic approval workflows

### Audit Logs: **100 Records**
- Multi-user activity tracking
- 3 user roles with different access levels:
  - Admin (user_id: 1)
  - Officer (user_id: 2)
  - Staff (user_id: 3)
- Activity types: INSERT, UPDATE, DELETE
- Timestamped entries spanning 90 days
- Complete before/after values for audit trail

### Users: **3 Records**
- **Admin**: Full system access (username: admin, password: admin123)
- **Officer**: Review & approval authority (username: officer1, password: officer123)
- **Staff**: Data entry & support (username: staff1, password: staff123)

## 🚀 How to Use

### Step 1: Database Setup
```bash
# Run the database schema creation
mysql -u root -p < database_schema.sql
```

### Step 2: Load Demo Data
```bash
# Compile and run the DatabaseInitializer
javac DatabaseInitializer.java
java DatabaseInitializer
```

The initializer will:
- Connect to MySQL
- Create all necessary tables (if not exists)
- Populate with 150 farmers + 350+ trees + 150 subsidy records + 100 audit logs
- Display initialization status and login credentials

### Step 3: Launch Application
```bash
# Start the application GUI
java MainMenu
```

## 📋 Testing Scenarios

### Data Validation
- ✓ Unique Aadhaar numbers (verified)
- ✓ Unique phone numbers (verified)
- ✓ Valid email formats
- ✓ Bank account format consistency
- ✓ GPS coordinates within valid ranges

### Reporting Capabilities
- **Subsidy Status Dashboard**: Visualize disbursement progress
- **Farmer Demographics**: Analyze by village, land area
- **Tree Plantation Analysis**: Track by species, date, status
- **Audit Trail**: Complete user activity history
- **Fraud Detection**: Identify suspicious patterns

### Search & Filter Tests
- Search farmers by village
- Filter trees by species and status
- Find subsidy records by status
- Sort farmers by land area
- Date range filters on audit logs

## 🔧 Customization

### Add More Farmers
Edit `insertDemoFarmers()` method:
```java
{151, "New Farmer Name", "Village", "Aadhaar", "Phone", "Email", LandArea, "BankAccount"}
```

### Add Tree Species
Update `treeTypes` array in `insertDemoTrees()`:
```java
String[] treeTypes = {"Neem", "Mango", ..., "YourTreeName"};
```

### Adjust Subsidy Statuses
Modify the switch case in `insertDemoSubsidies()` to change ratio of statuses.

## 📊 Sample Queries

```sql
-- Total subsidy amount eligible
SELECT SUM(eligible_amount) as total_eligible FROM Subsidy;

-- Average disbursement rate
SELECT AVG((disbursed_amount/eligible_amount)*100) as avg_disbursement_pct FROM Subsidy;

-- Farmers by village
SELECT village, COUNT(*) as farmer_count, AVG(land_area_hectares) as avg_land FROM Farmer GROUP BY village;

-- Tree species distribution
SELECT tree_type, COUNT(*) as count FROM Tree GROUP BY tree_type ORDER BY count DESC;

-- Pending subsidy approval
SELECT f.name, f.village, s.tree_count, s.eligible_amount FROM Farmer f 
JOIN Subsidy s ON f.farmer_id = s.farmer_id 
WHERE s.status = 'PENDING';

-- Recent audit activity
SELECT u.full_name, a.action, a.table_name, a.action_timestamp 
FROM AuditLog a 
JOIN Users u ON a.user_id = u.user_id 
ORDER BY a.action_timestamp DESC LIMIT 10;
```

## 📈 Performance Notes

- Database optimized with indexes on:
  - farmer_id
  - village
  - phone number
  - tree plant_date
  - subsidy status
  - audit timestamp

- Suitable for demonstrations with realistic data volume
- Can be extended to 500+ farmers for stress testing

## 🔐 Security Notes

- Demo passwords are weak (for testing only)
- Change credentials before production use
- Audit logs track all modifications
- Data includes realistic fraud scenarios (REJECTED/ON_HOLD statuses)

## 📝 Data Quality

All data has been:
- ✓ Validated for consistency
- ✓ Checked for realistic distributions
- ✓ Verified for referential integrity
- ✓ Formatted for standard Indian conventions
- ✓ Tested for UI/UX display

---

**Version**: 1.0  
**Last Updated**: May 2026  
**Database**: MySQL 5.7+  
**Language**: Java + Swing GUI
