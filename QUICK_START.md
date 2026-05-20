# Quick Start - Get It Running Fast

Just want to run this thing? Here's how to get it working in a few minutes.

## Before You Start

Make sure you have:
- Java 8 or higher
- MySQL installed and running
- MySQL connector in lib/ folder

## Step 1: Database Setup

Open MySQL and run the database_schema.sql file. This creates everything you need (tables + demo data):

```bash
mysql -u root -p < database_schema.sql
```

Or just copy-paste the SQL file contents into MySQL Workbench and execute it.

## Step 2: Compile

```bash
cd src
javac -encoding UTF-8 -cp ".:../lib/mysql-connector-java-8.0.33.jar" *.java
```

If you get compilation errors, make sure you're in the `src` folder.

## Step 3: Run the App

```bash
java -cp ".:../lib/mysql-connector-java-8.0.33.jar" LoginScreen
```

The login window should pop up.

## Step 4: Login

Use these credentials:
- Username: `admin`
- Password: `admin123`

That's it! You're in.

## Demo Accounts

If you want to test different roles:
- admin / admin123 (everything)
- officer1 / officer123 (farmer management)
- staff1 / staff123 (search only)

## What to Try

1. **Search Farmer** - Click search, try farmer ID 101 or search by name
2. **View Trees** - See all the tree records I added as demo data
3. **Check Subsidy** - Calculate subsidy for any farmer
4. **Fraud Report** - See what the fraud detection caught
5. **Audit Log** - Check the history of all changes
6. **Export Data** - Export farmers/trees to CSV

## Demo Data

The database comes preloaded with:
- 20 farmers (IDs 101-120)
- 100+ tree records
- 20 subsidy records
- Real audit logs showing changes

Try searching for any farmer from 101-120.

## Troubleshooting

**"Database connection failed"**
- Check if MySQL is running
- Verify credentials in DBConnection.java

**"Compilation error"**
- Use UTF-8 encoding: `javac -encoding UTF-8`
- Make sure you're in the src/ folder

**"No data showing"**
- Make sure database_schema.sql was executed
- Check MySQL for the greenledger database

**"Can't find classes"**
- Verify mysql-connector.jar is in lib/ folder
- Check the -cp path in compilation/run commands

## Next Steps

- Read SETUP_GUIDE.md for more detailed info
- Check out the actual Java code to see how it works
- Try adding a new farmer or tree record
- Explore the fraud report feature

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
