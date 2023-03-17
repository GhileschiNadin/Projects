import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int restaurantId;
    private String restaurantName;
    private String phoneNumber;
    private String address;
    ArrayList<Integer> restaurantIds = new ArrayList<>();
    ArrayList<Restaurant> restaurantList = new ArrayList<>();

    public Restaurant(int restaurantId, String restaurantName, String phoneNumber, String address) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Restaurant() {

    }

    public void getRestaurantIds() throws SQLException {
        Database db = new Database();
        restaurantIds = db.getRestaurantIds();
    }

    public ArrayList<Restaurant> populateRestaurantList() throws SQLException {
        getRestaurantIds();
        for(int iteration : restaurantIds){
            restaurantList.add(getById(iteration));
        }
        return restaurantList;
    }

    public static Restaurant getById(int id) throws SQLException{
        Database db= new Database();
        return db.getRestaurantById(id);
     }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    @Override
    public String toString() {
        return  " " + restaurantName + "     " +
                " phoneNumber: " + phoneNumber + "     " +
                " adress: " + address + ' ';
    }
}
