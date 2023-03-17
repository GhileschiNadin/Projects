import java.sql.SQLException;

public class Customer {
   private int customerId;
   private String customerName;
   private String phoneNumber;
   private String passCustomer;

    public Customer(int customerId, String customerName, String phoneNumber, String passCustomer) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.passCustomer = passCustomer;
    }

    public Customer() {

    }
    public static Customer getById(int id) throws SQLException {
        Database db= new Database();
        return db.getCustomerById(id);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassCustomer() {
        return passCustomer;
    }

    public void setPassCustomer(String passCustomer) {
        this.passCustomer = passCustomer;
    }
}
