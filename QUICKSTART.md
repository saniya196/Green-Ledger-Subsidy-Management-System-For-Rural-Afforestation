# 🚀 GreenLedger - Quick Start Guide

## ⚡ 5-Minute Setup

### 1. Create Database
```bash
# Open MySQL terminal
mysql -u root -p

# Copy & paste entire contents of database_schema.sql
# Or run:
source database_schema.sql;
```

### 2. Run Application
```bash
# In your IDE (NetBeans):
1. Open project: GreenLedger_1
2. Build project: Shift + F11
3. Run: F6 or click Run button
4. LoginScreen will launch automatically
```

### 3. Login
- Username: `admin`
- Password: `admin123`

### 4. Start Using!
- Click "Add Farmer" to add new farmers
- Click "Search" to find existing farmers
- Click "View Records" to see all trees
- Check "Fraud Report" for fraud detection

---

## 📊 What's Included

✅ 20 Demo Farmers with complete details  
✅ 100+ Tree records with GPS coordinates  
✅ Subsidy tracking (₹30k - ₹70k per farmer)  
✅ User authentication system  
✅ Advanced search by Farmer/Village/Tree type  
✅ Fraud detection analysis  
✅ Audit logging  
✅ Real-time validation  

---

## 🎮 Key Features to Try

### Search Functionality
- Go to "🔍 Search" button
- Try searching by:
  - Farmer ID: 101
  - Farmer Name: Rajesh
  - Village: Panchpur
  - Tree Type: Neem

### Subsidy Calculator
- Click "💰 Subsidy Check"
- Enter Farmer ID: 101
- See calculated subsidy based on trees

### Fraud Report
- Click "🚨 Fraud Report"
- View system statistics
- See fraud alerts

### View All Data
- Click "📋 View Records"
- See all 100+ tree records
- Check plant dates and locations

---

## 👥 More Demo Users

Try these other accounts:

| Login | Password | Role |
|-------|----------|------|
| officer1 | officer123 | Officer |
| staff1 | staff123 | Staff |

---

## ✨ What's New

**NEW Utility Classes:**
- `InputValidator.java` - Validates all input
- `AuthenticationManager.java` - Handles user sessions
- `DatabaseInitializer.java` - Populates demo data

**ENHANCED Files:**
- `DBConnection.java` - Better error handling
- `LoginScreen.java` - Proper authentication
- `MainMenu.java` - Logout & user info
- `SearchFarmer.java` - Advanced filters
- `FarmerForm.java` - Better validation

---

## 📋 Demo Data Overview

### 20 Farmers
From villages: Panchpur, Nandpur, Greenville, Woodland, Forestville

### 12 Tree Types
Neem, Mango, Sal, Teak, Eucalyptus, Ashoka, Peepul, Guava, Jamun, Sheesham, Ber, Tamarind

### 4 User Roles
Admin, Officer, Staff, User

### Subsidy Statuses
- ✓ DISBURSED (₹ transferred)
- ⬆ PARTIALLY_DISBURSED (partial amount)
- ◯ APPROVED (approved, pending transfer)
- ⏳ PENDING (under review)

---

## 🔧 Troubleshooting

**Can't login?**
- Ensure MySQL is running
- Check database was created
- Use exact credentials: admin / admin123

**"Database connection failed"?**
- Open DBConnection.java
- Verify MySQL host, port, database name
- Ensure 'greenledger' database exists

**No data showing?**
- Run DatabaseInitializer.java
- Or manually execute database_schema.sql

---

## 💡 Tips

1. **Search by Farmer ID 101-120** - All have demo data
2. **Use emoji buttons** for quick navigation
3. **Check Audit Log** to see all system actions
4. **View fraud reports** to understand patterns
5. **Test with different roles** using demo accounts

---

## 📞 Need Help?

1. Read the full SETUP_GUIDE.md
2. Check console for error messages
3. Verify all files are in src/ folder
4. Ensure MySQL JDBC driver is in lib/ folder

---

## 🎉 You're Ready!

The application is fully functional with demo data. Explore all features and enjoy the enhanced GreenLedger system!

**Happy Farming! 🌱**
