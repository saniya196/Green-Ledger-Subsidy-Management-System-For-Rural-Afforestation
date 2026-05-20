# 🚀 Quick Start Guide - Enhanced Demo Data

## One-Command Setup

### Windows Command Prompt
```batch
cd src
javac DatabaseInitializer.java
java DatabaseInitializer
```

### Linux/Mac Terminal
```bash
cd src
javac DatabaseInitializer.java
java DatabaseInitializer
```

## What Happens

```
✓ Connects to MySQL database "greenledger"
✓ Creates tables if they don't exist
✓ Inserts 150 farmers
✓ Plants 350+ trees
✓ Creates 150 subsidy records with varied statuses
✓ Logs 100+ audit entries
✓ Displays login credentials
```

## Login Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |
| Officer | officer1 | officer123 |
| Staff | staff1 | staff123 |

## Demo Data Breakdown

| Entity | Count | Details |
|--------|-------|---------|
| Farmers | 150 | 5 villages, varied land sizes |
| Trees | 350+ | 20 species, 4-9 per farmer |
| Subsidies | 150 | 6 statuses, ₹40K-₹90K each |
| Audit Logs | 100+ | 90 days of activity |
| Villages | 5 | Panchpur, Nandpur, Greenville, Woodland, Forestville |

## Test Scenarios

### 1. Search for Farmer
- Username: `admin` / Password: `admin123`
- Click "🔍 Search"
- Enter any farmer name like "Rajesh" or village "Panchpur"
- See results with tree count and subsidy status

### 2. View Subsidy Status
- Click "📋 View Records"
- See 150 farmers with subsidy pipeline:
  - 30 DISBURSED (fully paid)
  - 30 PARTIALLY_DISBURSED (50-80%)
  - 30 APPROVED (awaiting payment)
  - 30 PENDING (under review)
  - 15 REJECTED (fraud cases)
  - 15 ON_HOLD (documentation)

### 3. Audit Trail
- Click "History" tab
- See 100+ entries showing:
  - Who did what
  - When it happened
  - Before/after values
  - User role

### 4. Tree Analysis
- Click "🌳 View Trees"
- See 350+ trees across all farmers
- Filter by status: ACTIVE, INACTIVE, DEAD, TRANSPLANTED
- Sort by plant date to see plantation timeline

## Database Queries (MySQL)

### Total Subsidy Amount
```sql
SELECT SUM(eligible_amount) FROM Subsidy;
-- Result: ~₹15,00,000 - ₹18,00,000
```

### Farmers by Village
```sql
SELECT village, COUNT(*) FROM Farmer GROUP BY village;
```

### Subsidy Status Distribution
```sql
SELECT status, COUNT(*) FROM Subsidy GROUP BY status;
```

### Recent Activity
```sql
SELECT * FROM AuditLog ORDER BY action_timestamp DESC LIMIT 10;
```

## Troubleshooting

### Error: "Cannot connect to database"
- Ensure MySQL is running
- Check database name is "greenledger"
- Verify username/password in DBConnection.java

### Error: "DatabaseInitializer.java not found"
- Navigate to the `src/` directory first
- Run: `cd src && javac DatabaseInitializer.java`

### Error: "No database selected"
- Run database_schema.sql first:
```bash
mysql -u root -p < database_schema.sql
```

### Import conflicts or compilation issues
- Ensure Java 8 or higher is installed
- Check if DBConnection.java exists in src/

## Next Steps

1. **Explore the Data** - Use the GUI to search and filter
2. **Generate Reports** - Use the reporting features
3. **Test Workflows** - Try updating farmer info
4. **Check Audit Trail** - Verify all changes are logged
5. **Customize** - Add more farmers or modify statuses

## File Reference

| File | Purpose |
|------|---------|
| DatabaseInitializer.java | Populates demo data |
| database_schema.sql | Creates tables |
| DEMO_DATA_README.md | Full documentation |
| ENHANCEMENTS_SUMMARY.md | Detailed improvements |
| QUICK_START.md | This file |

## Support

For issues or customization:
- Check DEMO_DATA_README.md for detailed docs
- Review ENHANCEMENTS_SUMMARY.md for changes
- Examine database_schema.sql for table structure
- Debug with sample SQL queries above

---

**Ready to run!** Execute the command above and start exploring your enhanced GreenLedger system! 🌱
