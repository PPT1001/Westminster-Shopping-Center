import java.io.Serializable;

public abstract class Product implements Serializable{
    private String productId;
    private String productName;
    private final String productType;
    private int productQuantity;
    private double productPrice;

    public Product(String productId, String productName, String productType, int productQuantity, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String setProductId(String productId) {
        return this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public String setProductName(String productName) {
        return this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int setProductQuantity(int productQuantity) {
        return this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double setProductPrice(double productPrice) {
        return this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public abstract String getProductInfo();

    @Override
    public String toString() {
        return "Product ID: " + productId + "\n" +
                "Product Name: " + productName + "\n" +
                "Product Quantity: " + productQuantity + "\n" +
                "Product Price: " + productPrice + "\n";
    }

    public String shoppingCartString(Product product) {
        String ProductDetails = "<html><body>" + product.getProductId() + "<br>" +
                product.getProductName() + "<br>" +
                product.getProductInfo() + "<br>" +
                "</body></html>";
        return ProductDetails;
    }


}
