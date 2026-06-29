import java.util.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Transaction {
    private String type;
    private String stock;
    private int quantity;
    private double price;

    public Transaction(String type, String stock,
                       int quantity, double price) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return type + " : "
                + stock
                + " Qty="
                + quantity
                + " Price="
                + price;
    }
}

class Portfolio {

    private HashMap<String, Integer> holdings =
            new HashMap<>();

    private ArrayList<Transaction> history =
            new ArrayList<>();

    private double investedAmount = 0;

    public void buy(Stock stock, int qty) {

        holdings.put(
                stock.getSymbol(),
                holdings.getOrDefault(
                        stock.getSymbol(), 0) + qty);

        investedAmount +=
                stock.getPrice() * qty;

        history.add(
                new Transaction(
                        "BUY",
                        stock.getSymbol(),
                        qty,
                        stock.getPrice()));
    }

    public void sell(Stock stock, int qty) {

        if (!holdings.containsKey(
                stock.getSymbol())) {

            System.out.println(
                    "Stock not owned.");
            return;
        }

        int current =
                holdings.get(stock.getSymbol());

        if (current < qty) {

            System.out.println(
                    "Not enough shares.");
            return;
        }

        holdings.put(
                stock.getSymbol(),
                current - qty);

        history.add(
                new Transaction(
                        "SELL",
                        stock.getSymbol(),
                        qty,
                        stock.getPrice()));
    }

    public void displayPortfolio(
            Map<String, Stock> market) {

        System.out.println(
                "\n===== PORTFOLIO =====");

        double currentValue = 0;

        for (String stock :
                holdings.keySet()) {

            int qty = holdings.get(stock);

            if (qty > 0) {

                double value =
                        qty *
                        market.get(stock)
                              .getPrice();

                currentValue += value;

                System.out.println(
                        stock +
                        " Qty:" + qty +
                        " Value:" + value);
            }
        }

        System.out.println(
                "Current Value = "
                        + currentValue);

        System.out.println(
                "Invested Value = "
                        + investedAmount);

        System.out.println(
                "Profit/Loss = "
                        + (currentValue
                        - investedAmount));
    }

    public void displayHistory() {

        System.out.println(
                "\n===== HISTORY =====");

        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}

class User {

    private String name;
    private Portfolio portfolio;

    public User(String name) {
        this.name = name;
        portfolio = new Portfolio();
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public String getName() {
        return name;
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc =
                new Scanner(System.in);

        Map<String, Stock> market =
                new HashMap<>();

        market.put(
                "AAPL",
                new Stock("AAPL", 180));

        market.put(
                "TSLA",
                new Stock("TSLA", 800));

        market.put(
                "GOOGL",
                new Stock("GOOGL", 2500));

        market.put(
                "MSFT",
                new Stock("MSFT", 320));

        User user =
                new User("Yash");

        while (true) {

            System.out.println(
                    "\n====== STOCK MARKET ======");

            System.out.println(
                    "1. View Market");

            System.out.println(
                    "2. Buy Stock");

            System.out.println(
                    "3. Sell Stock");

            System.out.println(
                    "4. Portfolio");

            System.out.println(
                    "5. Transactions");

            System.out.println(
                    "6. Exit");

            System.out.print(
                    "Enter Choice: ");

            int choice =
                    sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println(
                            "\nMarket Data");

                    for (Stock s :
                            market.values()) {

                        System.out.println(
                                s.getSymbol()
                                        + " : "
                                        + s.getPrice());
                    }
                    break;

                case 2:

                    System.out.print(
                            "Enter Stock: ");

                    String buy =
                            sc.next();

                    System.out.print(
                            "Quantity: ");

                    int bqty =
                            sc.nextInt();

                    if (market.containsKey(buy)) {

                        user.getPortfolio()
                                .buy(
                                        market.get(buy),
                                        bqty);

                        System.out.println(
                                "Purchased.");
                    }
                    else {

                        System.out.println(
                                "Invalid stock.");
                    }
                    break;

                case 3:

                    System.out.print(
                            "Enter Stock: ");

                    String sell =
                            sc.next();

                    System.out.print(
                            "Quantity: ");

                    int sqty =
                            sc.nextInt();

                    if (market.containsKey(sell)) {

                        user.getPortfolio()
                                .sell(
                                        market.get(sell),
                                        sqty);
                    }
                    else {

                        System.out.println(
                                "Invalid stock.");
                    }
                    break;

                case 4:

                    user.getPortfolio()
                            .displayPortfolio(
                                    market);
                    break;

                case 5:

                    user.getPortfolio()
                            .displayHistory();
                    break;

                case 6:

                    System.out.println(
                            "Thank You");

                    System.exit(0);

                default:

                    System.out.println(
                            "Invalid Choice");
            }
        }
    }
}