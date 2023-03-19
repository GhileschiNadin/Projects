import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/orderingfoodapp?allowPublicKeyRetrieval=true";
    private static final String USERNAME = "Nadin";
    private static final String PASSWORD = "A@bMon29TNp";

    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public Restaurant getRestaurantById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Restaurants WHERE restaurant_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int restaurantId = resultSet.getInt("restaurant_id");
            String restaurantName = resultSet.getString("name");
            String restaurantAddress = resultSet.getString("address");
            String restaurantPhone = resultSet.getString("phone_number");
            return new Restaurant(restaurantId, restaurantName, restaurantPhone, restaurantAddress);
        } else {
            return null;
        }
    }

    public ArrayList<Integer> getRestaurantIds() throws SQLException{
        ArrayList<Integer> restaurantId = new ArrayList<>();
        String sql = "SELECT restaurant_id FROM Restaurants";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                restaurantId.add(rs.getInt("restaurant_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return restaurantId;
    }


    public Customer getCustomerById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customers WHERE customer_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int customerId = resultSet.getInt("customer_id");
            String customerName = resultSet.getString("name");
            String customerPhoneNumber = resultSet.getString("phone_number");
            String customerPass = resultSet.getString("pass_customer");
            return new Customer(customerId, customerName, customerPhoneNumber, customerPass);
        } else {
            return null;
        }
    }

    public Item getItemById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Items WHERE item_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int itemId = resultSet.getInt("item_id");
            int restaurantId = resultSet.getInt("restaurant_id");
            String itemName = resultSet.getString("name");
            String itemDescription = resultSet.getString("description");
            int itemPrice = resultSet.getInt("price");
            return new Item(itemId, itemName, itemDescription, itemPrice, restaurantId);
        } else {
            return null;
        }
    }

    public ArrayList<Item> getItemByRestaurantId(int id) throws SQLException {
        ArrayList<Item> itemList = new ArrayList<>();

        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Items WHERE restaurant_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int itemId = resultSet.getInt("item_id");
            int restaurantId = resultSet.getInt("restaurant_id");
            String itemName = resultSet.getString("name");
            String itemDescription = resultSet.getString("description");
            int itemPrice = resultSet.getInt("price");
            Item itm = new Item(itemId, itemName , itemDescription, itemPrice, restaurantId);
            itemList.add(itm);
        }
            return itemList;
        }

    public Order getOrderById(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders WHERE order_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            int customerId = resultSet.getInt("customer_id");
            int restaurantId = resultSet.getInt("restaurant_id");
            double totalPrice = resultSet.getDouble("total_ammount");
            return new Order(orderId, customerId, restaurantId, totalPrice);
        } else {
            return null;
        }
    }

    public ArrayList<OrderItem> getOrderItemById(int id) throws SQLException {
        ArrayList<OrderItem> orderItems=new ArrayList<>();
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM OrderItems WHERE order_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int orderItemId = resultSet.getInt("order_item_id");
            int orderId = resultSet.getInt("order_id");
            int itemId = resultSet.getInt("item_id");
            int quantity = resultSet.getInt("quantity");
            OrderItem orderItem = new OrderItem(orderItemId, orderId, itemId, quantity);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public Customer getUserByName(String user) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Customers WHERE name = ?");
        statement.setString(1,user);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            int customerId = resultSet.getInt("customer_id");
            String customerName = resultSet.getString("name");
            String customerPhoneNumber = resultSet.getString("phone_number");
            String customerPass = resultSet.getString("pass_customer");
            return new Customer(customerId, customerName, customerPhoneNumber, customerPass);
        }else{
            return null;
        }
    }

    //Does what it's supposed to do even tho it throws an exception
  public void addCustomer(String name, String address, String phone_number, String password) {
      try {
          Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
          PreparedStatement stmt = conn.prepareStatement("INSERT INTO Customers (name, address, phone_number, pass_customer) VALUES (?, ?, ?, ?)");
          stmt.setString(1, name);
          stmt.setString(2, address);
          stmt.setString(3, phone_number);
          stmt.setString(4, password);
          stmt.executeUpdate();
          conn.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

    public int addOrder(int customerId, int restaurantId, double totalAmount) throws SQLException {
        String sql = "INSERT INTO Orders (customer_id, restaurant_id, order_date, total_amount) VALUES (?, ?, ?, ?)";
        int orderId = -1;

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, customerId);
            statement.setInt(2, restaurantId);
            statement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            statement.setDouble(4, totalAmount);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return orderId;
    }

    public ArrayList<Order> getOrderByCustomerId(int id) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders WHERE customer_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            int customerId = resultSet.getInt("customer_id");
            int restaurantId = resultSet.getInt("restaurant_id");
            double totalPrice = resultSet.getDouble("total_amount");
            Order ord= new Order(orderId, customerId, restaurantId, totalPrice);
            orders.add(ord);
        }
        return orders;
    }

    public void addOrderItem(int orderId, int itemId, int quantity) {
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO OrderItems (order_id, item_id, quantity) VALUES (?, ?, ?)");
            stmt.setInt(1, orderId);
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}