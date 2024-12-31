import java.util.*;

// ========================= PIZZA CUSTOMIZATION - BUILDER PATTERN =========================
class Pizza {
    private String crust;
    private String sauce;
    private List<String> toppings;
    private String cheese;
    private boolean seasonalSpecial;

    public Pizza(String crust, String sauce, List<String> toppings, String cheese, boolean seasonalSpecial) {
        this.crust = crust;
        this.sauce = sauce;
        this.toppings = toppings;
        this.cheese = cheese;
        this.seasonalSpecial = seasonalSpecial;
    }

    public String getDescription() {
        return "Crust: " + crust + ", Sauce: " + sauce + ", Cheese: " + cheese +
                ", Toppings: " + toppings + (seasonalSpecial ? " [Seasonal Special]" : "");
    }

    public double calculatePrice() {
        double basePrice = 10.00;
        basePrice += toppings.size() * 1.5; // $1.5 per topping
        if (seasonalSpecial) basePrice += 3.0; // $3 for seasonal specials
        return basePrice;
    }
}

class PizzaBuilder {
    private String crust;
    private String sauce;
    private List<String> toppings = new ArrayList<>();
    private String cheese;
    private boolean seasonalSpecial;

    public PizzaBuilder setCrust(String crust) {
        this.crust = crust;
        return this;
    }

    public PizzaBuilder setSauce(String sauce) {
        this.sauce = sauce;
        return this;
    }

    public PizzaBuilder addTopping(String topping) {
        toppings.add(topping);
        return this;
    }

    public PizzaBuilder setCheese(String cheese) {
        this.cheese = cheese;
        return this;
    }

    public PizzaBuilder setSeasonalSpecial(boolean special) {
        this.seasonalSpecial = special;
        return this;
    }

    public Pizza build() {
        return new Pizza(crust, sauce, toppings, cheese, seasonalSpecial);
    }
}

// ========================= ORDER PROCESSING AND TRACKING =========================
class Order {
    private Pizza pizza;
    private double price;
    private String customerName;
    private String feedback;
    private int rating;

    public Order(Pizza pizza, String customerName) {
        this.pizza = pizza;
        this.customerName = customerName;
        this.price = pizza.calculatePrice();
    }

    public void provideFeedback(String feedback, int rating) {
        this.feedback = feedback;
        this.rating = rating;
    }

    public void printReceipt() {
        System.out.println("\n========= PAYMENT RECEIPT =========");
        System.out.println("Customer: " + customerName);
        System.out.println("Order Details: " + pizza.getDescription());
        System.out.printf("Total Price: $%.2f\n", price);
        System.out.println("Thank you for your order!\n===================================");
    }

    public String getOrderDetails() {
        return "Customer: " + customerName + ", Pizza: " + pizza.getDescription() + ", Price: $" + price;
    }

    public String getFeedback() {
        return feedback != null ? "Rating: " + rating + " | Feedback: " + feedback : "No feedback provided.";
    }

    public double getPrice() {
        return price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPizzaDescription() {
        return pizza.getDescription();
    }
}

// ========================= PAYMENT STRATEGY PATTERN =========================
interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.printf("Paid $%.2f via Credit Card.\n", amount);
    }
}

class DigitalWalletPayment implements PaymentStrategy {
    public void pay(double amount) {
        System.out.printf("Paid $%.2f via Digital Wallet.\n", amount);
    }
}

// ========================= USER PROFILE AND FAVORITES =========================
class UserProfile {
    private String name;
    private int loyaltyPoints = 0;
    private List<Order> orderHistory = new ArrayList<>();

    public UserProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    public void earnLoyaltyPoints(int points) {
        loyaltyPoints += points;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void showOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No order history found.");
            return;
        }
        System.out.println("========== ORDER HISTORY ==========");
        for (Order order : orderHistory) {
            System.out.println(order.getOrderDetails());
            System.out.println("Feedback: " + order.getFeedback());
        }
        System.out.println("===================================");
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }
}

// ========================= MAIN APPLICATION =========================
public class PizzaOrderingManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<UserProfile> users = new ArrayList<>();
    private static List<String> seasonalSpecials = Arrays.asList("Pumpkin Spice Pizza", "Holiday Turkey Pizza","20% Off on Commercial Credit Cards","Buy 1 Thin Crust Pizza and get 1 Free","25% off on Sundays for Large Pan Pizzas");

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n========= PIZZA ORDERING SYSTEM By ILSHAM=========");
            System.out.println("1. Create User Profile and Favourites");
            System.out.println("2. Place Order");
            System.out.println("3. View Order History");
            System.out.println("4. View Seasonal Specials and Promotions");
            System.out.println("5. View Sales Report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1: createUserProfile(); break;
                case 2: placeOrder(); break;
                case 3: viewOrderHistory(); break;
                case 4: viewSeasonalSpecials(); break;
                case 5: viewSalesReport(); break;
                case 6: System.out.println("Thank you & Come again - ILSHAM !"); return;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createUserProfile() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        users.add(new UserProfile(name));
        System.out.println("Profile created successfully for: " + name);
    }

    private static UserProfile findUserProfile(String name) {
        for (UserProfile user : users) {
            if (user.getName().equalsIgnoreCase(name)) return user;
        }
        return null;
    }

    private static void placeOrder() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        UserProfile user = findUserProfile(name);
        if (user == null) {
            System.out.println("User not found. Please create a profile first.");
            return;
        }

        // Pizza Customization
        PizzaBuilder builder = new PizzaBuilder();
        System.out.print("Choose crust (Thin/Thick): ");
        builder.setCrust(scanner.nextLine());
        System.out.print("Choose sauce: ");
        builder.setSauce(scanner.nextLine());
        System.out.print("Add toppings (comma-separated): ");
        String[] toppings = scanner.nextLine().split(",");
        for (String topping : toppings) builder.addTopping(topping.trim());

        System.out.print("Include seasonal special? (yes/no): ");
        boolean seasonalSpecial = scanner.nextLine().equalsIgnoreCase("yes");
        builder.setSeasonalSpecial(seasonalSpecial);

        Pizza pizza = builder.build();
        Order order = new Order(pizza, name);

        // Payment
        System.out.println("Choose payment method (1: Credit Card, 2: Digital Wallet): ");
        int paymentChoice = scanner.nextInt();
        PaymentStrategy payment = (paymentChoice == 1) ? new CreditCardPayment() : new DigitalWalletPayment();
        payment.pay(order.getPrice());

        // Finalize order
        user.addOrderToHistory(order);
        user.earnLoyaltyPoints(10);
        order.printReceipt();

        // Feedback
        System.out.print("Provide feedback (1-5 rating): ");
        int rating = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Write a short feedback: ");
        String feedback = scanner.nextLine();
        order.provideFeedback(feedback, rating);
    }

    private static void viewOrderHistory() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        UserProfile user = findUserProfile(name);
        if (user != null) user.showOrderHistory();
        else System.out.println("User not found.");
    }

    private static void viewSeasonalSpecials() {
        System.out.println("\n======= SEASONAL SPECIALS =======");
        for (String special : seasonalSpecials) System.out.println("- " + special);
        System.out.println("=================================");
    }

    private static void viewSalesReport() {
        System.out.println("\n======= SALES REPORT =======");
        double totalSales = 0;
        for (UserProfile user : users) {
            for (Order order : user.getOrderHistory()) {
                totalSales += order.getPrice();
            }
        }
        System.out.printf("Total Sales: $%.2f\n", totalSales);
        System.out.println("=============================");
    }
}
