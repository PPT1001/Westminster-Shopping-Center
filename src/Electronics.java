public class Electronics extends Product{
    private String brand;
    private int warranty;


    public Electronics(String productId, String productName, String productType, int productQuantity, double productPrice, String brand, int warranty) {
        super(productId, productName,productType, productQuantity, productPrice);

        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public String getProductInfo() {
        return brand + ", " + warranty + " weeks warranty";
    }

    @Override
    public String toString() {
        return super.toString() +
                "Product Brand: " + brand + "\n" +
                "Product Warranty: " + warranty +"weeks" + "\n";
    }
}