import java.util.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WestminsterShoppingManager implements ShoppingManager {
    private static Map<String, Product> productMap = new TreeMap<>();
    private static Map<String, User> userMap = new HashMap<>();
    static final int maxProducts = 50;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //Load the products from the file
        new WestminsterShoppingManager().loadFromFile();
        System.out.println("---------------------------------------");
        System.out.println("Welcome to Westminster Shopping Centre");
        System.out.println("---------------------------------------");
        while (true) {
            new WestminsterShoppingManager().runMenu();
        }
    }

    public void runMenu() {
        int option;

        try {
            Thread.sleep(1000); // Waits for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("1. Add product");
        System.out.println("2. Delete product");
        System.out.println("3. Print all products");
        System.out.println("4. Save to file");
        System.out.println("5. GUI");
        System.out.println("6. Exit");
        System.out.println();

        System.out.print("Enter your choice: ");
        try{
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.\n");
            scanner.nextLine();
            return;
        }
        switch (option) {
            case 1:
                addProduct();
                break;
            case 2:
                removeProduct();
                break;
            case 3:
                printProducts();
                break;
            case 4:
                saveToFile();
                break;
            case 5:
                new UserLogin();
                break;
            case 6:
                System.out.println("---------------------------------------------------");
                System.out.println("Thank you for using Westminster Shopping Centre.");
                System.out.println("---------------------------------------------------");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Choose a valid option from the menu.\n");
                break;
        }

    }

    @Override
    public void addProduct() {
        if (productMap.size() < maxProducts) {
            Product product = createProduct();
            if (product != null) {
                productMap.put(product.getProductId(), product);
                System.out.println("Product added successfully.");
            }
        } else {
            System.out.println("Maximum number of products reached.");
        }
    }

    @Override
    public void removeProduct() {
        System.out.print("Enter product ID: ");
        String productId = scanner.next();
        if (productMap.containsKey(productId)) {
            productMap.remove(productId);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    @Override
    public void printProducts() {
        for (Product product : productMap.values()) {
            System.out.println(product);
        }
    }

    @Override
    public void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream("products.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(productMap);
            oos.writeObject(userMap);
            System.out.println("Products saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving products to file.");
        }
    }

    @Override
    public void loadFromFile() {
        try (FileInputStream fis = new FileInputStream("products.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            productMap = (Map<String, Product>) ois.readObject();
            userMap = (Map<String, User>) ois.readObject();
            System.out.println("Products loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products from file.");
        }
    }

    public Product createProduct() {
        String productId;
        String productName;
        int productQuantity;
        double productPrice = 0;

        //Check if the product ID already exists
        while (true) {
            System.out.print("Enter product ID: ");
            productId = scanner.next();
            if (productMap.containsKey(productId)) {
                System.out.println("Product ID already exists.");
                runMenu();
            }
            System.out.print("Enter product name: ");
            productName = scanner.next();
            try {
                System.out.print("Enter product quantity: ");
                productQuantity = scanner.nextInt();
                if (productQuantity < 0) {
                    System.out.println("Invalid quantity. Please try again.");
                    continue;
                }
                System.out.print("Enter product price: ");
                if (productPrice < 0) {
                    System.out.println("Invalid price. Please try again.");
                    continue;
                }
                productPrice = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
                continue;
            }

            System.out.print("Enter product type (1 - Electronics, 2 - Clothing): ");
            int type;
            try {
                type = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
                continue;
            }

            switch (type) {
                case 1:
                    System.out.print("Enter product brand: ");
                    String brand = scanner.next();
                    try {
                        System.out.print("Enter product warranty (weeks): ");
                        int warranty = scanner.nextInt();
                        if (warranty < 0) {
                            System.out.println("Invalid warranty. Please try again.");
                            continue;
                        }
                        return new Electronics(productId, productName, "Electronics", productQuantity, productPrice, brand, warranty);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please try again.");
                        scanner.nextLine();
                        continue;
                    }
                case 2:
                    System.out.print("Enter product size: ");
                    String size = scanner.next();
                    System.out.print("Enter product colour: ");
                    String colour = scanner.next();
                    return new Clothing(productId, productName, "Clothing", productQuantity, productPrice, size, colour);
                default:
                    System.out.println("Invalid product type. Please try again.");
                    scanner.nextLine();
                    continue;
            }
        }
    }
    //Make a getter for the product tree map and user hash map
    public static Map<String, Product> getProductMap() {
        return productMap;
    }

    public static Map<String, User> getUserMap() {
        return userMap;
    }

}