import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class GUI {
    private static Restaurant restaurant;
    private static Customer thisUser;

    public static void setUser(String user) throws SQLException {
           Database db = new Database();
           thisUser = db.getUserByName(user);
    }

    public  static Customer getUser(){
        return thisUser;
    }

    public static void setRestaurant(Restaurant rs){
        restaurant = rs;
    }

    public static Restaurant getRestaurant(){
        return restaurant;
    }
    public static class AuthenticationPage extends JFrame implements ActionListener {
        JLabel userLabel, passwordLabel, message;
        JTextField userText;
        JPasswordField passwordText;
        JButton loginButton, signInButton;

        public AuthenticationPage() {
            setTitle("Authentication Page");
            setSize(350, 350);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);

            JPanel panel = new JPanel();
            panel.setLayout(null);

           // JLabel loginImage = new JLabel(new ImageIcon("path/to/image.png"));
            ImageIcon pic = new ImageIcon(getClass().getResource("resources/LogIn.png"));
            Image image = pic.getImage();
            Image scaledImage = image.getScaledInstance(90,75,Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel logInImage = new JLabel(scaledIcon);
            logInImage.setBounds(125, 30,90,75);
            panel.add(logInImage);

            userLabel = new JLabel("Username");
            userLabel.setBounds(10, 130, 80, 25);
            panel.add(userLabel);

            userText = new JTextField(20);
            userText.setBounds(100, 130, 165, 25);
            panel.add(userText);

            passwordLabel = new JLabel("Password");
            passwordLabel.setBounds(10, 160, 80, 25);
            panel.add(passwordLabel);

            passwordText = new JPasswordField();
            passwordText.setBounds(100, 160, 165, 25);
            panel.add(passwordText);

            loginButton = new JButton("Log In");
            loginButton.setBounds(70, 230, 80, 25);
            loginButton.addActionListener(this);
            panel.add(loginButton);

            signInButton = new JButton("Sign In");
            signInButton.setBounds(170, 230, 80, 25);
            signInButton.addActionListener(this);
            panel.add(signInButton);

            message = new JLabel();
            message.setBounds(10, 120, 300, 25);
            panel.add(message);

            add(panel);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                String user = userText.getText();
                String password = passwordText.getText();
                try {
                    setUser(user);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (isValidUser(user, password)) {
                    message.setText("Authentication successful");
                    dispose();
                    try {
                        new MainPage(user).show();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Username or password is wrong. Try again!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == signInButton) {
                dispose();
                new SignInPage().setVisible(true);
            }
        }

        public class SignInPage extends JFrame implements ActionListener {

            private JTextField usernameTextField;

            private JTextField adressTextField;
            private JTextField phoneNumberTextField;
            private JPasswordField passwordField;
            private JButton signInButton;

            public SignInPage() {

                setTitle("Sign In Page");
                setSize(350, 350);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setResizable(false);

                JPanel panel = new JPanel();
                panel.setLayout(null);

                JLabel usernameLabel = new JLabel("Username:");
                JLabel adressLabel = new JLabel("Adress:");
                JLabel phoneNumerLabel = new JLabel("Phone Number:");
                JLabel passwordLabel = new JLabel("Password:");
                usernameTextField = new JTextField(20);
                adressTextField = new JTextField(20);
                phoneNumberTextField = new JTextField(20);
                passwordField = new JPasswordField(20);
                signInButton = new JButton("Sign In");
                signInButton.addActionListener(this);


                ImageIcon pic = new ImageIcon(getClass().getResource("resources/LogIn.png"));
                Image image = pic.getImage();
                Image scaledImage = image.getScaledInstance(90,75,Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                JLabel logInImage = new JLabel(scaledIcon);
                logInImage.setBounds(125, 10,90,75);
                panel.add(logInImage);

                usernameLabel.setBounds(10, 100, 80, 25);
                panel.add(usernameLabel);

                usernameTextField.setBounds(140, 100, 165, 25);
                panel.add(usernameTextField);

                adressLabel.setBounds(10,130,80,25);
                panel.add(adressLabel);

                adressTextField.setBounds(140,130,165,25);
                panel.add(adressTextField);

                phoneNumerLabel.setBounds(10,160,100,25);
                panel.add(phoneNumerLabel);

                phoneNumberTextField.setBounds(140,160,165,25);
                panel.add(phoneNumberTextField);

                passwordLabel.setBounds(10,190,80,25);
                panel.add(passwordLabel);

                passwordField.setBounds(140,190,165,25);
                panel.add(passwordField);

                signInButton.setBounds(125, 260, 80, 25);
                panel.add(signInButton);

                add(panel);
                setVisible(true);
            }

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == signInButton) {

                    String username = usernameTextField.getText();
                    String adress = adressTextField.getText();
                    String phone_number = phoneNumberTextField.getText();
                    String password = new String(passwordField.getPassword());

                    boolean valid = checkCredentials(username);

                    if (valid) {
                        JOptionPane.showMessageDialog(this, "Username already taken. Please choose a different username.");
                        return;
                    }else{
                        if (username.isEmpty() || adress.isEmpty() || phone_number.isEmpty()  || password.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else {
                            Database db = new Database();
                            db.addCustomer(username, adress, phone_number, password);
                            JOptionPane.showMessageDialog(null, "Account created successfully!");
                            try {
                                setUser(username);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            MainPage mainPage = null;
                            try {
                                mainPage = new MainPage(username);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            mainPage.show();
                            dispose();
                        }
                    }
                }
            }

            private boolean checkCredentials(String username) {
                boolean isTaken = false;

                try{
                    String sql = "SELECT name FROM Customers";
                    Database db = new Database();
                    Connection conn = db.getConnection();
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1,username);
                    ResultSet result = statement.executeQuery();
                    if(result.next()){
                        String dbName=result.getString("name");
                        isTaken=dbName.equals(username);
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                return isTaken;
            }
        }

        private boolean isValidUser(String username, String password) {
            boolean isValid = false;
            try {
                String sql = "SELECT pass_customer FROM Customers WHERE name = ?";
                Database db = new Database();
                Connection conn = db.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    String dbPassword = result.getString("pass_customer");
                    isValid = dbPassword.equals(password);
                }
                statement.close();
                result.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return isValid;
        }
    }


    public static class MainPage {
        private JFrame frame;

        private JTabbedPane tabbedPanel;
        private JPanel restaurants, myOrders;
        private Menu menuRst;
        private String user;
        private ArrayList<Order> myOrdersList;

        private DefaultTableModel ordersModel;

        public MainPage(String user) throws SQLException {


            frame = new JFrame("Main Page");
            tabbedPanel = new JTabbedPane();
            restaurants = new JPanel();
            myOrders = new JPanel();

            frame.add(tabbedPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(550, 350);
            frame.setVisible(true);


            Restaurant rs = new Restaurant();
            ArrayList<Restaurant> rstList = new ArrayList<>();
            rstList = rs.populateRestaurantList();

            String[] columnNames = {"Restaurant Name", "Phone Number", "Address"};
            Object[][] data = new Object[rstList.size()][3];

            for (int i = 0; i < rstList.size(); i++) {
                data[i][0] = rstList.get(i).getRestaurantName();
                data[i][1] = rstList.get(i).getPhoneNumber();
                data[i][2] = rstList.get(i).getAddress();
            }

            JTable table = new JTable(data, columnNames);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            JScrollPane scrollRest = new JScrollPane(table);
            scrollRest.setPreferredSize(new Dimension(530, 330));
            restaurants.add(scrollRest);
            restaurants.revalidate();
            restaurants.repaint();

            ArrayList<Restaurant> finalRstList = rstList;
            menuRst = null;
            table.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selected = table.getSelectedRow();
                    try {
                        if (menuRst == null) {
                            menuRst = new Menu(finalRstList.get(selected));
                            setRestaurant(finalRstList.get(selected));
                        } else {
                            JOptionPane.showMessageDialog(null, "Your current order was deleted. You can only order from one restaurant at a time.");
                            menuRst.closeMenu();
                            menuRst = null;
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    menuRst.showMenu();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            Order order = new Order();
            myOrdersList = new ArrayList();
            myOrdersList = order.getMyOrders(getUser().getCustomerId());
            String[] columns = {"Order Number", "Restaurant Name", "totalAmount"};
            ordersModel = new DefaultTableModel(columns, 0);
            JTable tableOrders = new JTable(ordersModel);

            for (int i = 0; i < myOrdersList.size(); i++) {
                Database db = new Database();
                Restaurant rst = db.getRestaurantById(myOrdersList.get(i).getRestaurantId());
                Object[] row = {myOrdersList.get(i).getOrderId(), rst.getRestaurantName(), myOrdersList.get(i).getTotal_price()};
                ordersModel.addRow(row);
            }

            JScrollPane scrollOrders = new JScrollPane(tableOrders);
            scrollOrders.setPreferredSize(new Dimension(520, 230));
            myOrders.add(scrollOrders, BorderLayout.CENTER);

            JButton refresh = new JButton("Refresh");
            refresh.addActionListener(e -> {
                try {
                    updateMyOrders();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            myOrders.add(refresh, BorderLayout.SOUTH);
            tableOrders.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int selected = tableOrders.getSelectedRow();
                    int orderId = myOrdersList.get(selected).getOrderId();
                    try {
                        OrderItemsPage orderItemsPage = new OrderItemsPage(orderId);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });


            tabbedPanel.addTab("Restaurants", restaurants);
            tabbedPanel.addTab("My Orders ", myOrders);

        }

        public void show() {
            frame.setVisible(true);
        }


        public MainPage() {

        }

        public void updateMyOrders() throws SQLException {
            ordersModel.setRowCount(0);
            Order order = new Order();
            myOrdersList = new ArrayList();
            myOrdersList = order.getMyOrders(getUser().getCustomerId());
            for (int i = 0; i < myOrdersList.size(); i++) {
                Database db = new Database();
                Restaurant rst = db.getRestaurantById(myOrdersList.get(i).getRestaurantId());
                Object[] row = {myOrdersList.get(i).getOrderId(), rst.getRestaurantName(), myOrdersList.get(i).getTotal_price()};
                ordersModel.addRow(row);
            }
        }
    }

    public static class Menu implements ActionListener {
        private JFrame frame;
        private JButton addItem, viewOrder;
        private JComboBox amount;
        private JLabel amountLabel;

        private  ArrayList<Item> itemList = new ArrayList<>();
        private JTable table;
        private ArrayList<Item> itemsAdded = new ArrayList<>();
        private ArrayList<Integer> amountAdded = new ArrayList<>();

        public Menu(Restaurant restaurant) throws SQLException {
            frame = new JFrame("Menu" );
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 500);
            frame.setVisible(true);


            Database db = new Database();
            int id = restaurant.getRestaurantId();
            itemList=db.getItemByRestaurantId(id);

            String[] columnNames = {" Name ", " Description ", " Price "};
            Object[][] data = new Object[itemList.size()][3];

            for (int i = 0; i < itemList.size(); i++) {
                data[i][0] = itemList.get(i).getItemName();
                data[i][1] = itemList.get(i).getDescription();
                data[i][2] = itemList.get(i).getPrice();
            }

            table = new JTable(data, columnNames);
            JScrollPane scrollItem = new JScrollPane(table);
            scrollItem.setPreferredSize(new Dimension(590, 350));
            addItem = new JButton("Add Item to Order ");
            viewOrder = new JButton("View Order");
            String[] options = {"0","1","2","3","4","5","6","7","8","9","10"};
            amount = new JComboBox(options);
            amountLabel = new JLabel("Item amount");
            amount.setBounds(130,380,50,50);
            viewOrder.setBounds(220,380,150,50);
            viewOrder.addActionListener(this);
            addItem.setBounds(400, 380,150,50);
            addItem.addActionListener(this);
            amountLabel.setBounds(20,380,100,50);
            frame.add(viewOrder);
            frame.add(addItem);
            frame.add(amount);
            frame.add(amountLabel);
            frame.add(scrollItem);
            frame.revalidate();
            frame.repaint();
        }
        public Menu(){

        }
        public void showMenu(){
            frame.setVisible(true);
        }
        public void closeMenu(){
            frame.dispose();
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == addItem){
            int itemId = table.getSelectedRow();
            Item item= itemList.get(itemId);
            int itemAmount = amount.getSelectedIndex();
            itemsAdded.add(item);
            amountAdded.add(itemAmount);
            }
            if(e.getSource() == viewOrder){
                ViewOrder view = new ViewOrder(itemsAdded,amountAdded);
            }
        }
    }

    public static class ViewOrder extends JFrame implements ActionListener {
        private JLabel totalAmountLabel;
        private JTextField totalAmountText;
        private JTable tableView;
        private DefaultTableModel tableModel;
        private  JScrollPane scrollItem;
        private ArrayList<Item> itemList;
        private ArrayList<Integer> itemAmount;
        private JPanel totalPanel;
        private JButton placeOrder;
        public ViewOrder(ArrayList<Item> itemList, ArrayList<Integer> itemAmount) {
            setTitle("View Order");
            setPreferredSize(new Dimension(550,350));

            this.itemList = itemList;
            this.itemAmount = itemAmount;
            String[] columnNames = {"Item Name", "Price", "Amount"};
            tableModel = new DefaultTableModel(columnNames, 0);
            tableView = new JTable(tableModel);

            for (int i = 0; i < itemList.size(); i++) {
                Object[] row = {itemList.get(i).getItemName(), itemList.get(i).getPrice(), itemAmount.get(i)};
                tableModel.addRow(row);
            }

            scrollItem = new JScrollPane(tableView);
            getContentPane().add(scrollItem, BorderLayout.CENTER);

            totalAmountLabel = new JLabel("Total Amount:");
            totalAmountText = new JTextField();
            totalAmountText.setEditable(false);
            totalAmountText.setPreferredSize(new Dimension(50,30));
            totalPanel = new JPanel();
            totalPanel.add(totalAmountLabel);
            totalPanel.add(totalAmountText);


            JButton refreshButton = new JButton("Refresh");
            refreshButton.addActionListener(e -> updateTable());
            totalPanel.add(refreshButton);
            placeOrder = new JButton("Place Order");
            placeOrder.addActionListener( this);
            totalPanel.add(placeOrder);
            getContentPane().add(totalPanel, BorderLayout.SOUTH);
            calculateTotalAmount();

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(400, 300);
            setVisible(true);
        }

          public void updateTable(){
           tableModel.setRowCount(0);
              for (int i = 0; i < itemList.size(); i++) {
                  Object[] row = {itemList.get(i).getItemName(), itemList.get(i).getPrice(), itemAmount.get(i)};
                  tableModel.addRow(row);
              }
              calculateTotalAmount();
          }
          public void calculateTotalAmount(){
            int totalAmount=0;
              for (int i = 0; i < itemList.size(); i++) {
                int price = (int) itemList.get(i).getPrice();
                  int amount = itemAmount.get(i);
                  totalAmount += price * amount;
              }
              totalAmountText.setText(Integer.toString(totalAmount));
          }

          public void actionPerformed(ActionEvent e){
            if(e.getSource() == placeOrder){
                  Database db = new Database();
                  Restaurant rs = getRestaurant();
                  Customer user = getUser();
                  String text = totalAmountText.getText();
                  double value = Double.parseDouble(text);
                int orderId = 0;
                try {
                    orderId = db.addOrder(user.getCustomerId(),rs.getRestaurantId(), value);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                for(int i=0; i<itemList.size();i++){
                     db.addOrderItem(orderId, itemList.get(i).getItemId(), itemAmount.get(i));
                }
                  dispose();
            }
          }
    }

    public static class OrderItemsPage {
        private JFrame frame;
        private JTable table;

        public OrderItemsPage(int orderId) throws SQLException {
            Database db = new Database();
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            ArrayList<Integer> itemsId = new ArrayList<>();
            ArrayList<Integer> amount = new ArrayList<>();
            ArrayList<Item> items = new ArrayList<>();
            orderItems = db.getOrderItemById(orderId);

            for(int i=0; i<orderItems.size(); i++){
                itemsId.add(orderItems.get(i).getOrderItemId());
                amount.add(orderItems.get(i).getQuantity());
            }

            for(int i=0; i<itemsId.size(); i++){
                items.add(db.getItemById(itemsId.get(i)));
            }

            frame = new JFrame("Ordered Items");
            frame.setSize(450,350);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            String[] columnNames = {" Item Name ", " Price ", " Amount "};
            Object[][] data = new Object[items.size()][3];

            for (int i = 0; i < items.size(); i++) {
                data[i][0] = items.get(i).getItemName();
                data[i][1] = items.get(i).getPrice();
                data[i][2] = amount.get(i);
            }

            table=new JTable(data, columnNames);
            JScrollPane scrollItems = new JScrollPane(table);
            scrollItems.setPreferredSize(new Dimension(450, 350));
            frame.add(scrollItems);

        }
    }

}
