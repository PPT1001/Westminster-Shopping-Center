import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> cart = new ArrayList<>();

    public void addProduct(Product product) {
        cart.add(product);
    }

    public void removeProduct(Product product) {
        cart.remove(product);
    }

    public List<Product> getProducts() {
        return cart;
    }

    public int getQuantity(Product item) {
        int quantity = 0;
        for (Product product : cart) {
            if (product.getProductId().equals(item.getProductId())) {
                quantity++;
            }
        }
        return quantity;
    }

    public String getTotal() {
        double total = 0;
        for (Product product : cart) {
            total += product.getProductPrice();
        }
        return String.valueOf(total);
    }

    public String getFirstPurchaseDiscount(User user) {
        if (user.getNumTimesPurchased() == 0) {
            double total = 0;
            for (Product product : cart) {
                total += product.getProductPrice();
            }
            total = total * 0.1;
            total = Math.round(total * 100.0) / 100.0;
            return String.valueOf(total);
        } else {
            return "0";
        }
    }

    public String getCategoryDiscount() {
        //20% discount if the cart has 3 items or more from the same category
        int clothingCount = 0;
        int electronicsCount = 0;
        double total = 0;

        for (Product product : cart) {
            if (product.getProductType().equals("Clothing")) {
                clothingCount++;
            } else if (product.getProductType().equals("Electronics")) {
                electronicsCount++;
            }
        }

        if (clothingCount >= 3) {
            for (Product product : cart) {
                total += product.getProductPrice();
            }
            total = total * 0.2;
            return String.valueOf(total);
        } else if (electronicsCount >= 3) {
            for (Product product : cart) {
                total += product.getProductPrice();
            }
            total = total * 0.2;
            total = Math.round(total * 100.0) / 100.0;
            return String.valueOf(total);
        } else {
            return "0";
        }
    }

    public String getFinalTotal(User user) {
        double total = 0;
        for (Product product : cart) {
            total += product.getProductPrice();
        }
        total = total - Double.parseDouble(getFirstPurchaseDiscount(user)) - Double.parseDouble(getCategoryDiscount());
        total = Math.round(total * 100.0) / 100.0;
        return String.valueOf(total);
    }

    public void clearCart() {
        cart.clear();
    }
}
