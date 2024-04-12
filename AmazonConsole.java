
import java.util.ArrayList;
import java.util.Scanner;

class Product {
    String productName;
    double price;
    int stockQuantity;
    public String quantity;

    public Product(String productName, double price, int stockQuantity) {
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
class Order {
    static int nextOrderId = 1; 
    int orderId;
    ArrayList<Product> products;
    double totalPrice;
    String customerId;

    public Order(ArrayList<Product> products, String customerId) {
        this.orderId = nextOrderId++; 
        this.products = products;
        this.customerId = customerId;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.price;
        }
    }
}
class Customer {
    String custId="brijesh@amazon.in";
    String pass="123123";
    ArrayList<Product> cart = new ArrayList<>();
    double amazonWalletBalance;

    public Customer(String custId, String pass) {
        this.custId = custId;
        this.pass = pass;
        this.amazonWalletBalance = 0; 
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void viewCart() {
        System.out.println("Your Cart:");
        for (Product product : cart) {
            System.out.println(product.productName + " - $" + product.price);
        }
    }

    public void checkoutCart() {
        double totalPrice = 0;
        for (Product product : cart) {
            totalPrice += product.price;
        }
        System.out.println("Total price: $" + totalPrice);
        if (totalPrice <= amazonWalletBalance) {
            amazonWalletBalance -= totalPrice;
            System.out.println("Checkout successful! Order placed.");
            cart.clear(); 
        } else {
            System.out.println("Insufficient balance in Amazon Wallet. Please add funds.");
        }
    }

    public void writeReview(String sellerId, String review) {
        System.out.println("Review submitted for seller " + sellerId + ": " + review);
    }
}

class Retailer {
    String retailerId;
    String pass;
   
    ArrayList<Product> stock = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<String> reviews = new ArrayList<>();
    
    public Retailer(String retailerId, String pass) {
        this.retailerId = retailerId;
        this.pass = pass;
    }

    public void addProduct(Product product) {
        stock.add(product);
    }

    public void viewProductsAndStock() {
        System.out.println("Products Available in Stock:");
        for (Product product : stock) {
            System.out.println(product.productName + " - $" + product.price + " - Stock: " + product.stockQuantity);
        }
    }

    public void viewOrders() {
        System.out.println("Viewing orders for retailer " + retailerId);
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.orderId);
                System.out.println("Products:");
                for (Product product : order.products) {
                    System.out.println(product.productName + " - Quantity: " + product.quantity);
                }
                System.out.println("Total Price: $" + order.totalPrice);
                System.out.println("Customer ID: " + order.customerId);
                System.out.println("---------------------------");
            }
        }
    }

    public void readReviews(String sellerId) {
        System.out.println("Reading reviews for seller " + sellerId);
        if (reviews.isEmpty()) {
            System.out.println("No reviews available for this seller.");
        } else {
            for (String review : reviews) {
                System.out.println(review);
            }
        }
    }
}

class AdminAmazon {
    String id = "admin";
    String pass = "amazon@2024";
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Retailer> retailers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addRetailer(Retailer retailer) {
        retailers.add(retailer);
    }
    
    public boolean isAdmin(String id, String pass) {
        return this.id.equals(id) && this.pass.equals(pass);
    }
}

class AmazonConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminAmazon admin = new AdminAmazon();

        Product laptop = new Product("Laptop", 800, 10);
        Product smartphone = new Product("Smartphone", 500, 20);
        Product headphones = new Product("Headphones", 100, 30);

     
        Retailer retailer = new Retailer("retailer1", "password1");
        retailer.addProduct(laptop);
        retailer.addProduct(smartphone);
        retailer.addProduct(headphones);
        admin.addRetailer(retailer);

        Customer customer1 = new Customer("brijesh@amazon.in", "123123");
        admin.addCustomer(customer1);

        Retailer retailer1 = new Retailer("nike@chennai", "nikeshoes123");
        admin.addRetailer(retailer1);

        while (true) {
            System.out.println("Choose who to login as:");
            System.out.println("1. Customer");
            System.out.println("2. Seller (Retailer)");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    
                    System.out.println("Enter customer ID:");
                    String custId = scanner.nextLine();
                    System.out.println("Enter password:");
                    String custPass = scanner.nextLine();
                    Customer customer = findCustomer(admin.customers, custId, custPass);
                    if (customer != null) {
                        customerMenu(scanner, customer, retailer);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 2:
                    
                    System.out.println("Enter retailer ID:");
                    String retailerId = scanner.nextLine();
                    System.out.println("Enter password:");
                    String retailerPass = scanner.nextLine();
                    Retailer loggedInRetailer = findRetailer(admin.retailers, retailerId, retailerPass);
                    if (loggedInRetailer != null) {
                        retailerMenu(scanner, loggedInRetailer,retailerId);
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 3:
                    
                    System.out.println("Enter admin ID:");
                    String adminId = scanner.nextLine();
                    System.out.println("Enter password:");
                    String adminPass = scanner.nextLine();
                    if (admin.isAdmin(adminId, adminPass)) {
                        System.out.println("Admin login successful.");
                        // Admin actions can be added here
                    } else {
                        System.out.println("Admin login failed.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
    }

    private static Customer findCustomer(ArrayList<Customer> customers, String custId, String custPass) {
        for (Customer customer : customers) {
            if (customer.custId.equals(custId) && customer.pass.equals(custPass)) {
                return customer;
            }
        }
        return null;
    }

    private static Retailer findRetailer(ArrayList<Retailer> retailers, String retailerId, String retailerPass) {
        for (Retailer retailer : retailers) {
            if (retailer.retailerId.equals(retailerId) && retailer.pass.equals(retailerPass)) {
                return retailer;
            }
        }
        return null;
    }

    private static void customerMenu(Scanner scanner, Customer customer, Retailer retailer) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View list of products");
            System.out.println("2. Add product to cart");
            System.out.println("3. View cart");
            System.out.println("4. Checkout cart");
            System.out.println("5. Add funds to Amazon Wallet");
            System.out.println("6. Write a review for a seller");
            System.out.println("7. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Available Products:");
                    for (Product product : retailer.stock) {
                        System.out.println(product.productName + " - $" + product.price);
                    }
                    break;
                case 2:
                   
                    System.out.println("Enter the name of the product you want to add to cart:");
                    String productName = scanner.nextLine();
                    Product selectedProduct = findProduct(retailer.stock, productName);
                    if (selectedProduct != null) {
                        customer.addToCart(selectedProduct);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 3:
                    customer.viewCart();
                    break;
                case 4:
                    
                    customer.checkoutCart();
                    break;
                case 5:
                    System.out.println("Enter amount to add to Amazon Wallet:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    customer.amazonWalletBalance += amount;
                    System.out.println("Funds added to Amazon Wallet. Current balance: $" + customer.amazonWalletBalance);
                    break;
                case 6:
                    
                    System.out.println("Enter seller ID for whom you want to write a review:");
                    String sellerId = scanner.nextLine();
                    System.out.println("Enter your review:");
                    String review = scanner.nextLine();
                    customer.writeReview(sellerId, review);
                    break;
                case 7:
                    return; 
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }

    private static Product findProduct(ArrayList<Product> products, String productName) {
        for (Product product : products) {
            if (product.productName.equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    private static void retailerMenu(Scanner scanner, Retailer retailer,String sellerId) {
        while (true) {
            System.out.println("\nRetailer Menu:");
            System.out.println("1. View products and stock");
            System.out.println("2. View orders");
            System.out.println("3. Add product to stock");
            System.out.println("4. Read reviews");
            System.out.println("5. Back to main menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                   
                    retailer.viewProductsAndStock();
                    break;
                case 2:
                    
                    retailer.viewOrders();
                    break;
                case 3:
                  
                    System.out.println("Enter the name of the product to add:");
                    String newProductName = scanner.nextLine();
                    System.out.println("Enter the price of the product:");
                    double newProductPrice = scanner.nextDouble();
                    System.out.println("Enter the quantity of the product:");
                    int newProductQuantity = scanner.nextInt();
                    scanner.nextLine();
                    Product newProduct = new Product(newProductName, newProductPrice, newProductQuantity);
                    retailer.addProduct(newProduct);
                    System.out.println("Product added to stock.");
                    break;
                case 4:
                    retailer.readReviews(sellerId);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }
}
