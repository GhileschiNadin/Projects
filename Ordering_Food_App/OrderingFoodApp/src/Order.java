import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private int restaurantId;
    private double total_price;
    private LocalDateTime order_date;

    private ArrayList<Order> myOrders;

    public Order(int orderId, int customerId, int restaurantId, double total_price) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.total_price = total_price;
    }

    public Order(){

    }

    public ArrayList<Order> getMyOrders(int id) throws SQLException {
        Database db = new Database();
        myOrders=db.getOrderByCustomerId(id);
        return myOrders;
    }

    public static Order getById(int id) throws SQLException {
        Database db= new Database();
        return db.getOrderById(id);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public LocalDateTime getOrderDate(){
        return order_date=LocalDateTime.now();
    }


}
