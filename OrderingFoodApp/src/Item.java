import java.sql.SQLException;
import java.util.ArrayList;

public class Item {
    private int itemId;
    private String itemName;
    private String description;
    private double price;
    private int restaurantId;


    public Item(int itemId, String itemName, String description, double price, int restaurantId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public Item() {

    }

    public static Item getById(int id) throws SQLException {
        Database db= new Database();
        return db.getItemById(id);
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return
                " " + itemName + "     " +
                " " + description + "       " +
                " " + price;
    }
}
