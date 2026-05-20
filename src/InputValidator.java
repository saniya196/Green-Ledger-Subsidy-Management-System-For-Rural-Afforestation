import java.util.regex.Pattern;

/**
 * InputValidator - Centralized input validation utility
 * Provides methods to validate various input formats used in the application
 */
public class InputValidator {
    
    // Regex patterns
    private static final Pattern AADHAAR_PATTERN = Pattern.compile("[0-9]{12}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("[0-9]{10}");
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{2,100}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]+");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("[0-9]+(\\.[0-9]{1,2})?");
    
    /**
     * Validate if field is empty
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Validate farmer ID (must be numeric)
     */
    public static boolean isValidFarmerId(String farmerId) {
        if (isEmpty(farmerId)) {
            return false;
        }
        try {
            int id = Integer.parseInt(farmerId.trim());
            return id > 0 && id < 999999;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate farmer name
     */
    public static boolean isValidName(String name) {
        if (isEmpty(name)) {
            return false;
        }
        String trimmed = name.trim();
        return NAME_PATTERN.matcher(trimmed).matches() && trimmed.length() >= 2;
    }
    
    /**
     * Validate Aadhaar number (12 digits)
     */
    public static boolean isValidAadhaar(String aadhaar) {
        if (isEmpty(aadhaar)) {
            return false;
        }
        return AADHAAR_PATTERN.matcher(aadhaar.trim()).matches();
    }
    
    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }
    
    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return true; // Email is optional
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate land area in hectares
     */
    public static boolean isValidLandArea(String area) {
        if (isEmpty(area)) {
            return true; // Optional
        }
        try {
            double hectares = Double.parseDouble(area.trim());
            return hectares > 0 && hectares <= 100;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate tree ID
     */
    public static boolean isValidTreeId(String treeId) {
        if (isEmpty(treeId)) {
            return false;
        }
        try {
            int id = Integer.parseInt(treeId.trim());
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate tree type (non-empty, alphanumeric with spaces)
     */
    public static boolean isValidTreeType(String treeType) {
        if (isEmpty(treeType)) {
            return false;
        }
        String trimmed = treeType.trim();
        return trimmed.matches("[a-zA-Z ]{2,50}");
    }
    
    /**
     * Validate date format (YYYY-MM-DD)
     */
    public static boolean isValidDateFormat(String date) {
        if (isEmpty(date)) {
            return false;
        }
        return date.trim().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    }
    
    /**
     * Validate latitude (-90 to 90)
     */
    public static boolean isValidLatitude(String latitude) {
        if (isEmpty(latitude)) {
            return true; // Optional
        }
        try {
            double lat = Double.parseDouble(latitude.trim());
            return lat >= -90 && lat <= 90;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate longitude (-180 to 180)
     */
    public static boolean isValidLongitude(String longitude) {
        if (isEmpty(longitude)) {
            return true; // Optional
        }
        try {
            double lon = Double.parseDouble(longitude.trim());
            return lon >= -180 && lon <= 180;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate username
     */
    public static boolean isValidUsername(String username) {
        if (isEmpty(username)) {
            return false;
        }
        String trimmed = username.trim();
        return trimmed.length() >= 3 && trimmed.length() <= 50 && 
               trimmed.matches("[a-zA-Z0-9_]+");
    }
    
    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        // At least 6 characters
        return password.length() >= 6;
    }
    
    /**
     * Get error message for validation
     */
    public static String getErrorMessage(String fieldName, String value) {
        if (fieldName.equalsIgnoreCase("farmerId")) {
            return !isEmpty(value) && isValidFarmerId(value) ? "" : 
                   "Farmer ID must be a positive number";
        } else if (fieldName.equalsIgnoreCase("name")) {
            return !isEmpty(value) && isValidName(value) ? "" : 
                   "Name must contain only letters and spaces (min 2 characters)";
        } else if (fieldName.equalsIgnoreCase("aadhaar")) {
            return !isEmpty(value) && isValidAadhaar(value) ? "" : 
                   "Aadhaar must be exactly 12 digits";
        } else if (fieldName.equalsIgnoreCase("phone")) {
            return !isEmpty(value) && isValidPhone(value) ? "" : 
                   "Phone must be exactly 10 digits";
        } else if (fieldName.equalsIgnoreCase("email")) {
            return isValidEmail(value) ? "" : 
                   "Invalid email format";
        } else if (fieldName.equalsIgnoreCase("landArea")) {
            return isValidLandArea(value) ? "" : 
                   "Land area must be between 0 and 100 hectares";
        } else if (fieldName.equalsIgnoreCase("latitude")) {
            return isValidLatitude(value) ? "" : 
                   "Latitude must be between -90 and 90";
        } else if (fieldName.equalsIgnoreCase("longitude")) {
            return isValidLongitude(value) ? "" : 
                   "Longitude must be between -180 and 180";
        }
        return "";
    }
}
